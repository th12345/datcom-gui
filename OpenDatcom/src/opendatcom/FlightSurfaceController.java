/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public class FlightSurfaceController extends AbstractController {
    
    // Utilities
    ParserUtility util = new ParserUtility();

    // Variables
    String wingType;
    FlightSurfaceModel model;
    FlightSurfaceView view;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE type) {
        this.view = new FlightSurfaceView(type, this);
        this.model = new FlightSurfaceModel(type);
        wingType = getSurfaceType();
        this.xmlTag = wingType;
    }

    /**
     * Gathers input data from the view and updates the model
     */
    @Override
    public void gatherData()
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
    @Override
    public void refresh()
    {
        gatherData();
        generateOutput();
    }

    /**
     * Creates and formats the datcom data.
     */
    @Override
    public String generateOutput()
    {
        String temp = "";
        temp += safeAdd("CHRDTP=", model.getCHRDTP());
        temp += safeAdd("SSPNOP=", model.getSSPNOP());
        temp += safeAdd("SSPNE=", model.getSSPNE());
        temp += safeAdd("SSPN=", model.getSSPN());
        temp += safeAdd("CHRDBP=", model.getCHRDBP());
        temp += safeAdd("CHRDR=", model.getCHRDR());
        temp += safeAdd("SAVSI=", model.getSAVSI());
        temp += safeAdd("CHSTAT=", model.getCHSTAT());
        temp += safeAdd("TWISTA=", model.getTWISTA());
        temp += safeAdd("DHDADI=", model.getDHDADI());
        temp += safeAdd("TYPE=", model.getTYPE());
       
        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of " + wingType + " data\n$" + wingType + "\n" + temp;
            temp += "$\n#End of " + wingType + " data\n\n";

            // Set the output back to the model
        }
        return temp;
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

    public FlightSurfaceModel getModel() {
        return model;
    }

    public FlightSurfaceView getView() {
        return view;
    }

    @Override
    public void refreshFromSaved(String data) {
        String section = util.xmlParse(xmlTag, data);
        if(section.isEmpty())
        {
            return;
        }
        view.getjAirfoil_Text().setText(util.xmlParse("AIRFOIL", section));
        view.getjCHRDBP_Text().setText(util.xmlParse("CHRDBP", section));
        view.getjCHRDR_Text().setText(util.xmlParse("CHRDR", section));
        view.getjCHRDTP_Text().setText(util.xmlParse("CHRDTP", section));
        view.getjCHSTAT_Text().setText(util.xmlParse("CHSTAT", section));
        view.getjDHADO_Text().setText(util.xmlParse("DHADO", section));
        view.getjDHDADI_Text().setText(util.xmlParse("DHDADI", section));
        view.getjSAVSI_Text().setText(util.xmlParse("SAVSI", section));
        view.getjSAVSO_Text().setText(util.xmlParse("SAVSO", section));
        view.getjSSPNE_Text().setText(util.xmlParse("SSPNE", section));
        view.getjSSPNOP_Text().setText(util.xmlParse("SSPNOP", section));
        view.getjSSPN_Text().setText(util.xmlParse("SSPN", section));
        view.getjTWISTA_Text().setText(util.xmlParse("TWISTA", section));
        view.getjTYPE_Text().setText(util.xmlParse("TYPE", section));
        refresh();
    }

    @Override
    public String generateXML() {
        
        String temp = "";
        temp += "<" + xmlTag + ">\n";
        temp+= util.xmlWrite("AIRFOIL", model.getAirfoil());
        temp+= util.xmlWrite("CHRDBP", model.getCHRDBP());
        temp+= util.xmlWrite("CHRDR", model.getCHRDR());
        temp+= util.xmlWrite("CHRDTP", model.getCHRDTP());
        temp+= util.xmlWrite("CHSTAT", model.getCHSTAT());
        temp+= util.xmlWrite("DHADO", model.getDHADO());
        temp+= util.xmlWrite("DHDADI", model.getDHDADI());
        temp+= util.xmlWrite("SAVSI", model.getSAVSI());
        temp+= util.xmlWrite("SAVSO", model.getSAVSO());
        temp+= util.xmlWrite("SSPN", model.getSSPN());
        temp+= util.xmlWrite("SSPNE", model.getSSPNE());
        temp+= util.xmlWrite("SSPNOP", model.getSSPNOP());
        temp+= util.xmlWrite("TWISTA", model.getTWISTA());
        temp+= util.xmlWrite("TYPE", model.getTYPE());
        temp += "</" + xmlTag + ">\n";
        return temp;
    }
}
