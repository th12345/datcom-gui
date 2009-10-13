/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OpenDatcom;

/**
 * If Java has structs then this would be a struct, but it doesnt so we have a
 * nice container class. 
 * @author -B-
 */
public class PlanformData {
String Airfoil;
double CHRDBP;
double CHRDR;
double CHRDTP;
double CHSTAT;
double DHADO;
double DHDADI;
double SAVSI;
double SAVSO;
double SSPNE;
double SSPNOP;
double SSPN;
double TWISTA;
double TYPE;

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

public void PlanformData()
{
}

}
