/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Core.DataServer;
import Core.OAE_LinkedTable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import opendatcom.OpenDatcomController;

/**
 * Service that conditions the Datcom format to fit the requirements of JSBSim
 * dynamics files. It is an extension of the FortranFormat class and is slightly
 * modified to generate single Alt/mach FLTCON namelists.
 * @see FortranFormat
 * @author -B-
 */
public class JSBSimExporter extends FortranFormat
{
    double alt;
    double mach;
    double nAoas;
    OAE_LinkedTable aoas;
    Vector<File> filesGenerated;
    File defaultFor005;
    
    public JSBSimExporter()
    {
        fu = FormatUtility.getInstance();
        alt = 0;
        mach = 0;
        nAoas = 0;
        filesGenerated = new Vector<File>(20);
    }

    @Override
    public String generateFLTCON()
    {
        String out = "";
        String header = " $FLTCON\n";
        String offset = "  ";
        out += offset + "NMACH=1.0,\n";
        out += offset + fu.datcomFormat("MACH=", mach);
        out += offset + "NALT=1.0,\n";
        out += offset + fu.datcomFormat("ALT=", alt);
        out += mapRef.get("ALSCHD").datcomFormat(offset);
        out += offset + "NALPHA=" + nAoas + ",\n";
        out += aoas.datcomFormat(offset);
        out += mapRef.get("WT").datcomFormat(offset);
        out += mapRef.get("STMACH").datcomFormat(offset);
        out += mapRef.get("TSMACH").datcomFormat(offset);
        out += mapRef.get("GAMMA").datcomFormat(offset);
        out += mapRef.get("TR").datcomFormat(offset);
        out += mapRef.get("LOOP").datcomFormat(offset);
        out = out.substring(0, out.length() - 2);
        out = header + out + "$\n";
        return out;
    }
    
    @SuppressWarnings("unchecked")
    public void generate()
    {
        File datFile = new File(OpenDatcomController.getInstance().getWorkingDirectory().getAbsolutePath() +"\\for005.dat");
        File outputDir = new File(OpenDatcomController.getInstance().getWorkingDirectory().getAbsolutePath() +"\\JSBSim\\");
        String outputHeader = "Output_";
        alt = 0;
        mach = 0;
        nAoas = 0;
        
        Vector<Double> alts = ((OAE_LinkedTable)DataServer.getLink("ALT")).getData();
        Vector<Double> machs = ((OAE_LinkedTable)DataServer.getLink("MACH")).getData();
        aoas = ((OAE_LinkedTable)DataServer.getLink("MACH"));
        nAoas = aoas.getSize();

        for (Iterator<Double> it = alts.iterator(); it.hasNext();) {
            alt = it.next();

            for (Iterator<Double> it1 = machs.iterator(); it1.hasNext();) {
                try {
                    mach = it1.next();
                    String temp = generateForFile();
                    temp += "DIM FT\n";
                    temp += "BUILD\n";
                    temp += "NEXT CASE";
                    temp.replaceAll("\t", " ");
                    ImportExportService.getInstance().writeFile(datFile, temp);
                    DatcomThread t = new DatcomThread();
                    t.start();
                    t.join();
                    moveForFiles(outputDir.getAbsolutePath() + "/" + alt + "/" + mach);

                } catch (InterruptedException ex) {
                    StreamService.print("JSBSim Failed");
                    Logger.getLogger(JSBSimExporter.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    /**
      * Moves all for0XX.dat files to the destionation path. Deletes originals.
      * @param dest The destination path.
      */
    private void moveForFiles(String dest)
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
                moveForDest = new File(dest + "/for00" + i +".dat");
            }
            else
            {
                moveForSource = new File("for0" + i + ".dat");
                moveForDest = new File(dest + "/for0" + i +".dat");
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
    private void processJSBSimData(File target)
    {
        String regexHeader = "0 ALPHA     ";
        String regexFooter = "0                                    ALPHA     ";
        String datcomPath = System.getProperty("user.dir") + "/Bin/Datcom/datcom.exe";
        try {
            Vector<Double> CD = new Vector<Double>();
            Vector<Double> CL = new Vector<Double>();
            Vector<Double> CM = new Vector<Double>();
            Vector<Double> CN = new Vector<Double>();
            Vector<Double> CA = new Vector<Double>();
            Vector<Double> XCP = new Vector<Double>();
            Vector<Double> CLA = new Vector<Double>();
            Vector<Double> CMA = new Vector<Double>();
            Vector<Double> CYB = new Vector<Double>();
            Vector<Double> CNB = new Vector<Double>();
            Vector<Double> CLB = new Vector<Double>();
            Vector<Double> alpha = new Vector<Double>();

            String data = ImportExportService.getInstance().importFile(target);
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
            writeVector(CD, alpha, target.getParent(), "CD.txt");
            writeVector(CL, alpha, target.getParent(), "CL.txt");
            writeVector(CN, alpha, target.getParent(), "CN.txt");
            writeVector(CM, alpha, target.getParent(), "CM.txt");
            writeVector(CA, alpha, target.getParent(), "CA.txt");
            writeVector(XCP, alpha, target.getParent(), "XCP.txt");
            writeVector(CLA, alpha, target.getParent(), "CLA.txt");
            writeVector(CMA, alpha, target.getParent(), "CMA.txt");
            writeVector(CYB, alpha, target.getParent(), "CYB.txt");
            writeVector(CNB, alpha, target.getParent(), "CNB.txt");
            writeVector(CLB, alpha, target.getParent(), "CLB.txt");

        } catch (IOException ex) {
            StreamService.print("JSBSimExporter encountered an IO error, aborting.");
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
    private void writeVector(Vector<Double> in, Vector<Double> alpha,
            String Path, String fileName) throws IOException
    {
        String output = Path + "/" + fileName;
        File dest = new File(output);
        output = "<tableData breakPoint=\"" + alt + "\">\n";
        dest.createNewFile();
        for (int i = 0; i < in.size(); i++) {
            output += alpha.get(i) + "\t" + in.get(i) + "\n";
        }
        ImportExportService.getInstance().writeFile(dest, output);
    }

}
