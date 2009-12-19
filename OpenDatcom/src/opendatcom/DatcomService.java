/*
 * DatcomService.java
 *
 * Created on Oct 14, 2009, 2:09:07 AM
 */

package opendatcom;

import Abstracts.AbstractController;
import Abstracts.OAE_LinkedTable;
import FLTCON_Component.FlightConditionsController;
import Services.ImportExportService;
import Services.ProjectService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

/**
 * 
 * @author -B-
 */
public class DatcomService extends javax.swing.JPanel {

    String outputData;
    LinkedList<AbstractController> controllers;
    OpenDatcomController parent;
    ProjectService ps;
    ImportExportService ies;

    // Regex constants
    String regexHeader = "0 ALPHA     ";
    String regexFooter = "0                                    ALPHA     ";
    String datcomPath = System.getProperty("user.dir") + "\\Bin\\Datcom\\datcom.exe";

    /** Creates new form DatcomService */
    public DatcomService() {
        initComponents();
        outputData = "";
        controllers = new LinkedList<AbstractController>();
        parent = OpenDatcomController.getInstance();
        ps = ProjectService.getInstance();
        ies = ImportExportService.getInstance();
    }

    /**
     * Registers a controller with the view. The order matters! Controllers will
     * be accessed in the order they are added.
     * @param control The controller to register.
     */
    public void registerController(AbstractController control)
    {
        controllers.add(control);
    }

    /**
     * Updates the text in the output's view
     * @return
     */
    public String getControllerOutput()
    {
        String temp = "";
        
        for(int x = 0; x < controllers.size(); x++)
        {
            controllers.get(x).refresh();
            System.out.println("Generating output for: " + controllers.get(x).getName());
            temp += controllers.get(x).generateOutput();
        }

        return temp;
    }


    /**
     * Removes all the comments & blank lines from the input data.
     * @param target The input data.
     * @return The The input data - any # comments.
     */
    public String datcomFormat(String target)
    {
        String [] temp = target.split("\n");
        target = "";
        for(int x = 0; x < temp.length; x++)
        {
            if(!temp[x].isEmpty())
            {
                temp[x] += "\n";
                if(temp[x].charAt(0) == '#')
                {
                    temp[x] = "";
                }
                target += temp[x];
            }
        }
        target = target.replaceAll("\t", "");
        if(jBuild.isSelected())
        {
            target += "BUILD\n";
        }
        if(jPlot.isSelected())
        {
            target += "PLOT\n";
        }
        target += "NEXT CASE";
        return target;
    }

