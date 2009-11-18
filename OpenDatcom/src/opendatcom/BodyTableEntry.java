/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

/**
 *
 * @author -B-
 */
public class BodyTableEntry
{
    private double radius;
    private double zl = Double.NaN;
    private double zu = Double.NaN;
    private double x;
    private double p;
    private double sa;
    boolean useRadius;


    public BodyTableEntry()
    {
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        useRadius = true;
    }

    public double getSa() {
        return sa;
    }

    public void setSa(double sa) {
        this.sa = sa;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZu() {
        return zu;
    }

    public void setZu(double zu) {
        useRadius = false;
        this.zu = zu;
    }

    public double getZl() {
        if(Double.isNaN(zl))
        {
            return 0;
        }
        return zl;
    }

    public void setZl(double zl) {
        useRadius = false;
        this.zl = zl;
    }
}
