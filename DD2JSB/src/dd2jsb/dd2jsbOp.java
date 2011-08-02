/* Aleksey Matyushev / Alan Teeder
 *
 * This program is the sole property of Aleksey
 * Matyushev and Alan Teeder. This program is designed to be an
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear /JSBSim.
 */
package dd2jsb;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JPanel;
import java.io.*;

public class dd2jsbOp extends javax.swing.JPanel {

    public dd2jsbOp(String Path, String workingPath, String caseID,
            dd2jsbMAIN mainClass, JPanel mainPanel) {
        initComponents();
        this.path = Path;
        this.workingPath = workingPath;
        this.caseID = caseID;
        this.mainClass = mainClass;
        this.mainPanel = mainPanel;
        jTextArea1.append("Press 'Build Tables' to start processing." + newline);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dd2jsb.dd2jsbApp.class).getContext().getResourceMap(dd2jsbOp.class);
        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 356, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private String[] getArrayFromString(String tempField) {
        String tempArrayString = "";
        int arrayCounter = 0;
        int countARRAYnumbersINfield = 0;

        char[] tempFieldArray = tempField.toCharArray();

        for (int z = 0; z < tempFieldArray.length; z++) {
            if (tempFieldArray[z] == ',') {
                countARRAYnumbersINfield++;
            }
        }
        String[] ARRAY = new String[countARRAYnumbersINfield];

        for (int k = 0; k < tempFieldArray.length; k++) {
            if (tempFieldArray[k] == ',') {
                ARRAY[arrayCounter] = tempArrayString;
                tempArrayString = "";
                arrayCounter++;
            } else {
                tempArrayString = tempArrayString + tempFieldArray[k];
            }
        }
        return ARRAY;
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Build Tables
        int i = 0;
        int j = 0;
        int k = 0;
        String line;
        int iline;
        int linePosition = 0;
        int lineEnd = 0;
        String[] Case;
        int NCases;
        String[] lines;
        int Nlines;
        String printMACH;
        String printALT;
        double fMACHdata = 0.0;
        double fALTdata = 0.0;
        float FMACH = 0;
        int NMACH = 0;
        float FALT = 0;
        int NALT = 0;
        float FALPHA = 0;
        int NALPHA = 0;
        String s = null;
        String CaseIDtext = null;
        aero = null;
        DUtil = new dd2jsbUtils();
        try {
            aero = DUtil.getText(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(dd2jsbAeroData.class.getName()).log(Level.SEVERE, null, ex);
        }
// Spilt datcom output file into cases (i.e. one for each CASEID)
// change all scientific numbers to E+00 format instead of the +000 format pruduced by some Fortran ompilers.
    String REGEX = "([0-9])([-\\+])([0-9])([0-9])";
    String REPLACE = "$1E$2$4";
    Pattern p = Pattern.compile(REGEX);
    String newaero = aero.replaceAll (REGEX,REPLACE);
        Case = newaero.split("1          THE FOLLOWING IS A LIST OF ALL INPUT CARDS FOR THIS CASE.");
        NCases = Case.length;

// Now process each case in turn.
// (Note: "Case 0" is a copy of the Datcom input data for all cases

//Start at "Case 1" which is where the output data starts.
        for (k = 1; k < NCases; k++) {
            lines = Case[k].split("\n");
            Nlines = lines.length;

            //look for NMACH in Case and update if found
            linePosition = Case[k].lastIndexOf("NMACH");
            if (linePosition > -1) {
                linePosition = Case[k].indexOf("=", linePosition);
                lineEnd = Case[k].indexOf(",", linePosition);
                line = Case[k].substring(linePosition + 1, lineEnd);
                FMACH = Float.parseFloat(line);
                NMACH = (int) FMACH;
                //look for MACH(1) in Case
                linePosition = Case[k].indexOf("MACH(1)", lineEnd);
                 if (linePosition == -1) {
                 //look for just MACH in Case
                 linePosition = Case[k].indexOf("MACH", lineEnd);   
                 }
                linePosition = Case[k].indexOf("=", linePosition);
                line = Case[k].substring(linePosition + 1, linePosition + 200);
                line = line.replaceAll("\\$", ",");
                line = line.replaceAll("[^a-zA-Z0-9.,]", "");
                MACH = line.split("[,\n]");
            }
            //look for NALT in Case and update if found
            linePosition = Case[k].lastIndexOf("NALT");
            if (linePosition > -1) {
                linePosition = Case[k].indexOf("=", linePosition);
                lineEnd = Case[k].indexOf(",", linePosition);
                line = Case[k].substring(linePosition + 1, lineEnd);
                FALT = Float.parseFloat(line);
                NALT = (int) FALT;
                //look for ALT(1) in Case
                linePosition = Case[k].indexOf("ALT(1)", lineEnd);
                 if (linePosition == -1) {                
                 //look for just ALT in Case
                 linePosition = Case[k].indexOf("ALT", lineEnd);   
                 }                
                linePosition = Case[k].indexOf("=", linePosition);
                line = Case[k].substring(linePosition + 1, linePosition + 200);
                line = line.replaceAll("\\$", ",");
                line = line.replaceAll("[^a-zA-Z0-9.,]", "");
                ALT = line.split("[,\n]");
            }

            //look for NALPHA in Case and update if found
            linePosition = Case[k].lastIndexOf("NALPHA");
            if (linePosition > -1) {
                linePosition = Case[k].indexOf("=", linePosition);
                lineEnd = Case[k].indexOf(",", linePosition);
                line = Case[k].substring(linePosition + 1, lineEnd);
                FALPHA = Float.parseFloat(line);
                NALPHA = (int) FALPHA;
            }
            iline = 0;
            // look for CASEID .
            CaseIDtext = "";
            while (CaseIDtext.length() < 1) {
                iline++;
                if (iline <= Nlines) {
                  if (lines[iline].length() > 40) {
                    if (lines[iline].contains("CASEID")) {
                        CaseIDtext = (lines[iline].trim());
                        jTextArea1.append("Processing Case: ");
                        jTextArea1.append(CaseIDtext + newline);
                        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
                        //  Remove non alphanumerics to produce legal file name string.
                        CaseIDtext = CaseIDtext.replaceAll("[^a-zA-Z0-9-]", "");
                        CaseIDtext = CaseIDtext.replaceAll("CASEID", "");
                    }
                }
              }

            else {
                jTextArea1.append("dd2jsbOp warning: iline less than Nlines" + newline);
                }
            }
            //create a new instance of dd2jsbAeroData

            aerodat = new dd2jsbAeroData(Case[k]);
            aerodat.AllocateArrays(NALPHA, NMACH, NALT);
            int i1 = path.lastIndexOf("\\");
            int i2 = path.lastIndexOf(".");
            String fname = path.substring(i1 + 1, i2);
            String CaseDir = workingPath + "\\" + caseID + "\\" + fname + "\\" + CaseIDtext;
            jTextArea1.append("Creating Files in : " + CaseDir + ":" + newline);
            boolean status = new File(CaseDir).mkdirs();
            double[] fMACH = new double[NMACH];
            double[] fALT = new double[NALT];

            for (i = 0; i < NMACH; i++) {
                try {
                    fMACH[i] = DUtil.GetDoublefromString(MACH[i]);
                } catch (Exception ex) {
                    Logger.getLogger(dd2jsbAeroData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (i = 0; i < NALT; i++) {
                try {
                    fALT[i] = DUtil.GetDoublefromString(ALT[i]);
                } catch (Exception ex) {
                    Logger.getLogger(dd2jsbAeroData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            int[][] lmachaltstart = new int[NALT][NMACH];
            int[][] lmachaltend = new int[NALT][NMACH];
            for (i = 0; i < NALT; i++) {
                for (j = 0; j < NMACH; j++) {
                    lmachaltstart[i][j] = -1;
                    lmachaltend[i][j] = -1;
                }
            }
// scan through lines until mach and alt are found.
            for (iline = 0; iline < Nlines; iline++) {
                if (lines[iline].length() > 40) {
                    if (lines[iline].contains("AUTOMATED STABILITY AND CONTROL METHODS PER APRIL 1976 VERSION OF DATCOM")) {
                        for (int ii = 1; ii < 10; ii++) {
                            if (lines[iline + ii].contains("  MACH    ALTITUDE   VELOCITY")) {
                                printMACH = lines[iline + ii + 3].substring(1, 7);
                                try {
                                    fMACHdata = DUtil.GetDoublefromString(printMACH);
                                } catch (Exception ex) {
                                    Logger.getLogger(dd2jsbAeroData.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                printALT = lines[iline + ii + 3].substring(8, 18);
                                try {
                                    fALTdata = DUtil.GetDoublefromString(printALT);
                                } catch (Exception ex) {
                                    Logger.getLogger(dd2jsbAeroData.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                for (i = 0; i < NALT; i++) {
                                    for (j = 0; j < NMACH; j++) {
                                        if (lmachaltstart[i][j] == -1) {
                                            if ((DUtil.equals(fALT[i], fALTdata, 1.0)
                                                    && DUtil.equals(fMACH[j], fMACHdata, 0.01))) {
                                                lmachaltstart[i][j] = iline + 1;
                                            }
                                        }
                                        if ((lmachaltstart[i][j] != -1) && (lmachaltend[i][j] == -1)) {
                                            if (!((DUtil.equals(fALT[i], fALTdata, 1.0)
                                                    && DUtil.equals(fMACH[j], fMACHdata, 0.01)))) {
                                                lmachaltend[i][j] = iline;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } //end of scanning lines for ALT and MACH

            // terminate last mach alt
            if (lmachaltstart[NALT - 1][NMACH - 1] > -1) {
                lmachaltend[NALT - 1][NMACH - 1] = Nlines - 1;
            }

            for (i = 0; i < NALT; i++) {
                for (j = 0; j < NMACH; j++) {
                    if (lmachaltend[i][j] > lmachaltstart[i][j]) {
                        jTextArea1.append("Processing Mach  :" + MACH[j] + "  Alt  :" + ALT[i] + newline);
                        String aString = Integer.toString(lmachaltstart[i][j]);
                        String bString = Integer.toString(lmachaltend[i][j]);
                        String cString = Integer.toString(Nlines);
                        jTextArea1.append("Processing MAdata  lines :" + aString + "  " + bString + " of " + cString + newline);
                        String MAdata = "";
                        int l;
                        for (l = lmachaltstart[i][j]; l <= lmachaltend[i][j]; l++) {
                            MAdata = MAdata + lines[l] + "\n";
                        }
                        aerodat.grabParameters(MAdata, ALT, MACH, i, j);
                    }
                }
            }
            jTextArea1.append("Write MASTER" + newline);
            aerodat.writemaster(CaseDir, ALT, MACH, NALT, NMACH, NALPHA);
        }
        jTextArea1.append("Finished Processing" + newline);
        // end of loop around Datcom output Cases
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // if the user presses the BACK button, the previous frame is shown
        setVisible(false);
        {
            mainPanel.setVisible(true);
            mainClass.setComponent(mainPanel);
        }
    }//GEN-LAST:event_jButton3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    private String path;
    private String workingPath;
    private String caseID;
    private String aero;
    private dd2jsbUtils DUtil;
    private dd2jsbRunCmd execCmd = new dd2jsbRunCmd();
    private dd2jsbAeroData aerodat;
    private dd2jsbMAIN mainClass;
    private JPanel mainPanel;
    //temp dd2jsbAeroData inputs
    private String[] ALT;
    private String[] MACH;
    //END temp dd2jsbAeroData inputs
    private final static String newline = "\n\r";
}
