/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public class FlightConditionsController extends AbstractController {

    // Variables
    String wingType;

    FlightConditionsModel model;
    FlightConditionsView view;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public FlightConditionsController() {
        this.view = new FlightConditionsView(this);
        this.model = FlightConditionsModel.getInstance();
        this.xmlTag = "FLTCON";
        this.name = "Flight Conditions";
        registerWithService("ImportExport");
        registerForMe();
    }

    /**
     * Gathers input data from the view and updates the model
     */
    @Override
    public void gatherData()
    {
        model.setFlightHeader(util.processHeader(view.getjFlightHeader()));
        model.setMachs(util.processTextField(view.getjMachText()));
        model.setAltitudes(util.processTextField(view.getjAltText()));
        model.setAoas(util.processTextField(view.getjAOAText()));
        model.setWeight(util.processDataField(view.getjWeightText()));
        model.setStMach(util.processDataField(view.getjSTMachText()));
        model.setTsMach(util.processDataField(view.getjTSMachText()));
        model.setGamma(util.processDataField(view.getjGammaText()));
        model.setTr(util.processDataField(view.getjTRText()));
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
        temp += util.safeAdd("NMACH=", model.getnMach());
        temp += util.safeAdd("MACH(1)= " , model.getMachs());
        temp += util.safeAdd("NALPHA=" , model.getnAOA());
        temp += util.safeAdd("ALSCHD(1)= ", model.getAoas());
        temp += util.safeAdd("NALT=", model.getnAlt());
        temp += util.safeAdd("ALT(1)= ", model.getAltitudes());
        temp += util.safeAdd("WT= ", model.getWeight());
        temp += util.safeAdd("LOOP= ", model.getLoop());

        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of Flight Condition data\n$FLTCON\n" + temp;
            temp += "$\n#End of Flight Condition data\n\n";

            // Set the output back to the model
            view.setOutputData(temp);
        }
        return temp;
    }

    public FlightConditionsModel getModel() {
        return model;
    }

    public FlightConditionsView getView() {
        return view;
    }

    @Override
    public void refreshFromSaved(String data)
    {
        String section = util.xmlParse(xmlTag, data);
        if(section.isEmpty())
        {
            return;
        }
        view.getjMachText().setText(util.xmlParse("MACH", section));
        view.getjAOAText().setText(util.xmlParse("AOA", section));
        view.getjAltText().setText(util.xmlParse("ALT", section));
        view.getjSTMachText().setText(util.xmlParse("ST", section));
        view.getjTSMachText().setText(util.xmlParse("TS", section));
        view.getjTRText().setText(util.xmlParse("TR", section));
        view.getjGammaText().setText(util.xmlParse("GAMMA", section));
        view.getjWeightText().setText(util.xmlParse("WEIGHT", section));
        refresh();
    }

    @Override
    public String generateXML() {
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
        temp += "</" + xmlTag + ">\n";
        return temp;
    }
}
