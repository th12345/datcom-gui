/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public class FlightConditionsController {

    // Utilities
    ParserUtility util = new ParserUtility();

    // References
    FlightConditionsView view;
    FlightConditionsModel model;

    // Variables
    String wingType;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public FlightConditionsController(FlightConditionsView view) {
        this.view = view;
        this.model = FlightConditionsModel.getInstance();
    }

    /**
     * Gathers input data from the view and updates the model
     */
    private void gatherData()
    {
        model.setFlightHeader(util.processHeader(view.getjFlightHeader()));
        model.setMachs(util.processTextField(view.getjMachText()));
        model.setAltitudes(util.processTextField(view.getjAltText()));
        model.setAoas(util.processTextField(view.getjAOAText()));
        model.setWeight(util.processDataField(view.getjWeightText()));
        model.setStMach(util.processDataField(view.getjSTMachText()));
        model.setTsMach(util.processDataField(view.getjTSMachText()));
        model.setGamma(util.processDataField(view.getjGammaText()));
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
        safeAdd("NMACH=", model.getnMach());
        safeAdd("MACH(1)= " , model.getMachs());
        safeAdd("NALPHA=" , model.getnAOA());
        safeAdd("ALSCHD(1)= ", model.getAoas());
        safeAdd("NALT=", model.getnAlt());
        safeAdd("ALT(1)= ", model.getAltitudes());
        safeAdd("WT= ", model.getWeight());
        safeAdd("LOOP= ", model.getLoop());

        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of Flight Condition data\n$FLTCON\n" + temp;
            temp += "$\n#End of Flight Condition data";

            // Set the output back to the model
            model.setOutput(temp);
        }
    }
    
    /**
     * All the safeAdd functions take the input data and format  it the following
     * way: <  Header \t Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and review.getjected if invalid. If valid, it is
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

    /**
     * All the safeAdd functions take the input data and format  it the following
     * way: <  Header \t Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and rejected if invalid. If valid, it is
     * appended to the end of the aggragateData string.
     * @param Header
     * @param Data
     */
    private String safeAdd(String Header, String Data)
    {
        String temp = "";
        if(Data.isEmpty())
        {
            return "";
        }
        temp += Header + "\t" + Data + ",\n";
        return temp;
    }
}
