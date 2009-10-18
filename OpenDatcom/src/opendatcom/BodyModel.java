/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public class BodyModel extends AbstractModel {

    // Self Reference
    static BodyModel selfReference;

    // BodyModel Parameters
    private double xValues[];
    private double radii[];
    private double sectionCount;
    
    /**
     * Private constructor as per singleton implementation.
     */
    private BodyModel() {
        xValues = new double[20];
        radii = new double[20];
    }

    /**
     * Returns reference to the singleton class. Initiailzes the reference if it
     * is the first call to getInstance.
     * @return Reference to the singleton object.
     */
    public static BodyModel getInstance()
    {
        if(selfReference == null)
        {
            selfReference = new BodyModel();
        }
        return selfReference;
    }

    public double[] getRadii() {
        return radii;
    }

    public void setRadii(double[] radii) {
        this.radii = radii;
    }

    public double getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(double sectionCount) {
        this.sectionCount = sectionCount;
    }

    public double[] getxValues() {
        return xValues;
    }

    public void setxValues(double[] xValues) {
        this.xValues = xValues;
    }
}
