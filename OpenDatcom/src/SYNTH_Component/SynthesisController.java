/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SYNTH_Component;

import Abstracts.AbstractController;
import opendatcom.*;

/**
 *
 * @author -B-
 */
public class SynthesisController extends AbstractController{

    SynthesisModel model;
    SynthesisView view;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public SynthesisController() {
        this.view = new SynthesisView(this);
        this.model = SynthesisModel.getInstance();
        this.xmlTag = "SYNTHESIS";
        this.name = "Synthesis";
        registerWithService("ImportExport");
        registerForMe();
    }

    /**
     * Gathers input data from the view and updates the model
     */
    @Override
    public void gatherData()
    {
        model.setALIH(util.processDataField(view.getjALIHText()));
        model.setALIW(util.processDataField(view.getjALIWText()));
        model.setXCG(util.processDataField(view.getjXCGText()));
        model.setZCG(util.processDataField(view.getjZCGText()));
        model.setXW(util.processDataField(view.getjXWText()));
        model.setZW(util.processDataField(view.getjZWText()));
        model.setXH(util.processDataField(view.getjXHText()));
        model.setZH(util.processDataField(view.getjZHText()));
        model.setXVF(util.processDataField(view.getjXVFText()));
        model.setZVF(util.processDataField(view.getjZVFText()));
        model.setXV(util.processDataField(view.getjXVText()));
        model.setZV(util.processDataField(view.getjZVText()));
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
        String temp;
        temp =  util.safeFormat("  XCG=", model.getXCG());
        temp += util.safeFormat("  ZCG=", model.getZCG());
        temp += util.safeFormat("  XW=", model.getXW());
        temp += util.safeFormat("  ZW=", model.getZW());
        temp += util.safeFormat("  ALIH=", model.getALIH());
        temp += util.safeFormat("  ALIW=", model.getALIW());
        temp += util.safeFormat("  XH=", model.getXH());
        temp += util.safeFormat("  ZH=", model.getZH());
        temp += util.safeFormat("  XV=", model.getXV());
        temp += util.safeFormat("  ZV=", model.getZV());

        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of Synthesis data\n $SYNTHS\n" + temp;
            temp += "$\n#End of Synthesis data\n\n";

            // Set the output back to the model
            view.setOutputData(temp);
        }
        return temp;
    }

    public SynthesisModel getModel() {
        return model;
    }

    @Override
    public SynthesisView getView() {
        return view;
    }

    @Override
    public void refreshFromSaved(String data) {
        String section = util.xmlParse(xmlTag, data);
        if(section.isEmpty())
        {
            return;
        }

        view.getjALIHText().setText(util.xmlParse("ALIH", section));
        view.getjALIWText().setText(util.xmlParse("ALIW", section));
        view.getjXCGText().setText(util.xmlParse("XCG", section));
        view.getjZCGText().setText(util.xmlParse("ZCG", section));
        view.getjXHText().setText(util.xmlParse("XH", section));
        view.getjZHText().setText(util.xmlParse("ZH", section));
        view.getjXVText().setText(util.xmlParse("XV", section));
        view.getjZVText().setText(util.xmlParse("ZV", section));
        view.getjXVFText().setText(util.xmlParse("XVF", section));
        view.getjZVFText().setText(util.xmlParse("ZVF", section));
        view.getjXWText().setText(util.xmlParse("XW", section));
        view.getjZWText().setText(util.xmlParse("ZW", section));
    }

    @Override
    public String generateXML() {
        String temp = "";
        temp += "<" + xmlTag + ">\n";

        temp+= util.xmlWrite("ALIH", model.getALIH());
        temp+= util.xmlWrite("ALIW", model.getALIW());
        temp+= util.xmlWrite("XCG", model.getXCG());
        temp+= util.xmlWrite("ZCG", model.getZCG());
        temp+= util.xmlWrite("XH", model.getXH());
        temp+= util.xmlWrite("ZH", model.getZH());
        temp+= util.xmlWrite("XV", model.getXV());
        temp+= util.xmlWrite("ZV", model.getZV());
        temp+= util.xmlWrite("XVF", model.getXVF());
        temp+= util.xmlWrite("ZVF", model.getZVF());
        temp+= util.xmlWrite("XW", model.getXW());
        temp+= util.xmlWrite("ZW", model.getZW());

        temp += "</" + xmlTag + ">\n";
        return temp;
    }
}
