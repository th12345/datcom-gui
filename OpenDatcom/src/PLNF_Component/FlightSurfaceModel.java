/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PLNF_Component;

import Abstracts.AbstractModel;
import opendatcom.*;

/**
 * Class that serves as the model class for all flight surfaces.
 * @author -B-
 */
public class FlightSurfaceModel extends AbstractModel {
    public enum SURFACE_TYPE
    {
        MAIN_WING, HORIZONTAL_TAIL, VERTICAL_TAIL, OTHER, NUM_SURFACE_TYPES
    }

    //TODO: Update the view class with the correct title based on type
    private SURFACE_TYPE surfaceType;
    private String Airfoil;
    private double CHRDBP;
    private double CHRDR;
    private double CHRDTP;
    private double CHSTAT;
    private double DHADO;
    private double DHDADI;
    private double SAVSI;
    private double SAVSO;
    private double SSPNE;
    private double SSPNOP;
    private double SSPN;
    private double TWISTA;
    private double TYPE;

    private String output;
    /**
     * Default constructor.
     * @param surfaceType The surface type the data represents (enum).
     */
    public FlightSurfaceModel(SURFACE_TYPE surfaceType) {
        this.surfaceType = surfaceType;
    }




    // Auto-generated code below this point

    public SURFACE_TYPE getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(SURFACE_TYPE surfaceType) {
        this.surfaceType = surfaceType;
    }
    
    public String getAirfoil() {
        return Airfoil;
    }

    public void setAirfoil(String Airfoil) {
        this.Airfoil = Airfoil;
    }

    public double getCHRDBP() {
        return CHRDBP;
    }

    public void setCHRDBP(double CHRDBP) {
        this.CHRDBP = CHRDBP;
    }

    public double getCHRDR() {
        return CHRDR;
    }

    public void setCHRDR(double CHRDR) {
        this.CHRDR = CHRDR;
    }

    public double getCHRDTP() {
        return CHRDTP;
    }

    public void setCHRDTP(double CHRDTP) {
        this.CHRDTP = CHRDTP;
    }

    public double getCHSTAT() {
        return CHSTAT;
    }

    public void setCHSTAT(double CHSTAT) {
        this.CHSTAT = CHSTAT;
    }

    public double getDHADO() {
        return DHADO;
    }

    public void setDHADO(double DHADO) {
        this.DHADO = DHADO;
    }

    public double getDHDADI() {
        return DHDADI;
    }

    public void setDHDADI(double DHDADI) {
        this.DHDADI = DHDADI;
    }

    public double getSAVSI() {
        return SAVSI;
    }

    public void setSAVSI(double SAVSI) {
        this.SAVSI = SAVSI;
    }

    public double getSAVSO() {
        return SAVSO;
    }

    public void setSAVSO(double SAVSO) {
        this.SAVSO = SAVSO;
    }

    public double getSSPN() {
        return SSPN;
    }

    public void setSSPN(double SSPN) {
        this.SSPN = SSPN;
    }

    public double getSSPNE() {
        return SSPNE;
    }

    public void setSSPNE(double SSPNE) {
        this.SSPNE = SSPNE;
    }

    public double getSSPNOP() {
        return SSPNOP;
    }

    public void setSSPNOP(double SSPNOP) {
        this.SSPNOP = SSPNOP;
    }

    public double getTWISTA() {
        return TWISTA;
    }

    public void setTWISTA(double TWISTA) {
        this.TWISTA = TWISTA;
    }

    public double getTYPE() {
        return TYPE;
    }

    public void setTYPE(double TYPE) {
        this.TYPE = TYPE;
    }

    void setOutput(String temp) {
        this.output = temp;
    }

    public String getOutput() {
        return output;
    }
}
