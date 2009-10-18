/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public abstract interface AbstractController {
    String output = "";
    
    public abstract void gatherData();
    public abstract String generateOutput();
    public abstract void refresh();
}
