/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import opendatcom.OpenDatcomController;

/**
 * Class provides easy file loading and saving and handles associated io exceptions.
 * Implemented statically.
 * @author -B-
 */
public class ImportExportService{
    private static ImportExportService self;
    private ImportExportService(){}

    /**
     * Shows a JFileChooser and allows the user to choose which file to open.
     * After a valid file is chosen, it calls importFile on the selected file
     * and returns the parsed file data.
     * @return A string containing all parsed data.
     */
    public String importFile_FC()
    {
        File inFile;
        OpenDatcomController parent = OpenDatcomController.getInstance();
        int check = parent.getFc().showOpenDialog(null);
        if(check == JFileChooser.APPROVE_OPTION)
        {
            inFile = parent.getFc().getSelectedFile();
            return importFile(inFile);
        }
        return "";
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
            StreamService.printToStream("File not found exception: " + ex.getCause(), "err");
        }        catch (IOException ex)  // Catch IO errors reading the stream
        {
            StreamService.printToStream("IO Exception: " + ex.getCause(), "err");
        }
        return temp;
    }

    /**
     * Writes data to the destination file.
     * @param destination
     * @param data
     */
    public void writeFile(File destination, String data)
    {
        String newline = System.getProperty("line.separator");
        data = data.replaceAll("\n", newline);
       
        try {
            destination.delete();
            destination.createNewFile();
            BufferedWriter output = new BufferedWriter(new FileWriter(destination));
            output.write(data);
            output.close();
        }

        catch (FileNotFoundException ex) // Catch file not found errors
        {
           StreamService.printToStream("File not found exception: " + ex.getCause(), "err");
        }        catch (IOException ex)  // Catch IO errors reading the stream
        {
            StreamService.printToStream("IO Exception: " + ex.getCause(), "err");
        }
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