    /**
     * Runs the Datcom a single time.
     */
    private void runDatcom()
    {
        try {
            generateDat();
            Process p = new ProcessBuilder(datcomPath).start();
            p.waitFor();
            moveForFiles();
            getjOutputText().setText(getjOutputText().getText() + "Datcom completely succesfully, " +
                    "data saved to: " + ps.getProjectPath());
        } catch (IOException ex) {
            Logger.getLogger(DatcomService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DatcomService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Start of the monolithic Datcom->JSBSim process. This is one of the slowest
     * functions I have ever created and calls datcom.exe nAlpha * nMach * nAOA times.
     * It is broken up into helper functions to make it (slightly) more readable.
     */
    private void generateJSBSim()
    {
        // Remove build and plot before JSBSim is ran
        jBuild.setSelected(false);
        jPlot.setSelected(false);
        
        FlightConditionsController fcc = (FlightConditionsController)parent.getController("Flight Conditions");
        if(fcc == null)
        {
            return;
        }

        Vector<Double> sAlts = ((OAE_LinkedTable)fcc.lookupLink("ALT")).getData();
        Vector<Double> sMachs = ((OAE_LinkedTable)fcc.lookupLink("MACH")).getData();
        Vector<Double> sAoas = ((OAE_LinkedTable)fcc.lookupLink("AOA")).getData();

        String tempPath = "";
        try {
            for(int a = 0; a < sAlts.size(); a++)
            {
                ((OAE_LinkedTable)fcc.lookupLink("ALT")).getData().clear();
                ((OAE_LinkedTable)fcc.lookupLink("MACH")).getData().add(sMachs.get(a));
                for(int m = 0; m < sMachs.size(); m++)
                {
                    ((OAE_LinkedTable)fcc.lookupLink("MACH")).getData().clear();
                    ((OAE_LinkedTable)fcc.lookupLink("MACH")).getData().add(sMachs.get(a));
                    tempPath = ps.getProjectPath() + "\\Table_Data\\" + sAlts.get(a)
                            + "\\" + sMachs.get(m) + "_" + sAlts.get(a);
                    File destF = new File(tempPath + "\\for006.dat");
                    destF.mkdirs();
                    generateDat();
                    getjOutputText().setText(getjOutputText().getText() + "Generating JSBSim data for " +
                        "Altitude: " + sAlts.get(a) + " Mach: " + sMachs.get(m) + "\n");
                    Process p = new ProcessBuilder(datcomPath).start();
                    p.waitFor();
                    moveForFiles(tempPath, sAlts.get(a).toString());
                    processJSBSimData(destF, sAlts.get(a).toString());
                }// Machs
            }// Altitudes
            
            ((OAE_LinkedTable)fcc.lookupLink("ALT")).setData(sAlts);
            ((OAE_LinkedTable)fcc.lookupLink("MACH")).setData(sMachs);
            ((OAE_LinkedTable)fcc.lookupLink("AOA")).setData(sAoas);
            getjOutputText().setText(getjOutputText().getText() + "JSBSim table generation " +
                    "completed. Data saved to " + ps.getProjectPath() + "\\Table_Data");
        } catch (InterruptedException ex) {
            Logger.getLogger(DatcomService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatcomService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Generates a for005.dat file from all the user's entered data. The file is
     * created in the working directory, which is in the OpenDatcom folder.
     */
    public void generateDat()
    {
        File datFile = new File(parent.getWorkingDirectory().getAbsolutePath() +"\\for005.dat");
        String temp = getControllerOutput();
        temp = datcomFormat(temp);
        try
        {
           datFile.createNewFile();
           BufferedWriter output = new BufferedWriter(new FileWriter(datFile));
           String [] newlineTempCauseJavaSucks = temp.split("\n");
           for(int x = 0; x < newlineTempCauseJavaSucks.length; x++)
           {
               output.write(newlineTempCauseJavaSucks[x]);
               output.newLine();
           }
           output.close();

        } catch (IOException ex)
        {
            Logger.getLogger(DatcomService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Moves the datcom-generated for00X files from the working space to the user's
     * project directory.
     */
    private void moveForFiles()
    {
        File moveForSource = null;
        File moveForDest = null;

        // Loop through and move the files
        for(int i = 5; i < 15; i++)
        {
            // Set the file names correctly
            if(i < 10)
            {
                moveForSource = new File("for00" + i + ".dat");
                moveForDest = new File(ps.getProjectPath() + "\\"+ ps.getProjectName() + " for00" + i +".dat");
            }
            else
            {
                moveForSource = new File("for0" + i + ".dat");
                moveForDest = new File(ps.getProjectPath() + "\\"+ ps.getProjectName() + " for0" + i +".dat");
            }

            // Delete old files so the move can be executed
            if(moveForDest.exists())
            {
                moveForDest.delete();
            }

            // only move the
            if(moveForSource != null)
            {
                moveForSource.renameTo(moveForDest);
            }
        }
    }

     /**
      * Moves all for0XX.dat files to the destionation path. Deletes originals.
      * @param dest The destination path.
      */
    private void moveForFiles(String dest, String Alt)
    {

        File moveForSource = null;
        File moveForDest = null;

        // Loop through and move the files
        for(int i = 5; i < 15; i++)
        {
            // Set the file names correctly
            if(i < 10)
            {
                moveForSource = new File("for00" + i + ".dat");
                moveForDest = new File(dest + "\\for00" + i +".dat");
            }
            else
            {
                moveForSource = new File("for0" + i + ".dat");
                moveForDest = new File(dest + "\\for0" + i +".dat");
            }

            // Delete old files so the move can be executed
            if(moveForDest.exists())
            {
                moveForDest.delete();
            }

            if(moveForSource != null)
            {
                moveForSource.renameTo(moveForDest);
            }
        }
    }

    /**
     * Takes a for006.dat file and rip out the table with the JSBSim data out. Calls
     * several helper functions in the process. Warning, this function is a hog, it
     * uses 12 linked lists, relies mainley on regex expressions and executes a
     * ton of file i/o. Function is a potential target for future optimization
     * @param target Target for006 file, everything else will error immediately.
     */
    private void processJSBSimData(File target, String Alt)
    {
        try {
            LinkedList<Double> CD = new LinkedList<Double>();
            LinkedList<Double> CL = new LinkedList<Double>();
            LinkedList<Double> CM = new LinkedList<Double>();
            LinkedList<Double> CN = new LinkedList<Double>();
            LinkedList<Double> CA = new LinkedList<Double>();
            LinkedList<Double> XCP = new LinkedList<Double>();
            LinkedList<Double> CLA = new LinkedList<Double>();
            LinkedList<Double> CMA = new LinkedList<Double>();
            LinkedList<Double> CYB = new LinkedList<Double>();
            LinkedList<Double> CNB = new LinkedList<Double>();
            LinkedList<Double> CLB = new LinkedList<Double>();
            LinkedList<Double> alpha = new LinkedList<Double>();

            String data = ies.importFile(target);
            data = data.split(regexHeader)[1];
            data = data.split(regexFooter)[0];

            // At this point data is just 2 lines of header followed by the table
            String[] lines = data.split("\n");
            String[] values;

            for (int i = 2; i < lines.length; i++) {
                // this junk eliminates the whitespace and replaces it with commas
                lines[i] = lines[i].replaceAll(" * ", ",");
                lines[i] = lines[i].replaceAll("NDM", "-1");
                // and now the commas are gone!
                values = lines[i].split(",");
                alpha.add(Double.valueOf(values[1]));

                // Switch off the number of values and add as approprate
                if (values.length == 13)
                {
                    CD.add(Double.valueOf(values[2]));
                    CL.add(Double.valueOf(values[3]));
                    CM.add(Double.valueOf(values[4]));
                    CN.add(Double.valueOf(values[5]));
                    CA.add(Double.valueOf(values[6]));
                    XCP.add(Double.valueOf(values[7]));
                    CLA.add(Double.valueOf(values[8]));
                    CMA.add(Double.valueOf(values[9]));
                    CYB.add(Double.valueOf(values[10]));
                    CNB.add(Double.valueOf(values[11]));
                    CLB.add(Double.valueOf(values[12]));
                }
                else if (values.length == 11)
                {
                    CD.add(Double.valueOf(values[2]));
                    CL.add(Double.valueOf(values[3]));
                    CM.add(Double.valueOf(values[4]));
                    CN.add(Double.valueOf(values[5]));
                    CA.add(Double.valueOf(values[6]));
                    XCP.add(Double.valueOf(values[7]));
                    CLA.add(Double.valueOf(values[8]));
                    CMA.add(Double.valueOf(values[9]));
                    CLB.add(Double.valueOf(values[10]));
                }
            }
            writeLinkedList(CD, alpha, target.getParent(), "CD.txt", Alt);
            writeLinkedList(CL, alpha, target.getParent(), "CL.txt", Alt);
            writeLinkedList(CN, alpha, target.getParent(), "CN.txt", Alt);
            writeLinkedList(CM, alpha, target.getParent(), "CM.txt", Alt);
            writeLinkedList(CA, alpha, target.getParent(), "CA.txt", Alt);
            writeLinkedList(XCP, alpha, target.getParent(), "XCP.txt", Alt);
            writeLinkedList(CLA, alpha, target.getParent(), "CLA.txt", Alt);
            writeLinkedList(CMA, alpha, target.getParent(), "CMA.txt", Alt);
            writeLinkedList(CYB, alpha, target.getParent(), "CYB.txt", Alt);
            writeLinkedList(CNB, alpha, target.getParent(), "CNB.txt", Alt);
            writeLinkedList(CLB, alpha, target.getParent(), "CLB.txt", Alt);

        } catch (IOException ex) {
            Logger.getLogger(DatcomService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Helper function to make everything a bit more readable; called in processJSBSim.
     * Function takes in the data linked list and the alpha values and writes it to
     * the file specified.
     * @param in The data to write
     * @param alpha The corespoinding alpha values
     * @param Path Path to write the data too.
     * @param fileName Filename to write the data to.
     * @param Alt The current altitude value
     * @throws IOException IOException handled in processJSBSim.
     */
    private void writeLinkedList(LinkedList<Double> in, LinkedList<Double> alpha,
            String Path, String fileName, String Alt) throws IOException
    {
        String output = Path + "\\" + fileName;
        File dest = new File(output);
        output = "<tableData breakPoint=\"" + Alt + "\">\n";
        dest.createNewFile();
        for (int i = 0; i < in.size(); i++) {
            output += alpha.get(i) + "\t" + in.get(i) + "\n";
        }
        ies.writeFile(dest, output);
    }

    public JTextPane getjOutputText() {
        return jOutputText;
    }

    public void setjOutputText(JTextPane jOutputText) {
        this.jOutputText = jOutputText;
    }

    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
        jOutputText.setText(outputData);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jOutputText = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jShowDatcomReadable = new javax.swing.JButton();
        jShowHumanReadable = new javax.swing.JButton();
        jRunDatcom = new javax.swing.JButton();
        jRunJSBSim = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jDrawPane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPlot = new javax.swing.JCheckBox();
        jBuild = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(opendatcom.OpenDatcomController.class).getContext().getResourceMap(DatcomService.class);
        jOutputText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jOutputText.border.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION))); // NOI18N
        jOutputText.setEditable(false);
        jOutputText.setFocusable(false);
        jOutputText.setName("jOutputText"); // NOI18N
        jScrollPane1.setViewportView(jOutputText);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jShowDatcomReadable.setText(resourceMap.getString("jShowDatcomReadable.text")); // NOI18N
        jShowDatcomReadable.setName("jShowDatcomReadable"); // NOI18N
        jShowDatcomReadable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jShowDatcomReadableActionPerformed(evt);
            }
        });

        jShowHumanReadable.setText(resourceMap.getString("jShowHumanReadable.text")); // NOI18N
        jShowHumanReadable.setName("jShowHumanReadable"); // NOI18N
        jShowHumanReadable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jShowHumanReadableActionPerformed(evt);
            }
        });

        jRunDatcom.setText(resourceMap.getString("jRunDatcom.text")); // NOI18N
        jRunDatcom.setName("jRunDatcom"); // NOI18N
        jRunDatcom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRunDatcomActionPerformed(evt);
            }
        });

