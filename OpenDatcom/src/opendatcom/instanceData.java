/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;


/**
 * The main class contains all the Datcom Data. This should probably be split into
 * smaller, more manageable classes but for now it serves it's purpose. There is
 * a single instance of this class declared during program execution that serves
 * as a global variable.
 * @author -B-
 */
public class instanceData {

    // TODO: Singleton

    String aggrateData = null;
    String aggrateDataWithComments = null;

    // Model Class variables
    FlightSurfaceModel hwPlanform;
    FlightSurfaceModel htPlanform;
    FlightSurfaceModel vtPlanform;
    SynthesisModel synthesisData;
    FlightConditionsModel flightData;
    BodyModel    bodyData;

    boolean isValid;

    public instanceData()
    {
        // Don't really need to declare these here because they are assigned later
        // but doing so to avoid ANY potential problems.

        // Initialize the singleton model classes
        hwPlanform = new FlightSurfaceModel(FlightSurfaceModel.SURFACE_TYPE.MAIN_WING);
        htPlanform = new FlightSurfaceModel(FlightSurfaceModel.SURFACE_TYPE.HORIZONTAL_TAIL);
        vtPlanform = new FlightSurfaceModel(FlightSurfaceModel.SURFACE_TYPE.VERTICAL_TAIL);
        synthesisData = SynthesisModel.getInstance();
        flightData = FlightConditionsModel.getInstance();
        bodyData = BodyModel.getInstance();
    }

    public void refresh()
    {
        /*
        // Set the correct count variables
        nMach = 0;
        nAlt = 0;
        nAOA = 0;
        
        if((!machs.isEmpty()) && (!altitudes.isEmpty()) && (!aoas.isEmpty()))
        {
            String dummy = machs;
            nMach = dummy.split(",").length;
            dummy = altitudes;
            nAlt = dummy.split(",").length;
            dummy = aoas;
            nAOA = dummy.split(",").length;
        }

        //validateData();
        
        createFLTCON();
        createSYNTH();
        createPlanform("WGPLNF", hwPlanform);
        createPlanform("HTPLNF", htPlanform);
        createBODY();
    }

        
    /**
     * All the safeAdd functions take the input data and format  it the following
     * way: <  Header \t Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and rejected if invalid. If valid, it is
     * appended to the end of the aggragateData string.
     * @param Header
     * @param Data
     */
        /*
    private void safeAdd(String Header, double Data)
    {
        if(Double.isNaN(Data))
        {
            return;
        }
        aggrateData += Header + "\t" +  Data + ",\n";
    }


    /**
     * All the safeAdd functions take the input data and format  it the following
     * way: <  Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and rejected if invalid. If valid, it is
     * appended to the end of the aggragateData string.
     * @param Data
     */
    }
    private void safeAdd(String Data)
    {
        if(Data.isEmpty())
        {
            return;
        }
        aggrateData += Data + "\n";
    }

    /**
     * All the safeAdd functions take the input data and format  it the following
     * way: <  Header \t Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and rejected if invalid. If valid, it is
     * appended to the end of the aggragateData string.
     * @param Header
     * @param Data
     */
    private void safeAdd(String Header, String Data)
    {
        if(Data.isEmpty())
        {
            return;
        }
        aggrateData += Header + "\t" + Data + ",\n";
    }

}
