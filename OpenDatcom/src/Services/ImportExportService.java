/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Abstracts.AbstractService;
import Abstracts.AbstractController;
import opendatcom.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class provides XML reading/writing functionality to registered controllers. It
 * also allows retroing parsed data back into the correct form/text fields.
 * @author -B-
 */
public class ImportExportService extends AbstractService{
    private static ImportExportService self;
    BufferedReader in;
    String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

    private ImportExportService()
    {
        name = "ImportExport";
        controllers = new LinkedList<AbstractController>();
        registerForMe();
    }

    /**
     * Parses a text file into a string.
     * @param inputFile The file to parse.
     * @return A string containing all parsed data.
     */
    public String importFile(File inputFile)
    {
       String temp = "";
        try {
            BufferedReader input = new BufferedReader(new FileReader(inputFile));
            while(input.ready())
            {
                temp += input.readLine() + "\n";
            }
        }

        catch (FileNotFoundException ex) // Catch file not found errors
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (IOException ex)  // Catch IO errors reading the stream
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public void writeXML(File inputFile)
    {        
        String temp = "";
        System.out.println("Writing XML");
        try {
           if(!inputFile.exists())
           {
                inputFile.createNewFile();
           } 
           BufferedWriter output = new BufferedWriter(new FileWriter(inputFile));
           temp += parent.generateXML();
           for(int x = 0; x < controllers.size(); x++)
           {
                controllers.get(x).refresh();
                System.out.println("Saving: " + controllers.get(x).getName());
                temp += controllers.get(x).generateXML();
           }
           String [] newlineTempCauseJavaSucks = temp.split("\n");
           output.write(xmlHeader);
           output.newLine();
           output.write("<DATCOM>");
           for(int x = 0; x < newlineTempCauseJavaSucks.length; x++)
           {
               output.write(newlineTempCauseJavaSucks[x]);
               output.newLine();
           }
           System.out.println("Saved as: " + inputFile.getName());
           output.write("</DATCOM>");
           output.close();
        }

        catch (FileNotFoundException ex) // Catch file not found errors
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (IOException ex)  // Catch IO errors reading the stream
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Writes data to the destination file.
     * @param destination
     * @param data
     */

    public void writeFile(File destination, String data)
    {
        String[] temp = data.split("\n");
       
        try {
            destination.mkdirs();
            destination.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(destination));
            for (int i = 0; i < temp.length; i++) {
                output.write(temp[i]);
                output.newLine();
            }
            output.close();
        }

        catch (FileNotFoundException ex) // Catch file not found errors
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (IOException ex)  // Catch IO errors reading the stream
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Compatability wrapper for Aleksey's JSBSim stuff.
     * @param path
     * @param fileName
     * @param data
     */
    public void writeFile(String path, String fileName, String data)
    {
        File destination = new File(path + "\\" + fileName);
        writeFile(destination, data);
    }

    public static ImportExportService getInstance()
    {
        if(self == null)
        {
            self = new ImportExportService();
        }
        return self;
    }
}
