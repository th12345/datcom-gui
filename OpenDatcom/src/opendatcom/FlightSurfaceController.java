/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public class FlightSurfaceController {
    
    // Utilities
    ParserUtility util = new ParserUtility();

    // References
    FlightSurfaceView view;
    FlightSurfaceModel model;

    // Variables
    String wingType;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public FlightSurfaceController(FlightSurfaceView view, FlightSurfaceModel.SURFACE_TYPE type) {
        this.view = view;
        this.model = new FlightSurfaceModel(type);
        wingType = getSurfaceType();
    }

    /**
     * Gathers input data from the view and updates the model
     */
    private void gatherData()
    {
        model.setAirfoil(util.processTextField(view.getjAirfoil_Text()));
        model.setCHRDBP(util.processDataField(view.getjCHRDBP_Text()));
        model.setCHRDR(util.processDataField(view.getjCHRDR_Text()));
        model.setCHRDTP(util.processDataField(view.getjCHRDTP_Text()));
        model.setCHSTAT(util.processDataField(view.getjCHSTAT_Text()));
        model.setDHADO(util.processDataField(view.getjDHADO_Text()));
        model.setDHDADI(util.processDataField(view.getjDHDADI_Text()));
        model.setSAVSI(util.processDataField(view.getjSAVSI_Text()));
        model.setSAVSO(util.processDataField(view.getjSAVSO_Text()));
        model.setSSPN(util.processDataField(view.getjSSPN_Text()));
        model.setSSPNE(util.processDataField(view.getjSSPNE_Text()));
        model.setSSPNOP(util.processDataField(view.getjSSPNOP_Text()));
        model.setTWISTA(util.processDataField(view.getjTWISTA_Text()));
        model.setTYPE(util.processDataField(view.getjTYPE_Text()));
    }

    /**
     * Refreshes the entire model/view/controllers links & formats the datcom
     * output data.
     */
    public void refresh()
    {
        gatherData();
        createOutput();
    }

    /**
     * Creates and formats the datcom data.
     */
    private void createOutput()
    {
        String temp = "";
        safeAdd("CHRDTP=", model.getCHRDTP());
        safeAdd("SSPNOP=", model.getSSPNOP());
        safeAdd("SSPNE=", model.getSSPNE());
        safeAdd("SSPN=", model.getSSPN());
        safeAdd("CHRDBP=", model.getCHRDBP());
        safeAdd("CHRDR=", model.getCHRDR());
        safeAdd("SAVSI=", model.getSAVSI());
        safeAdd("CHSTAT=", model.getCHSTAT());
        safeAdd("TWISTA=", model.getTWISTA());
        safeAdd("DHDADI=", model.getDHDADI());
        safeAdd("TYPE=", model.getTYPE());
       
        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of " + wingType + " data\n$" + wingType + "\n" + temp;
            temp += "$\n#End of " + wingType + " data";

            // Set the output back to the model
            model.setOutput(temp);
        }
    }

    private String getSurfaceType()
    {
        String output = "";
        switch(model.getSurfaceType())
        {
            case MAIN_WING:
            {
                output = "WGPLNF";
                break;
            }
            case HORIZONTAL_TAIL:
            {
                output = "HTPLNF";
                break;
            }
            case VERTICAL_TAIL:
            {
                output = "VTPLNF";
                break;
            }
            case OTHER:
            {
                //TODO: Define the rest of the planform types
                output = "undefinted";
                break;
            }
        }
        view.setjTitle(output + " Parameters");
        return output;
    }

    /**
     * All the safeAdd functions take the input data and format  it the following
     * way: <  Header \t Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and rejected if invalid. If valid, it is
     * appended to the end of the aggragateData string.
     * @param Header
     * @param Data
     */
    private String safeAdd(String Header, double Data)
    {
        String output = "";
        if(Double.isNaN(Data))
        {
            return output;
        }
        output += Header + "\t" +  Data + ",\n";
        return output;
    }
}
