/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FLTCON_Component;

import Abstracts.AbstractController;
import opendatcom.*;
import Services.ImportExportService;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author -B-
 */
public class FlightConditionsController extends AbstractController {

    // Variables
    String wingType;

    FlightConditionsModel model;
    FlightConditionsView view;

    ImportExportService in;

    //TODO: Have these values parsed in froma  text file
    double [] roughValues = { 0 , 0.00002, .00016, .00025, .00040, .0012, 006, .01 };

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public FlightConditionsController() {
        this.view = new FlightConditionsView(this);
        this.model = FlightConditionsModel.getInstance();
        this.xmlTag = "FLTCON";
        this.name = "Flight Conditions";
        in = ImportExportService.getInstance();
        registerWithService("ImportExport");
        registerForMe();
    }

    /**
     * Gathers input data from the view and updates the model
     */
    @Override
    public void gatherData()
    {
        model.setMachs(util.processTextField(view.getjMachText()));
        model.setAltitudes(util.processTextField(view.getjAltText()));
        model.setAoas(util.processTextField(view.getjAOAText()));
        model.setWeight(util.processDataField(view.getjWeightText()));
        model.setStMach(util.processDataField(view.getjSTMachText()));
        model.setTsMach(util.processDataField(view.getjTSMachText()));
        model.setGamma(util.processDataField(view.getjGammaText()));
        model.setTr(util.processDataField(view.getjTRText()));
        model.setBlref(util.processDataField(view.getjBlrefText()));
        model.setCbarr(util.processDataField(view.getjCbarrText()));
        model.setSref(util.processDataField(view.getjSrefText()));
        model.setLoop(util.processDataField(view.getjLoop()));


        String [] temp = model.getMachs().split(",");
        model.setnMach(temp.length);
        temp = model.getAltitudes().split(",");
        model.setnAlt(temp.length);
        temp = model.getAoas().split(",");
        model.setnAOA(temp.length);

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
        temp += generateFCON();
        temp += generateOPTS();
        return temp;
    }

    private String generateOPTS()
    {
        String temp = "";
        //temp += util.safeAdd("  ROUGFC=", roughValues[view.getjRoughness().getSelectedIndex()]);
        temp += util.safeAdd("  SREF=", model.getSref());
        temp += util.safeAdd("  CBARR=", model.getCbarr());
        temp += util.safeAdd("  BLREF=", model.getBlref());

        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of Option Data\n $OPTINS\n" + temp;
            temp += "$\n#End of Option Data\n\n";

            // Set the output back to the model
            view.setOutputData(temp);
        }
        return temp;
    }

    private String generateFCON()
    {
        String temp = "";
        temp += util.safeAdd("  NMACH=", model.getnMach());
        temp += util.safeAdd("  MACH(1)=" , model.getMachs());
        temp += util.safeAdd("  NALPHA=" , model.getnAOA());
        temp += util.safeAdd("  ALSCHD(1)=", model.getAoas());
        temp += util.safeAdd("  NALT=", model.getnAlt());
        temp += util.safeAdd("  ALT(1)= ", model.getAltitudes());
        temp += util.safeAdd("  WT= ", model.getWeight());
        temp += util.safeAdd("  LOOP= ", model.getLoop());

        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of Flight Condition Data\nCASEID " + parent.getCaseName()
                    + "\n $FLTCON\n" + temp;
            temp += "$\n#End of Flight Condition Data\n\n";

            // Set the output back to the model
            view.setOutputData(temp);
        }
        return temp;
    }

    @Override
    public void refreshFromSaved(String data)
    {
        // Parse the fcon section
        String section = util.xmlParse(xmlTag, data);
        if(!section.isEmpty())
        {
            view.getjMachText().setText(util.xmlParse("MACH", section));
            view.getjAOAText().setText(util.xmlParse("AOA", section));
            view.getjAltText().setText(util.xmlParse("ALT", section));
            view.getjSTMachText().setText(util.xmlParse("ST", section));
            view.getjTSMachText().setText(util.xmlParse("TS", section));
            view.getjTRText().setText(util.xmlParse("TR", section));
            view.getjGammaText().setText(util.xmlParse("GAMMA", section));
            view.getjWeightText().setText(util.xmlParse("WEIGHT", section));
            view.getjLoop().setText(util.xmlParse("LOOP", section));
        }

        // Parse the options section
        section = util.xmlParse("OPTINS", data);
        if(!section.isEmpty())
        {
            view.getjBlrefText().setText(util.xmlParse("BLREF", section));
            view.getjSrefText().setText(util.xmlParse("SREF", section));
            view.getjCbarrText().setText(util.xmlParse("CBARR", section));
            String temp = util.xmlParse("ROUGFC", section);
            for(int i = 0; i < view.getjRoughness().getItemCount(); i++)
            {
                if(roughValues[i] == Double.parseDouble(temp))
                {
                    view.getjRoughness().setSelectedIndex(i);
                    break;
                }
            }
        }

        // Refresh everything
        refresh();
    }

    @Override
    public String generateXML() {
        String temp = "";
        temp += writeOPTS();
        temp += writeFCON();
        return temp;
    }

    private String writeFCON()
    {
        String temp = "";
        temp += "<" + xmlTag + ">\n";
        temp+= util.xmlWrite("ALT", model.getAltitudes());
        temp+= util.xmlWrite("AOA", model.getAoas());
        temp+= util.xmlWrite("MACH", model.getMachs());
        temp+= util.xmlWrite("GAMMA", model.getGamma());
        temp+= util.xmlWrite("ST", model.getStMach());
        temp+= util.xmlWrite("TS", model.getTsMach());
        temp+= util.xmlWrite("TR", model.getTr());
        temp+= util.xmlWrite("WEIGHT", model.getWeight());
        temp+= util.xmlWrite("LOOP", model.getLoop());
        temp += "</" + xmlTag + ">\n";
        return temp;
    }

    private String writeOPTS()
    {
        String temp = "";
        temp += "<OPTINS>\n";
        temp+= util.xmlWrite("ROUGFC", roughValues[view.getjRoughness().getSelectedIndex()]);
        temp+= util.xmlWrite("SREF", model.getSref());
        temp+= util.xmlWrite("CBARR", model.getCbarr());
        temp+= util.xmlWrite("BLREF", model.getBlref());
        temp += "</OPTINS>\n";
        return temp;
    }

    public FlightConditionsModel getModel() {
        return model;
    }

    @Override
    public FlightConditionsView getView() {
        return view;
    }
}
