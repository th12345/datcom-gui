/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FLTCON_Component;

import Abstracts.AbstractModel;
import opendatcom.*;

/**
 * Model class that stores the flight condition information. The class is
 * implemented as a singleton.
 * @author -B-
 */
public class FlightConditionsModel extends AbstractModel {

    // Self Reference
    static FlightConditionsModel selfReference;

     // FlightCon data
    private String flightHeader;
    private String machs;
    private String altitudes;
    private String aoas;
    private String airfoil;
    private double weight;
    private double loop;
    private double nMach;
    private double nAlt;
    private double nAOA;
    private double stMach;
    private double tsMach;
    private double tr;
    private double gamma;
    private String output;
    private double sref;
    private double cbarr;
    private double blref;

    /**
     * Private constructor as per singleton implementation.
     */
    private FlightConditionsModel() {
        machs = "";
        altitudes = "";
        aoas = "";
        loop = 1;

    }

    /**
     * Returns reference to the singleton class. Initiailzes the reference if it
     * is the first call to getInstance.
     * @return Reference to the singleton object.
     */
    public static FlightConditionsModel getInstance()
    {
        if(selfReference == null)
        {
            selfReference = new FlightConditionsModel();
        }
        return selfReference;
    }

    // Auto-generated code below this point
    
    public String getAirfoil() {
        return airfoil;
    }

    public void setAirfoil(String airfoil) {
        this.airfoil = airfoil;
    }

    public String getAltitudes() {
        return altitudes;
    }

    public void setAltitudes(String altitudes) {
        this.altitudes = altitudes;
    }

    public String getAoas() {
        return aoas;
    }

    public void setAoas(String aoas) {
        this.aoas = aoas;
    }

    public String getFlightHeader() {
        return flightHeader;
    }

    public void setFlightHeader(String flightHeader) {
        this.flightHeader = flightHeader;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getLoop() {
        return loop;
    }

    public void setLoop(double loop) {
        this.loop = loop;
    }

    public String getMachs() {
        return machs;
    }

    public void setMachs(String machs) {
        this.machs = machs;
    }

    public double getnAOA() {
        return nAOA;
    }

    public void setnAOA(double nAOA) {
        this.nAOA = nAOA;
    }

    public double getnAlt() {
        return nAlt;
    }

    public void setnAlt(double nAlt) {
        this.nAlt = nAlt;
    }

    public double getnMach() {
        return nMach;
    }

    public void setnMach(double nMach) {
        this.nMach = nMach;
    }

    public double getStMach() {
        return stMach;
    }

    public void setStMach(double stMach) {
        this.stMach = stMach;
    }

    public double getTsMach() {
        return tsMach;
    }

    public void setTsMach(double tsMach) {
        this.tsMach = tsMach;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    void setOutput(String temp) {
        this.output = temp;
    }

    public String getOutput() {
        return output;
    }

    public double getTr() {
        return tr;
    }

    public void setTr(double tr) {
        this.tr = tr;
    }

    public double getBlref() {
        return blref;
    }

    public void setBlref(double blref) {
        this.blref = blref;
    }

    public double getCbarr() {
        return cbarr;
    }

    public void setCbarr(double cbarr) {
        this.cbarr = cbarr;
    }

    public double getSref() {
        return sref;
    }

    public void setSref(double sref) {
        this.sref = sref;
    }
}
