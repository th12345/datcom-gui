/* Aleksey Matyushev
 *
 * This program is the sole property of Aleksey
 * Matyushev. This program is designed to be a GUI
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear.
 */

package datcom2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleksey
 */
public class C3PO {
    
    private String for005;
    private String for006;
    private String masterPrintCmalpha;
    private String masterPrintCdalpha;
    private String masterPrintClalpha;
    private String masterPrintCnalpha;
    private String masterPrintCaalpha;
    private String masterPrintXcpalpha;
    private String masterPrintClaalpha;
    private char[] for005array;
    private char[] for006array;
    //private DOMparser parse = new DOMparser();
    private getFor005 getFile;
    private String path;

    private String MACH;
    private String newMACH;
    private String ALT;
    private String newALT;

    private int initialStart = 0;
    private String MASTER = null;
    //private boolean test = false;

        //declare the eight arraytypes
    ArrayList    alphaTable;
    ArrayList    CdTable;
    ArrayList    ClTable;
    ArrayList    CmTable;
    ArrayList    CnTable;
    ArrayList    CaTable;
    ArrayList    XcpTable;
    ArrayList    ClaTable;

    ArrayList Cmalpha;
    ArrayList Cdalpha;
    ArrayList Clalpha;
    ArrayList Cnalpha;
    ArrayList Caalpha;
    ArrayList Xcpalpha;
    ArrayList Claalpha;

    public C3PO(String for005){
    this.for005 = for005;

    }

    public String alterFile(String newMACHs, String newALTs){
        this.newMACH =null;
        this.newALT = null;

        this.newMACH = newMACHs;
        this.newALT  = newALTs;
        getFile = new getFor005();

        for005array = for005.toCharArray();
        //change the MACH number
        for (int i=0; for005array.length > i; i++ ){
            if(for005array[i] == 'M' && for005array[i+1] == 'A'&& for005array[i+2] == 'C' && for005array[i+3] == 'H'
                    && for005array[i-1] != 'N' && for005array[i-1] != 'T' && for005array[i-1] != 'S'){
                //System.out.println("I found a MACH NUMBER!!!!!! :) :) :) :) ");

                //alter the MACH number
                int k = 0;
                for(int j = 8; for005array[i+j] != ','; j++){
                    if (MACH == null){
                        //System.out.println("Character is : "  + for005array[i+j]);
                        MACH = Character.toString(for005array[i+j]);

                        //replace the MACH number
                        for005array[i+j] = newMACH.charAt(k);
                        k++;
                    }
                    else{
                        //System.out.println("Character is : "  + for005array[i+j]);
                        MACH = MACH + Character.toString(for005array[i+j]);

                        //replace the MACH Number
                        for005array[i+j] = newMACH.charAt(k);
                        k++;
                    }//end else
                }//end for
            }//end if

        }//end for

        //change the ALTITUDE
        for (int i=0; for005array.length > i; i++ ){
            if(for005array[i] == 'A' && for005array[i+1] == 'L'&& for005array[i+2] == 'T'
                    && for005array[i-1] != 'N'){
                //System.out.println("I found an ALT NUMBER!!!!!! :) :) :) :) ");

                //alter the ALT number
                int k = 0;
                for(int j = 7; for005array[i+j] != ','; j++){
                    if (ALT == null){
                        //System.out.println("Character is : "  + for005array[i+j]);
                        ALT = Character.toString(for005array[i+j]);

                        //replace the MACH number
                        for005array[i+j] = newALT.charAt(k);
                        k++;
                    }
                    else{
                        //System.out.println("Character is : "  + for005array[i+j]);
                        ALT = ALT + Character.toString(for005array[i+j]);

                        //replace the MACH Number
                        for005array[i+j] = newALT.charAt(k);
                        k++;
                    }//end else
                }//end for
            }//end if

        }//end for

        System.out.println("THE MACH NUMBER IS = " + MACH);
        System.out.println("THE ALT NUMBER IS = " + ALT);

        for005 = new String(for005array);

        return for005;
    }//end alterFile

