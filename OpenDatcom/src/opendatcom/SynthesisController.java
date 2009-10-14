/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public class SynthesisController {

    // Utilities
    ParserUtility util = new ParserUtility();

    // References
    SynthesisView view;
    SynthesisModel model;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public SynthesisController(SynthesisView view) {
        this.view = view;
        this.model = SynthesisModel.getInstance();
    }

    /**
     * Gathers input data from the view and updates the model
     */
    private void gatherData()
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
        String temp;
        temp = safeAdd("XCG=", model.getXCG());
        temp += safeAdd("ZCG=", model.getZCG());
        temp += safeAdd("XW=", model.getXW());
        temp += safeAdd("ZW=", model.getZW());
        temp += safeAdd("ALIH=", model.getALIH());
        temp += safeAdd("ALIW=", model.getALIW());
        temp += safeAdd("XH=", model.getXH());
        temp += safeAdd("ZH=", model.getZH());
        temp += safeAdd("XV=", model.getXV());
        temp += safeAdd("ZV=", model.getZV());

        // Make sure atleast 1 value was written then append the header/footer
        if(!temp.isEmpty())
        {
            // Trim off the extra comma
            temp = temp.substring(0, temp.length() - 2);
            temp = "#Start of Synthesis data\n$SYNTH\n" + temp;
            temp += "$\n#End of Synthesis data";

            // Set the output back to the model
            model.setOutput(temp);
        }
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
