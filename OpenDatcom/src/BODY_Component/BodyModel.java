/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BODY_Component;

import Abstracts.AbstractModel;
import java.util.LinkedList;
import opendatcom.*;

/**
 *
 * @author -B-
 */
public class BodyModel extends AbstractModel {

    // Self Reference
    static BodyModel selfReference;

    // BodyModel Parameters
    LinkedList<Double> x;                   // Longitidunal Distance from LOCN
    private double s[];                   // Cross-sectional Area at x
    private double p[];                   // Perimeter at x
    private double r[];                   // Planform half width at x
    private double zu[];                  // Z-coordinate of upper surface
    private double zl[];                  // Z-coordinate of lower surface
    private double sectionCount;
    private double bnose;               // Nose roundness
    private double btail;               // Tail roundness
    private double bln;                 // Length of nose
    private double bla;                 // Length of tail
    
    /**
     * Private constructor as per singleton implementation.
     */
    private BodyModel() {
        x = new LinkedList<Double>();
        s = new double[20];
        p = new double[20];
        r = new double[20];
        zu = new double[20];
        zl = new double[20]; 
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

    public double getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(double sectionCount) {
        this.sectionCount = sectionCount;
    }

    public double getBla() {
        return bla;
    }

    public void setBla(double bla) {
        this.bla = bla;
    }

    public double getBln() {
        return bln;
    }

    public void setBln(double bln) {
        this.bln = bln;
    }

    public double getBnose() {
        return bnose;
    }

    public void setBnose(double bnose) {
        this.bnose = bnose;
    }

    public double getBtail() {
        return btail;
    }

    public void setBtail(double btail) {
        this.btail = btail;
    }

    public double[] getP() {
        return p;
    }

    public void setP(double[] p) {
        this.p = p;
    }

    public double[] getR() {
        return r;
    }

    public void setR(double[] r) {
        this.r = r;
    }

    public double[] getS() {
        return s;
    }

    public void setS(double[] s) {
        this.s = s;
    }

    public LinkedList<Double> getX() {
        return x;
    }

    public void setX(LinkedList<Double> x) {
        this.x = x;
    }

    public double[] getZl() {
        return zl;
    }

    public void setZl(double[] zl) {
        this.zl = zl;
    }

    public double[] getZu() {
        return zu;
    }

    public void setZu(double[] zu) {
        this.zu = zu;
    }

}
