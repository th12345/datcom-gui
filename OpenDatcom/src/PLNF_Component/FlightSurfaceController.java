/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PLNF_Component;

import Abstracts.AbstractController;
import Abstracts.OAE_DataLink;
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
        String temp = super.generateOutput();
        temp.replaceAll("  ", "   ");

        // Lookup the airfoil value
        String airfoil = view.getjAirfoil_Text().getText();
        // If an aifoil isnt specified, dont print anything.
        if(airfoil.isEmpty())
        {
            return "";
        }
        // Append the airfoil to the start of the namelist, as per DATCOM requirements
        temp = airfoil +"\n"+ temp;
        return temp;
    }

    public  String initSurfaceType()
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

    @Override
    public FlightSurfaceView getView() {
        return view;
    }

    @Override
    public String generateXML()
    {
        String temp = "";
        temp += "<" + xmlTag + ">\n";
        temp += util.xmlWrite("AIRFOIL", view.getjAirfoil_Text().getText());
        for (int i = 0; i < Links.size(); i++)
        {
            temp += Links.get(i).generateXML_Element();
        }
        temp += "</" + xmlTag + ">\n";
        return temp;
    }

    @Override
    public void refreshFromSaved(String in)
    {
        super.refreshFromSaved(in);
        String section = util.xmlParse(xmlTag, in);
        view.getjAirfoil_Text().setText(util.xmlParse("AIRFOIL", section));
    }
}
