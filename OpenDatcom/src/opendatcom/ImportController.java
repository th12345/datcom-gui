/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author -B-
 */
public class ImportController implements AbstractController{
    BufferedReader in;

    @Override
    public void gatherData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String importFile(String Filename)
    {
        String temp = "";
        try {
            in = new BufferedReader(new FileReader(Filename));
            while(in.ready())
            {
                temp += in.readLine();
            }
        }
        catch (FileNotFoundException ex) // Catch file not found errors
        {
            Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } 
        catch (IOException ex)  // Catch IO errors reading the stream
        {
            Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return temp;
    }
    
    @Override
    public String generateOutput() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
