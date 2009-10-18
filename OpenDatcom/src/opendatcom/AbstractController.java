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
    AbstractModel model;
    AbstractView view;
    
    public abstract void gatherData();
    public abstract String generateOutput();
    public abstract void refresh();

    public String getOutput() {
        return outputText;
    }

    public void setOutput(String output) {
        this.outputText = output;
    }
}
