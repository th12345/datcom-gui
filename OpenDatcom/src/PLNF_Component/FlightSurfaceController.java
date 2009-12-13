/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PLNF_Component;

import Abstracts.AbstractController;
import Abstracts.MVC_DataLink;
import java.util.LinkedList;
import opendatcom.*;

/**
 *
 * @author -B-
 */
public class FlightSurfaceController extends AbstractController {
    
    // Variables
    String wingType;
    FlightSurfaceView view;
    FlightSurfaceModel model;

    SURFACE_TYPE surfaceType;

    public enum SURFACE_TYPE
    {
        MAIN_WING,
        HORIZONTAL_TAIL,
        VERTICAL_TAIL,
        VFPLNF,
        NUM_SURFACE_TYPES
    }

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public FlightSurfaceController(SURFACE_TYPE type) {
        surfaceType = type;
        this.view = new FlightSurfaceView(type, this);
        this.model = new FlightSurfaceModel();
        this.name = "FlightSurface";
        wingType = initSurfaceType();
        this.xmlTag = wingType;
        registerWithService("ImportExport");
        registerForMe();
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
        for (int i = 0; i < Links.size(); i++) 
        {
            temp += Links.get(i).datcomFormat(" ");
        }
        /*
        temp += util.safeFormat(" CHRDTP=", model.getCHRDTP());
        temp += util.safeFormat(" SSPNOP=", model.getSSPNOP());
        temp += util.safeFormat(" SSPNE=", model.getSSPNE());
        temp += util.safeFormat(" SSPN=", model.getSSPN());
        temp += util.safeFormat(" CHRDBP=", model.getCHRDBP());
        temp += util.safeFormat(" CHRDR=", model.getCHRDR());
        temp += util.safeFormat(" SAVSI=", model.getSAVSI());
        temp += util.safeFormat(" CHSTAT=", model.getCHSTAT());
        temp += util.safeFormat(" TWISTA=", model.getTWISTA());
        temp += util.safeFormat(" DHDADI=", model.getDHDADI());
        temp += util.safeFormat(" TYPE=", model.getTYPE());
       */
        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {            
            temp = temp.substring(0, temp.length() - 2);
            String header = "#Start of " + wingType + " data\n";
            if(true)
            {
                // Double space everything as per datcom standard
                temp = temp.replace(" ", "  ");
                header += model.getAirfoil() + "\n";
                temp = header +  " $" + wingType + "\n" + temp;
                temp += "$\n#End of " + wingType + " data\n\n";
                // Set the output back to the model
            }
            else
            {
                temp = header +  "$" + wingType + "\n" + temp;
                temp += "$\n#End of " + wingType + " data\n\n";
            }
        }
        return temp;
    }

    private String initSurfaceType()
    {
        String output = "";
        switch(surfaceType)
        {
            case MAIN_WING:
            {
                output    = "WGPLNF";
                this.name = "Wing";
                view.setIsHT(true);
                view.setIsV(false);
                break;
            }
            case HORIZONTAL_TAIL:
            {
                output = "HTPLNF";
                this.name = "Horizontal Tail";
                view.setIsHT(true);
                view.setIsV(false);
                break;
            }
            case VERTICAL_TAIL:
            {
                output = "VTPLNF";
                this.name = "Vertical Tail";
                view.setIsHT(false);
                view.setIsV(true);
                view.getjTWISTA_Text().setEnabled(false);
                view.getjDHDADI_Text().setEnabled(false);
                view.getjDHDADO_Text().setEnabled(false);
                view.getjSSPNDO().setEnabled(false);
                break;
            }
            case VFPLNF:
            {
                output = "VFPLNF";
                this.name = "???????";
                view.setIsHT(false);
                view.setIsV(true);
                view.getjTWISTA_Text().setEnabled(false);
                view.getjDHDADI_Text().setEnabled(false);
                view.getjDHDADO_Text().setEnabled(false);
                view.getjSSPNDO().setEnabled(false);
                break;
            }
        }
        view.setjTitle(output + " Parameters");
        return output;
    }

    public FlightSurfaceModel getModel() {
        return model;
    }

    @Override
    public FlightSurfaceView getView() {
        return view;
    }

    @Override
    public void refreshFromSaved(String data)
    {
        super.refreshFromSaved(data);
        String temp = util.xmlParse(xmlTag, data);
        view.setjTYPE((int)Double.parseDouble(util.xmlParse("TYPE", temp)));
    }

    @Override
    public String generateXML()
    {
        String temp = "";
        temp += "<" + xmlTag + ">\n";
        for (int i = 0; i < Links.size(); i++)
        {
            temp += Links.get(i).generateXML_Element();
        }
        temp += "</" + xmlTag + ">\n";
        return temp;
    }

    public String generateTemplate()
    {
        String hack = this.xmlTag;
        xmlTag = this.name + "_TEMPLATE";
        String temp = generateXML();
        this.xmlTag = hack;
        return temp;
    }
}