    public ArrayList grabParameters(String workPath){
        //parse.parseXML(path);
        path = workPath + "/for006.dat";
        ArrayList JSBtables = new ArrayList();
        alphaTable = new ArrayList();
        CdTable = new ArrayList();
        ClTable = new ArrayList();
        CmTable = new ArrayList();
        CnTable = new ArrayList();
        CaTable = new ArrayList();
        XcpTable = new ArrayList();
        ClaTable = new ArrayList();
        getFile = new getFor005();

        for006 = null;
        for006array = null;

        try {
            for006 = getFile.getText(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(C3PO.class.getName()).log(Level.SEVERE, null, ex);
        }

        for006array = for006.toCharArray();
        int j;
        int lineBreaks =0;
        boolean openInput = false;
        int skip = 0;
        int position = 0;

        //grab values from for006
        for (int i = 0; i < for006array.length ; i ++){
            if (for006array[i] == '\n'){
                lineBreaks++;
                position =1;
                }
            if(lineBreaks >= 150)
            {
                //System.out.println("I can start to grab stuff ----------------->");
                //System.out.println(for006array[j]);
                 

                if (for006array[i] != ' '){
                    String temp = null;
                    //int position = 1;

                    for (j = 0; (i+j) < for006array.length && for006array[i+j] != ' '; j++)
                    {
                        if (temp != null)
                        {
                            temp = temp + for006array[i+j];
                        }//end if
                        else
                        {
                            temp = Character.toString(for006array[i+j]);
                           
                        }//end else

                    }//end for
                    i = i + j;                 

                    //check for CLB
                    if (temp.equals("CNB")){
                        openInput = true;
                        System.out.println("Opened the gates");
                    }

                    //close input
                    if(openInput == true && temp.equals("AUTOMATED") || temp.equals("ALPHA")){
                        openInput = false;
                    }//END IF

                    if (openInput == true){
                        //JSBtables.add(temp);
                        //increment the skip value
                        skip++;
                        
                        
                        if (skip >= 3){
                            if (position == 1)
                            {
                                alphaTable.add(temp);
                                //System.out.println(alphaTable.toString());
                                //position++;
                            }//end If
                            if (position == 2)
                            {
                                CdTable.add(temp);
                                //System.out.println(CdTable.toString());
                                //position++;
                            }//end If
                            if (position == 3)
                            {
                                ClTable.add(temp);
                                //System.out.println(ClTable.toString());
                                //position++;
                            }//end If
                            if (position == 4)
                            {
                                CmTable.add(temp);
                                //System.out.println(CmTable.toString());
                                //position++;
                            }//end If
                            if (position == 5)
                            {
                                CnTable.add(temp);
                                //System.out.println(CnTable.toString());
                                //position++;
                            }//end If
                            if (position == 6)
                            {
                                CaTable.add(temp);
                                //System.out.println(CaTable.toString());
                                //position++;
                            }//end If
                            if (position == 7)
                            {
                                XcpTable.add(temp);
                                //System.out.println(XcpTable.toString());
                                //position++;
                            }//end If
                            if (position == 8)
                            {
                                ClaTable.add(temp);
                                //System.out.println(ClaTable.toString());
                                //position = 0;
                            }//end If
                            position++;
                        }//end IF

                        if (temp.toCharArray()[temp.toCharArray().length -1 ] == '\n')
                            {
                                position =1;
                                System.out.println("RESET THE POSITION .........................." + temp);

                            }

                    }//END IF
                    //JSBtables.add(temp);
                    //System.out.println(temp);
                    //System.out.println(alphaTable.toString());
                        
                }//end if

            }//end if
            
        }//end for
        //test = true;
        //return the JSBtable
        return JSBtables;
    }//end grabParameters

    public void createMaster(String workingPath, String printMACH, String printALT, int key, String masterPath)
    {
        //path = workingPath;
        initialStart = key;
        MASTER = null;
        getFile = new getFor005();
        
        if (initialStart ==0)
        {
             ///Lift versus alpha
            Cmalpha  = new ArrayList();
            Cdalpha = new ArrayList();
            Clalpha = new ArrayList();
            Cnalpha = new ArrayList();
            Caalpha = new ArrayList();
            Xcpalpha = new ArrayList();
            Claalpha = new ArrayList();
            
            /*Cmalpha.add("<function name='aero/coefficient/Cmalpha'>\n\t" +
                    "<description>Pitch_moment_due_to_alpha</description>\n\t" +
                    "<product>\n\t\t" + "<table>\n\t\t\t" + "<tableData>");*/
            Cmalpha.add("<tableData breakPoint=\""+printALT+ "\">");
            Cdalpha.add("<tableData breakPoint=\""+printALT+ "\">");
            Clalpha.add("<tableData breakPoint=\""+printALT+ "\">");
            Cnalpha.add("<tableData breakPoint=\""+printALT+ "\">");
            Caalpha.add("<tableData breakPoint=\""+printALT+ "\">");
            Xcpalpha.add("<tableData breakPoint=\""+printALT+ "\">");
            Claalpha.add("<tableData breakPoint=\""+printALT+ "\">");


            MASTER= "\n\t\t" + printMACH;

            Cmalpha.add(MASTER);
            Cdalpha.add(MASTER);
            Clalpha.add(MASTER);
            Cnalpha.add(MASTER);
            Caalpha.add(MASTER);
            Xcpalpha.add(MASTER);
            Claalpha.add(MASTER);

            //test = false;
            for (int simba = 0;  simba < alphaTable.size();simba++){
                    //add to tables
                    MASTER = "\n" + alphaTable.get(simba) + "\t\t" + CmTable.get(simba);
                    Cmalpha.add(MASTER);

                    MASTER = "\n" + alphaTable.get(simba) + "\t\t" + CdTable.get(simba);
                    Cdalpha.add(MASTER);

                    MASTER = "\n" + alphaTable.get(simba) + "\t\t" + ClTable.get(simba);
                    Clalpha.add(MASTER);

                    MASTER = "\n" + alphaTable.get(simba) + "\t\t" + CnTable.get(simba);
                    Cnalpha.add(MASTER);

                    MASTER = "\n" + alphaTable.get(simba) + "\t\t" + CaTable.get(simba);
                    Caalpha.add(MASTER);

                    MASTER = "\n" + alphaTable.get(simba) + "\t\t" + XcpTable.get(simba);
                    Xcpalpha.add(MASTER);

                    MASTER = "\n" + alphaTable.get(simba) + "\t\t" + ClaTable.get(simba);
                    Claalpha.add(MASTER);
            }//end for
            
        }//end if


        if (initialStart >= 1 )
        {
             MASTER= "\t\t\t" + printMACH;

            Cmalpha.set(1, Cmalpha.get(1) + MASTER);
            Cdalpha.set(1, Cdalpha.get(1) + MASTER);
            Clalpha.set(1, Clalpha.get(1) + MASTER);
            Cnalpha.set(1, Cnalpha.get(1) + MASTER);
            Caalpha.set(1, Caalpha.get(1) + MASTER);
            Xcpalpha.set(1, Xcpalpha.get(1) + MASTER);
            Claalpha.set(1, Claalpha.get(1) + MASTER);


            for (int mufasa = 2;  mufasa <= (alphaTable.size() +1 );mufasa++){
                //store more values
                Cmalpha.set(mufasa, Cmalpha.get(mufasa) + "\t\t" + CmTable.get(mufasa-2));
                Cdalpha.set(mufasa, Cdalpha.get(mufasa) + "\t\t" + CdTable.get(mufasa-2));
                Clalpha.set(mufasa, Clalpha.get(mufasa) + "\t\t" + ClTable.get(mufasa-2));
                Cnalpha.set(mufasa, Cnalpha.get(mufasa) + "\t\t" + CnTable.get(mufasa-2));
                Caalpha.set(mufasa, Caalpha.get(mufasa) + "\t\t" + CaTable.get(mufasa -2));
                Xcpalpha.set(mufasa, Xcpalpha.get(mufasa) + "\t\t" + XcpTable.get(mufasa -2));
                Claalpha.set(mufasa, Claalpha.get(mufasa) + "\t\t" + ClaTable.get(mufasa -2));
                
            }
            //initialStart++;
        }
        initialStart++;

        String print = null;
        //write tables to files
        Cmalpha.add("\n\t\t\t</tableData>\n\t\t" +
                "</table>\n\t" + "</product>\n" + "</function>");
        for(int d = 0; d <= (alphaTable.size() +1); d++){
            if (print != null){
                print = print + Cmalpha.get(d);
            }
            else{
                print = (String)Cmalpha.get(d);
            }
        }
        getFile.writeFile(workingPath, "Cmalpha.txt", print);
        masterPrintCmalpha = masterPrintCmalpha + print;
        getFile.writeFile(masterPath, "MASTER_Cmalpha.txt", masterPrintCmalpha);

        print = null;
        //write tables to files
        for(int d = 0; d <= (alphaTable.size() +1); d++){
            if (print != null){
                print = print + Cdalpha.get(d);
            }
            else{
                print = (String)Cdalpha.get(d);
            }
        }
        getFile.writeFile(workingPath, "Cdalpha.txt", print);
        getFile.writeFile(masterPath, "MASTER_Cdalpha.txt", print);

        print = null;
        //write tables to files
        for(int d = 0; d <= (alphaTable.size() +1); d++){
            if (print != null){
                print = print + Clalpha.get(d);
            }
            else{
                print = (String)Clalpha.get(d);
            }
        }
        getFile.writeFile(workingPath, "Clalpha.txt", print);
        getFile.writeFile(masterPath, "MASTER_Clalpha.txt", print);

        print = null;
        //write tables to files
        for(int d = 0; d <= (alphaTable.size() +1); d++){
            if (print != null){
                print = print + Cnalpha.get(d);
            }
            else{
                print = (String)Cnalpha.get(d);
            }
        }
        getFile.writeFile(workingPath, "Cnalpha.txt", print);
        getFile.writeFile(masterPath, "MASTER_Cnalpha.txt", print);

        print = null;
        //write tables to files
        for(int d = 0; d <= (alphaTable.size() + 1); d++){
            if (print != null){
                print = print + Caalpha.get(d);
            }
            else{
                print = (String)Caalpha.get(d);
            }
        }
        getFile.writeFile(workingPath, "Caalpha.txt", print);
        getFile.writeFile(masterPath, "MASTER_Caalpha.txt", print);

        print = null;
        //write tables to files
        for(int d = 0; d <= (alphaTable.size() +1); d++){
            if (print != null){
                print = print + Xcpalpha.get(d);
            }
            else{
                print = (String)Xcpalpha.get(d);
            }
        }
        getFile.writeFile(workingPath, "Xcpalpha.txt", print);
        getFile.writeFile(masterPath, "MASTER_Xcpalpha.txt", print);

        print = null;
        //write tables to files
        for(int d = 0; d <= (alphaTable.size() +1); d++){
            if (print != null){
                print = print + Claalpha.get(d);
            }
            else{
                print = (String)Claalpha.get(d);
            }
        }
        getFile.writeFile(workingPath, "Claalpha.txt", print);
        getFile.writeFile(masterPath, "MASTER_Claalpha.txt", print);

        
        //System.out.println(Cmalpha.toString());
        //System.out.println(Clalpha.toString());
        //System.out.println(Cdalpha.toString());

        //System.out.println(MASTER);
    }
}
