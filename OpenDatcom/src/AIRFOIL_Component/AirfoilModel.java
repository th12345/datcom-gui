/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AIRFOIL_Component;

import java.util.LinkedList;

/**
 *
 * @author -B-
 */
public class AirfoilModel
{
    AirfoilController controller;
    LinkedList<Double> yupper;
    double [] ylower;
    LinkedList<Double> mean;
    double [] thick;
    LinkedList<Double> xcord;
    TYPE_IN inputMode;

    enum TYPE_IN
    {
        PARAMETER,
        UPPER_LOWER,
        MEAN_THICK,
        NONE
    }

    public AirfoilModel(AirfoilController controller)
    {
        this.controller = controller;
        yupper  = new LinkedList<Double>();
        ylower  = new double[48];
        mean    = new LinkedList<Double>();
        thick   = new double[48];
        xcord   = new LinkedList<Double>();
    }

    public TYPE_IN getInputMode() {
        return inputMode;
    }

    public void setInputMode(TYPE_IN inputMode) {
        this.inputMode = inputMode;
    }

    public LinkedList<Double> getMean() {
        return mean;
    }

    public void setMean(LinkedList<Double> mean) {
        this.mean = mean;
    }

    public double[] getThick() {
        return thick;
    }

    public void setThick(double[] thick) {
        this.thick = thick;
    }

    public LinkedList<Double> getXcord() {
        return xcord;
    }

    public void setXcord(LinkedList<Double> xcord) {
        this.xcord = xcord;
    }

    public double[] getYlower() {
        return ylower;
    }

    public void setYlower(double[] ylower) {
        this.ylower = ylower;
    }

    public LinkedList<Double> getYupper() {
        return yupper;
    }

    public void setYupper(LinkedList<Double> yupper) {
        this.yupper = yupper;
    }
}
