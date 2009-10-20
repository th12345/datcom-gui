/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public abstract class AbstractController {

    String outputText = "";
    String xmlTag = "";
    AbstractModel model;
    AbstractView view;
    ParserUtility util = new ParserUtility();

    /**
     * Pulls data from the view and stores it in the model.
     */
    public abstract void gatherData();

    /**
     * Generates datcom-formatted output from the controller's variables.
     * @return String containing datcom-formatted data.
     */
    public abstract String generateOutput();

    /**
     * Refreshes the data in the controller. Cascades down and updates the model
     * and the view. Methods called: gather data, generate output, & view.refresh.
     */
    public abstract void refresh();

    /**
     * Parses variable data from the input data. The input data should be in
     * XML format.
     * @param data XML-formatted controller node.
     */
    public abstract void refreshFromSaved(String data);

    /**
     * Generates the XML data for the controller's variables. Outputs the data
     * in a formatted string.
     * @return A string containing the XML-formatted variable data.
     */
    public abstract String generateXML();

    /**
     * Gets the controller's datcom output data.
     * @return
     */
    public String getOutput() {
        return outputText;
    }

    /**
     * Sets the output data. DO NOT USE.
     * @param output
     */
    public void setOutput(String output) {
        this.outputText = output;
    }

    /**
     * Gets the controller's unique xml identifier
     * @return The identifier
     */
    public String getXmlTag() {
        return xmlTag;
    }

    /**
     * Sets the controller's unique xml identifier
     * @param xmlTag
     */
    public void setXmlTag(String xmlTag) {
        this.xmlTag = xmlTag;
    }
}
