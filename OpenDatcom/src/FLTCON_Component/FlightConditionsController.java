/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FLTCON_Component;

import Abstracts.AbstractController;
import Abstracts.OAE_LinkedTable;
import opendatcom.*;
import Services.ImportExportService;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author -B-
 */
public class FlightConditionsController extends AbstractController {

    // Variables
    String wingType;

    FlightConditionsView view;

    double nMach;
    double nAOA;
    double nALT;

    ImportExportService in;

    //TODO: Have these values parsed in froma  text file
    double [] roughValues = { 0 , 0.00002, .00016, .00025, .00040, .0012, 006, .01 };

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public FlightConditionsController() {
        this.view = FlightConditionsView.getInstance();
        this.xmlTag = "FLTCON";
        this.name = "Flight Conditions";
        in = ImportExportService.getInstance();
        registerWithService("ImportExport");
        registerForMe();

        createLink(new OAE_LinkedTable("AOA",  view.getjTable(), 0));
        createLink(new OAE_LinkedTable("ALT",  view.getjTable(), 1));
        createLink(new OAE_LinkedTable("MACH", view.getjTable(), 2));
        createLink("STMACH",     view.getjSTMach(), double.class);
        createLink("TSMACH", view.getjTSMach(), double.class);
        createLink("TR",     view.getjTR(),     double.class);
        createLink("WT", view.getjWeight(), double.class);
        createLink("GAMMA", view.getjGamma(), double.class);
        createLink("LOOP",   view.getjLoop(),   double.class);
    }

    /**
     * Gathers input data from the view and updates the model
     */
    @Override
    @SuppressWarnings("unchecked")
    public void gatherData()
    {
        super.refresh();
        
        nALT =  ((List<Double>)lookupValue("ALT" )).size();
        nAOA =  ((List<Double>)lookupValue("AOA" )).size();
        nMach = ((List<Double>)lookupValue("MACH")).size();
    }

    
    @Override
    public FlightConditionsView getView() {
        return view;
    }
}
