/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 * The main class contains all the Datcom Data. Eventually it will replace/wrap
 * all parsing methods. 
 * @author -B-
 */
public class instanceData {
    // Root frame reference, IMPORTANT!
    RootFrame root;

    String aggrateData = null;
    String aggrateDataWithComments = null;

    // FlightCon datat
    static String flightHeader;
    static String machs;
    static String altitudes;
    static String aoas;

    long weight = 15000;
    int loop = 1;
    int nMach = 4;
    int nAlt = 3;
    int nAOA = 2;

    boolean isValid;

    public instanceData(RootFrame Root)
    {
        root = Root;
    }
    public void refresh()
    {
        // Set the correct count variables
        String dummy = machs;
        nMach = dummy.split(",").length;
        dummy = altitudes;
        nAlt = dummy.split(",").length;
        dummy = aoas;
        nAOA = dummy.split(",").length;


        // This isnt right, just using data for testing.
        aggrateData = "CASEID TEST TEST\n\n";
        aggrateData += "#" + flightHeader + "\n";
        aggrateData += "$FLTCON\n";
        aggrateData += "NMACH=" + String.valueOf(nMach) + ".0,\n";
        aggrateData += "MACH(1)=\n";
        aggrateData += machs + "\n\n";
        aggrateData += "NALPHA=" + String.valueOf(nAOA)+ ".0,\n";
        aggrateData += "ALSCHD(1)=,\n";
        aggrateData += aoas + "\n\n";
        aggrateData += "NALT=" + String.valueOf(nAlt) + ".0,\n";
        aggrateData += "ALT(1)=,\n";
        aggrateData += altitudes + "\n\n";
        aggrateData += "WT= " + weight + ".0,\n";
        aggrateData += "LOOP= " + loop + ".0,\n";
        aggrateData += "$\n";
        aggrateData += "# End of flight conditions\n";
    }
    public String getData()
    {
        refresh();
        return aggrateData;
    }
    public String getDataComments()
    {
        refresh();
        return aggrateDataWithComments;
    }
}
