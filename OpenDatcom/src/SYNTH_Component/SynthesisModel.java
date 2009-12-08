package SYNTH_Component;

/**
 * Model class that stores the synthesis information. The class is implemented as
 * a singleton.
 * @author -B-
 */
public class SynthesisModel
{
    private static SynthesisModel selfReference;

    private double XCG, ZCG;
    private double XW, ZW;
    private double XH, ZH;
    private double XVF, ZVF;
    private double XV, ZV;
    private double ALIW, ALIH;
    private String output;

    /**
     * Private constructor as per singleton implementation.
     */
    private SynthesisModel() {}

    /**
     * Returns reference to the singleton class. Initiailzes the reference if it
     * is the first call to getInstance.
     * @return Reference to the singleton object.
     */
    public static SynthesisModel getInstance()
    {
        if(selfReference == null)
        {
            selfReference = new SynthesisModel();
        }
        return selfReference;
    }

    // Auto-generated code below this point.

    public double getALIH() {
        return ALIH;
    }

    public void setALIH(double ALIH) {
        this.ALIH = ALIH;
    }

    public double getALIW() {
        return ALIW;
    }

    public void setALIW(double ALIW) {
        this.ALIW = ALIW;
    }

    public double getXCG() {
        return XCG;
    }

    public void setXCG(double XCG) {
        this.XCG = XCG;
    }

    public double getXH() {
        return XH;
    }

    public void setXH(double XH) {
        this.XH = XH;
    }

    public double getXV() {
        return XV;
    }

    public void setXV(double XV) {
        this.XV = XV;
    }

    public double getXVF() {
        return XVF;
    }

    public void setXVF(double XVF) {
        this.XVF = XVF;
    }

    public double getXW() {
        return XW;
    }

    public void setXW(double XW) {
        this.XW = XW;
    }

    public double getZCG() {
        return ZCG;
    }

    public void setZCG(double ZCG) {
        this.ZCG = ZCG;
    }

    public double getZH() {
        return ZH;
    }

    public void setZH(double ZH) {
        this.ZH = ZH;
    }

    public double getZV() {
        return ZV;
    }

    public void setZV(double ZV) {
        this.ZV = ZV;
    }

    public double getZVF() {
        return ZVF;
    }

    public void setZVF(double ZVF) {
        this.ZVF = ZVF;
    }

    public double getZW() {
        return ZW;
    }

    public void setZW(double ZW) {
        this.ZW = ZW;
    }

    void setOutput(String temp) {
        this.output = temp;
    }

    public String getOutput() {
        return output;
    }
}