        jRunJSBSim.setText(resourceMap.getString("jRunJSBSim.text")); // NOI18N
        jRunJSBSim.setName("jRunJSBSim"); // NOI18N
        jRunJSBSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRunJSBSimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jShowDatcomReadable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jShowHumanReadable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jRunDatcom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jRunJSBSim, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jShowHumanReadable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jShowDatcomReadable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRunDatcom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRunJSBSim)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setName("jPanel3"); // NOI18N

        jDrawPane.setBackground(resourceMap.getColor("jDrawPane.background")); // NOI18N
        jDrawPane.setName("jDrawPane"); // NOI18N

        javax.swing.GroupLayout jDrawPaneLayout = new javax.swing.GroupLayout(jDrawPane);
        jDrawPane.setLayout(jDrawPaneLayout);
        jDrawPaneLayout.setHorizontalGroup(
            jDrawPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 246, Short.MAX_VALUE)
        );
        jDrawPaneLayout.setVerticalGroup(
            jDrawPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDrawPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jDrawPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jPlot.setText(resourceMap.getString("jPlot.text")); // NOI18N
        jPlot.setName("jPlot"); // NOI18N

        jBuild.setText(resourceMap.getString("jBuild.text")); // NOI18N
        jBuild.setName("jBuild"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBuild)
                    .addComponent(jPlot))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(127, Short.MAX_VALUE)
                .addComponent(jBuild)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPlot)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jShowHumanReadableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jShowHumanReadableActionPerformed
        String temp = getControllerOutput();
        jOutputText.setText(temp);
        //parent.getUnits();
    }//GEN-LAST:event_jShowHumanReadableActionPerformed

    private void jShowDatcomReadableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jShowDatcomReadableActionPerformed
        String temp = getControllerOutput();
        temp = datcomFormat(temp);
        jOutputText.setText(temp);
    }//GEN-LAST:event_jShowDatcomReadableActionPerformed

    private void jRunDatcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRunDatcomActionPerformed
        getjOutputText().setText("Running Datcom\n");
        runDatcom();
    }//GEN-LAST:event_jRunDatcomActionPerformed

    private void jRunJSBSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRunJSBSimActionPerformed
        getjOutputText().setText("Starting JSBSim generation (This can take a while).\n");
        generateJSBSim();
    }//GEN-LAST:event_jRunJSBSimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jBuild;
    private javax.swing.JPanel jDrawPane;
    private javax.swing.JTextPane jOutputText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JCheckBox jPlot;
    private javax.swing.JButton jRunDatcom;
    private javax.swing.JButton jRunJSBSim;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jShowDatcomReadable;
    private javax.swing.JButton jShowHumanReadable;
    // End of variables declaration//GEN-END:variables

}
