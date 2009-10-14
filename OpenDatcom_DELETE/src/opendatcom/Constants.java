/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to contain all constant values used in the program. Java does not
 * support constant globals so this class is necessary.
 * @author -B-
 */
public class Constants {
    // Flight Profiles
     static ArrayList defaultFlightProfiles = new ArrayList();
    static ArrayList defaultFlightProfileNames = new ArrayList();

    public Constants()
    {
        try {
            readDataFiles();
        } catch (IOException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void readDataFiles() throws IOException
    {
        String Buffer = null;
        String temp = null;
        String profileHolder [];
        String blockHolder [];
        String dataHolder [];
        String FilePath = new java.io.File(".").getCanonicalPath() + "\\DefaultFlightProfiles.cfg";
        System.out.println(FilePath);
        BufferedReader input = new BufferedReader(new FileReader(FilePath));
        while(input.ready())
        {
            temp = input.readLine();
            // Toss away the comments...
            if(temp.contains("#")){}
            else
            {
                Buffer += temp;
            }
        }
        input.close();
        profileHolder = Buffer.split("!");
        // At this point the file is divided into header sections, ie Default is
        // together in one block, General Aviation is in another.. etc
        for(int i = 1; i < profileHolder.length; i++)
        {
            blockHolder = profileHolder[i].split(":");
            // Strip the profile header out and add it to the name list
            defaultFlightProfileNames.add(profileHolder[i]);
        }


    }
}
