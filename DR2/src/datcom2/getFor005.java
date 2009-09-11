/* Aleksey Matyushev
 *
 * This program is the sole property of Aleksey
 * Matyushev. This program is designed to be a GUI
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear.
 */

package datcom2;

import java.io.*;
import java.util.ArrayList;


public class getFor005 {

    private String textFromFile;
    public getFor005() {};
    
    public String getText(String filePath) throws FileNotFoundException{
    File file = new File(filePath);
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;

    try {
      fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);

      // dis.available() returns 0 if the file does not have more lines.
      while (dis.available() != 0) {

      // this statement reads the line from the file and print it to the console


        if (textFromFile == null){
            textFromFile = dis.readLine() + "\n";
        }
        else {
            textFromFile = textFromFile + dis.readLine() + "\n";
        }   
      }

     textFromFile = textFromFile.trim();

      // dispose all the resources after using them.
      fis.close();
      bis.close();
      dis.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return textFromFile;
    }

    public void writeFile(String path, String filename, String content){
     BufferedWriter bufferedwriter = null;
     String seperator = System.getProperty("file.separator");
     PrintWriter printwriter = null;
     try{
          bufferedwriter = new BufferedWriter(
          new FileWriter(path+seperator+filename));
          printwriter = new PrintWriter(bufferedwriter);
          printwriter.println(content);
     }catch(IOException ex){
          ex.printStackTrace();
     }finally{
          printwriter.close();
     }
    }

    public String createFor005(String caseID, ArrayList FLTCON, ArrayList OPTINS, ArrayList SYNTHS, String BODY, ArrayList WGPLNF, ArrayList HTPLNF, ArrayList VTPLNF, ArrayList TERMINATION)
    {
        String for005 = "CASEID ---------"+ caseID + "-------------\n";

        if(FLTCON.isEmpty() == false)
        {
            //System.out.println("------------------------>FLTCON loop");
            
            for005 = for005 + " $FLTCON ";
            //System.out.println(for005);
            for(int i = 0; i < FLTCON.size(); i++)
            {
                for005 = for005 + FLTCON.get(i) + ",\n  ";
            }
            for005 = for005 + "LOOP=1.0$\n";
        }
        //System.out.println(for005);

        if(OPTINS.isEmpty() == false)
        {
            //System.out.println("------------------------>OPTINS loop");
            
            for005 = for005 + " $OPTINS ";
            for(int i = 0; i < OPTINS.size(); i++)
            {
                if(i == (OPTINS.size() - 1) ){
                   for005 = for005 + OPTINS.get(i) + "$\n";
                }
                else{
                   for005 = for005 + OPTINS.get(i) + ",\n  ";
                }
            }
            //end of OPTINS
            //System.out.println(for005);
        }

        if(SYNTHS.isEmpty() == false)
        {
            //System.out.println("------------------------>SYNTHS loop");

            for005 = for005 + " $SYNTHS ";
            for(int i = 0; i < SYNTHS.size(); i++)
            {
                if(i == (SYNTHS.size() - 1) ){
                   for005 = for005 + SYNTHS.get(i) + "$\n";
                }
                else{
                   for005 = for005 + SYNTHS.get(i) + ",\n  ";
                }
            }
            //end of SYNTHS
            //System.out.println(for005);
        }

        if(BODY.isEmpty() == false)
        {
            //System.out.println("------------------------>BODY loop");

            for005 = for005 + " $BODY \n";
            for005 = for005 + "  " + BODY + "\n";
            //end of BODY
            //System.out.println(for005);
        }

        if(WGPLNF.isEmpty() == false)
        {
            //System.out.println("------------------------>WGPLNF loop");

            for005 = for005 + " $WGPLNF ";
            for(int i = 0; i < (WGPLNF.size() -1); i++)
            {
                if(i == (WGPLNF.size() - 2) ){
                   for005 = for005 + WGPLNF.get(i) + "$\n";
                }
                else{
                   for005 = for005 + WGPLNF.get(i) + ",\n  ";
                }

            }
            for005 = for005 + WGPLNF.get(WGPLNF.size()-1) + "\n";
            //end of WGPLNF
            //System.out.println(for005);
        }

        if(HTPLNF.isEmpty() == false)
        {
            //System.out.println("------------------------>HTPLNF loop");

            for005 = for005 + " $HTPLNF ";
            for(int i = 0; i < (HTPLNF.size() -1); i++)
            {
                if(i == (HTPLNF.size() - 2) ){
                   for005 = for005 + HTPLNF.get(i) + "$\n";
                }
                else{
                   for005 = for005 + HTPLNF.get(i) + ",\n  ";
                }

            }
            for005 = for005 + HTPLNF.get(HTPLNF.size()-1) + "\n";
            //end of HTPLNF
            //System.out.println(for005);
        }

        if(VTPLNF.isEmpty() == false)
        {
            //System.out.println("------------------------>VTPLNF loop");

            for005 = for005 + " $VTPLNF ";
            for(int i = 0; i < (VTPLNF.size() -1); i++)
            {
                if(i == (VTPLNF.size() - 2) ){
                   for005 = for005 + VTPLNF.get(i) + "$\n";
                }
                else{
                   for005 = for005 + VTPLNF.get(i) + ",\n  ";
                }

            }
            for005 = for005 + VTPLNF.get(VTPLNF.size()-1) + "\n";
            //end of VTPLNF
            //System.out.println(for005);
        }

        if(TERMINATION.isEmpty() == false)
        {
            //System.out.println("------------------------>TERMINATION loop");
            for(int i = 0; i <= (TERMINATION.size() -1); i++)
            {
                for005 = for005 + TERMINATION.get(i) + "\n";
            }
        }
        for005 = for005 + "NEXT CASE";
        return for005;
    }
}
