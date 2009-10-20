/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

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
public class ImportExportService{
    BufferedReader in;
    LinkedList<AbstractController> controllers;
    String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

    public ImportExportService()
    {
        controllers = new LinkedList<AbstractController>();
    }

    public void RegisterController(AbstractController target)
    {
        controllers.add(target);
    }

    public void importFile(File inputFile)
    {
       String temp = "";
        try {
            BufferedReader input = new BufferedReader(new FileReader(inputFile));
            while(input.ready())
            {
                temp += input.readLine();
            }
        }

        catch (FileNotFoundException ex) // Catch file not found errors
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (IOException ex)  // Catch IO errors reading the stream
        {
            Logger.getLogger(ImportExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parse(temp);
    }

    public void writeXML(File inputFile)
    {
        String temp = "";
        System.out.println("Writing XML");
        try {
           BufferedWriter output = new BufferedWriter(new FileWriter(inputFile));
           for(int x = 0; x < controllers.size(); x++)
           {
                controllers.get(x).refresh();
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

    private void Parse(String target)
    {
        for(int x = 0; x < controllers.size(); x++)
        {
            controllers.get(x).refreshFromSaved(target);
        }
    }
}
