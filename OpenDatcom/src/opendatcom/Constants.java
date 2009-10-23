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
 * support constant globals so this class is necessary. For now, the class also
 * contains all data file parsing.
 * @author -B-
 */
public class Constants {
    // Flight Profiles
    ArrayList defaultFlightProfiles = new ArrayList();
    ArrayList defaultFlightProfileNames = new ArrayList();

    public Constants()
    {
       
    }

    private void readDataFiles() throws IOException
    {
        
    }

    public ArrayList getDefaultFlightProfileNames() {
        return defaultFlightProfileNames;
    }

    public void setDefaultFlightProfileNames(ArrayList defaultFlightProfileNames) {
        this.defaultFlightProfileNames = defaultFlightProfileNames;
    }

    public ArrayList getDefaultFlightProfiles() {
        return defaultFlightProfiles;
    }

    public void setDefaultFlightProfiles(ArrayList defaultFlightProfiles) {
        this.defaultFlightProfiles = defaultFlightProfiles;
    }
}
