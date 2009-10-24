/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;
/**
 *
 * @author aleksey
 */

//Given the working directory path, this class writes a BATCH file under
//Windows OS to run DATCOM.

public class writeBAT {
    private getFor005 makeFile;

    public writeBAT(){}

    public void writeFile (String path, String CASEID){
        String batchFile;
        makeFile = new getFor005();
        char[] pathArray = path.toCharArray();

        batchFile = "path = %path%;" + path + "\\bin" + "\n"
                + "::run datcom\n\n"
                + pathArray[0] + pathArray[1] + "\n"
                + "CD";

        for (int i = 2; i < (pathArray.length ); i++){
            batchFile = batchFile + pathArray[i];
        }
        batchFile = batchFile + "\\" + CASEID + "\n\ndatcom";
        //System.out.println(batchFile);
        makeFile.writeFile(path,"Datcom.bat" , batchFile);
    }//end makeFile

    public void writeFile (String path, String pathFile, String CASEID){
        String batchFile;
        makeFile = new getFor005();
        char[] pathArray = pathFile.toCharArray();
        System.out.println("The pathFile for batch is : " + pathFile);
        batchFile = "path = %path%;" + path + "\\bin" + "\n"
                + "::run datcom\n\n"
                + pathArray[0] + pathArray[1] + "\n"
                + "CD";

        for (int i = 2; i < (pathArray.length ); i++){
            batchFile = batchFile + pathArray[i];
        }
        batchFile = batchFile  + "\n\ndatcom";
        //System.out.println(batchFile);
        makeFile.writeFile(path,"Datcom.bat" , batchFile);
    }//end makeFile
}
