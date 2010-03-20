/* Aleksey Matyushev / Alan Teeder
 *
 * This program is the sole property of Aleksey
 * Matyushev and Alan Teeder. This program is designed to be an
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear /JSBSim.
 */
package dd2jsb;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class dd2jsbAeroData {

    public String AeroDataText;
    private String aerodatastring;
    private String Sections[];
    int NSections;
    private String Lines[];
    int Nlines;
    private dd2jsbUtils DUtil;
    String[] alphaTab;
    String[][][] CdTab;
    String[][][] ClTab;
    String[][][] CmTab;
    String[][][] CnTab;
    String[][][] CaTab;
    String[][][] XcpTab;
    String[][][] ClaTab;
    String[][][] CmaTab;
    String[][][] CybTab;
    String[][][] CnbTab;
    String[][][] ClbTab;
    String[][][] ClqTab;
    String[][][] CmqTab;
    String[][][] CladTab;
    String[][][] CmadTab;
    String[][][] ClpTab;
    String[][][] CypTab;
    String[][][] CnpTab;
    String[][][] CnrTab;
    String[][][] ClrTab;
    String[] DeltaTab;
    String[] DeltaLTab;
    String[] DeltaRTab;
    String[] DeltaLRTab;
    String[][][] ClFlapTab;
    String[][][] CmFlapTab;
    String[][][] ClMaxFlapTab;
    String[][][] CdMinFlapTab;
    String[][][] ClaFlapTab;
    String[][][] ChaFlapTab;
    String[][][] ChdFlapTab;
    String[][][][] CdiFlapTab;
    String[][][][] CnAilTab;
    String[][][] ClAilTab;
    String[][][][] ClDiffTailTab;

    public dd2jsbAeroData(String aerodatastring) {
        this.aerodatastring = aerodatastring;
    }

    public void AllocateArrays(int nAlpha, int nMach, int nAlt) {
        alphaTab = new String[nAlpha];
        CdTab = new String[nAlt][nMach][nAlpha];
        ClTab = new String[nAlt][nMach][nAlpha];
        CmTab = new String[nAlt][nMach][nAlpha];
        CnTab = new String[nAlt][nMach][nAlpha];
        CaTab = new String[nAlt][nMach][nAlpha];
        XcpTab = new String[nAlt][nMach][nAlpha];
        ClaTab = new String[nAlt][nMach][nAlpha];
        CmaTab = new String[nAlt][nMach][nAlpha];
        CybTab = new String[nAlt][nMach][nAlpha];
        CnbTab = new String[nAlt][nMach][nAlpha];

        ClbTab = new String[nAlt][nMach][nAlpha];
        ClqTab = new String[nAlt][nMach][nAlpha];
        CmqTab = new String[nAlt][nMach][nAlpha];
        CladTab = new String[nAlt][nMach][nAlpha];
        CmadTab = new String[nAlt][nMach][nAlpha];
        ClpTab = new String[nAlt][nMach][nAlpha];
        CypTab = new String[nAlt][nMach][nAlpha];
        CnpTab = new String[nAlt][nMach][nAlpha];
        CnrTab = new String[nAlt][nMach][nAlpha];
        ClrTab = new String[nAlt][nMach][nAlpha];

        DeltaTab = new String[9];
        DeltaLTab = new String[9];
        DeltaRTab = new String[9];
        DeltaLRTab = new String[9];
        ClFlapTab = new String[nAlt][nMach][9];
        CmFlapTab = new String[nAlt][nMach][9];
        ClMaxFlapTab = new String[nAlt][nMach][9];
        CdMinFlapTab = new String[nAlt][nMach][9];
        ClaFlapTab = new String[nAlt][nMach][9];
        ChaFlapTab = new String[nAlt][nMach][9];
        ChdFlapTab = new String[nAlt][nMach][9];
        CdiFlapTab = new String[nAlt][nMach][nAlpha][9];
        CnAilTab = new String[nAlt][nMach][nAlpha][9];
        ClAilTab = new String[nAlt][nMach][9];
        ClDiffTailTab = new String[nAlt][nMach][nAlpha][9];
    }

    public int grabParameters(String data, String[] printALT, String[] printMACH, int iA, int jM) {
        int nAlpha = 0;
        String DataSection;
        DUtil = new dd2jsbUtils();
        Sections = data.split("AUTOMATED STABILITY AND CONTROL METHODS PER APRIL 1976 VERSION OF DATCOM");
        NSections = Sections.length;
        for (int iS = 0; iS < NSections; iS++) {
            DataSection = Sections[iS];
            nAlpha = 0;
            Lines = DataSection.split("\n");
            Nlines = Lines.length;
            if (Nlines > 0) {
                if (DataSection.indexOf("CHARACTERISTICS AT ANGLE OF ATTACK AND IN SIDESLIP") > 0) {
                    nAlpha = ReadStaticDerivatives(DataSection, printALT[iA], printMACH[jM], iA, jM);
                } else if (DataSection.indexOf("DYNAMIC DERIVATIVES") > 0) {
                    nAlpha = ReadDynamicDerivatives(DataSection, printALT[iA], printMACH[jM], iA, jM);
                } else if (DataSection.indexOf("CHARACTERISTICS OF HIGH LIFT AND CONTROL DEVICES") > 0) {
                    if (DataSection.indexOf("DIFFERENTIALLY DEFLECTED HORIZONTAL") > 0) {
                        nAlpha = ReadDifferentialTailDerivatives(DataSection, printALT[iA], printMACH[jM], iA, jM);
                    } else if (DataSection.indexOf("(DELTAL-DELTAR)") > 0) {
                        nAlpha = ReadAileronDerivatives(DataSection, printALT[iA], printMACH[jM], iA, jM);
                    } else {
                        nAlpha = ReadFlapDerivatives(DataSection, printALT[iA], printMACH[jM], iA, jM);
                    }
                } else if (DataSection.indexOf("CONFIGURATION AUXILIARY AND PARTIAL OUTPUT") > 0) {
                    nAlpha = ReadPartials(DataSection, printALT[iA], printMACH[jM], iA, jM);
                }
            }
        }
        return nAlpha;
    }

    public int ReadStaticDerivatives(String DataSection,
            String printALT, String printMACH, int iA, int jM) {
        int iline = 0;
        boolean derivsfound;
        String temp;
        Lines = DataSection.split("\n");
        Nlines = Lines.length;
        int lmachaltstart = 0;
        int nAlpha = 0;
// scan through lines until mach and alt are found.
        for (iline = 0; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (temp.length() > 40) {
                if (temp.contains("  MACH    ALTITUDE   VELOCITY")) {

                    printMACH = Lines[iline + 3].substring(1, 7);
                    printALT = Lines[iline + 3].substring(8, 18);
                    lmachaltstart = iline;
                }
            }
        }
        derivsfound = false;
        for (iline = lmachaltstart; iline < Nlines; iline++) {
            temp = Lines[iline];
            // now advance to start of derivative data
            if (iline < Nlines) {
                if (temp.length() > 40) {
                    if (temp.contains("ALPHA     CD       CL")) {
                        // Have found start of derivative data
                        // read lines and extract derivatives
                        // each line should have 12 colums of data
                        iline = iline + 2;
                        temp = Lines[iline];
                        derivsfound = true;
                    }
                    if (derivsfound && temp.startsWith(" ")) {
                        int ntemp = temp.length();
                        alphaTab[nAlpha] = temp.substring(1, 7);
                        CdTab[iA][jM][nAlpha] = temp.substring(8, 17);
                        ClTab[iA][jM][nAlpha] = temp.substring(18, 26);
                        CmTab[iA][jM][nAlpha] = temp.substring(27, 36);
                        CnTab[iA][jM][nAlpha] = temp.substring(37, 44);
                        CaTab[iA][jM][nAlpha] = temp.substring(45, 53);
                        XcpTab[iA][jM][nAlpha] = temp.substring(54, 62);
                        ClaTab[iA][jM][nAlpha] = temp.substring(63, 75);
                        CmaTab[iA][jM][nAlpha] = temp.substring(76, 88);
                        CybTab[iA][jM][nAlpha] = temp.substring(89, 101);
                        CnbTab[iA][jM][nAlpha] = temp.substring(102, 114);
                        ClbTab[iA][jM][nAlpha] = temp.substring(115, ntemp);
                        nAlpha++;
                    } else {
                        derivsfound = false;
                    }
                }
            }
        }
        return nAlpha;
    }//end ReadStaticDerivatives

    public int ReadDynamicDerivatives(String DataSection,
            String printALT, String printMACH, int iA, int jM) {
        int iline = 0;
        boolean derivsfound;
        String temp;
        Lines = DataSection.split("\n");
        Nlines = Lines.length;
        int lmachaltstart = 0;
        int nAlpha = 0;
// scan through lines until mach and alt are found.
        for (iline = 0; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (temp.length() > 40) {
                if (temp.contains("  MACH    ALTITUDE   VELOCITY")) {
                    printMACH = Lines[iline + 3].substring(1, 7);
                    printALT = Lines[iline + 3].substring(8, 18);
                    lmachaltstart = iline;
                }
            }
        }
        derivsfound = false;
        for (iline = lmachaltstart; iline < Nlines; iline++) {
            temp = Lines[iline];
            // now advance to start of derivative data
            if (iline < Nlines) {
                if (temp.length() > 40) {
                    if (temp.contains("ALPHA       CLQ          CMQ ")) {
                        // Have found start of derivative data
                        // read lines and extract derivatives
                        // each line should have 12 colums of data
                        iline = iline + 2;
                        temp = Lines[iline];
                        derivsfound = true;
                    }
                    if (derivsfound && temp.startsWith(" ")) {
                        int ntemp = temp.length();
                        ClqTab[iA][jM][nAlpha] = temp.substring(11, 23);
                        CmqTab[iA][jM][nAlpha] = temp.substring(24, 36);
                        CladTab[iA][jM][nAlpha] = temp.substring(37, 50);
                        CmadTab[iA][jM][nAlpha] = temp.substring(51, 63);
                        ClpTab[iA][jM][nAlpha] = temp.substring(64, 76);
                        CypTab[iA][jM][nAlpha] = temp.substring(77, 89);
                        CnpTab[iA][jM][nAlpha] = temp.substring(90, 102);
                        CnrTab[iA][jM][nAlpha] = temp.substring(103, 115);
                        ClrTab[iA][jM][nAlpha] = temp.substring(116, ntemp);
                        nAlpha++;
                    } else {
                        derivsfound = false;
                    }
                }
            }
        }
        return nAlpha;
    }//end ReadDynamicDerivatives

    private int ReadFlapDerivatives(String DataSection,
            String printALT, String printMACH, int iA, int jM) {
        int iline = 0;
        boolean derivs1found;
        boolean derivs1done;
        boolean derivs2found;
        String temp;
        int ndelta = 0;
        int ldelta = 0;
        int ind = 0;
        Lines = DataSection.split("\n");
        Nlines = Lines.length;
        int lmachaltstart = 0;
        int nAlpha = 0;
// scan through lines until mach and alt are found.
        for (iline = 0; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (temp.length() > 40) {
                if (temp.contains("  MACH    ALTITUDE   VELOCITY")) {
                    printMACH = Lines[iline + 3].substring(1, 7);
                    printALT = Lines[iline + 3].substring(8, 18);
                    lmachaltstart = iline;
                }
            }
        }
        derivs1found = false;
        derivs2found = false;
        derivs1done = false;
        //       derivs2done = false;
        for (iline = lmachaltstart; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (!derivs1done) {
                // now advance to start of first derivative data
                if (temp.length() > 20) {
                    if (temp.contains("DELTA     D(CL)     D(CM)")) {
                        // Have found start of derivative data
                        // read lines and extract derivates
                        derivs1found = true;
                        iline = iline + 3;
                        temp = Lines[iline];
                        ind = 0;
                    }
                    if (derivs1found && temp.startsWith("  ")) {
                        int ntemp = temp.length();
                        DeltaTab[ind] = temp.substring(5, 12);
                        ClFlapTab[iA][jM][ind] = temp.substring(13, 22);
                        CmFlapTab[iA][jM][ind] = temp.substring(23, 33);
                        ClMaxFlapTab[iA][jM][ind] = temp.substring(34, 43);
                        CdMinFlapTab[iA][jM][ind] = temp.substring(44, 56);
                        ClaFlapTab[iA][jM][ind] = temp.substring(57, 81);
                        ChaFlapTab[iA][jM][ind] = temp.substring(82, 93);
                        ChdFlapTab[iA][jM][ind] = temp.substring(94, ntemp);
                        ind++;
                    } else {
                        if (derivs1found) {
                            derivs1done = true;
                        }
                        derivs1found = false;
                    }
                }
            } else {
                // now advance to start of second derivative data
                if (temp.length() > 20) {
                    if (temp.contains("INDUCED DRAG COEFFICIENT INCREMENT")) {
                        // Have found start of derivative data
                        // read lines and extract derivates
                        iline = iline + 1;
                        temp = Lines[iline];
                        ldelta = temp.length();
                        ndelta = ((temp.length() - 16) / 10) + 1;
                        for (ind = 0; ind < ndelta; ind++) {
                            int ipos = 10 * ind + 16;
                            int ipos2 = ipos + 5;
                            if (ipos2 > ldelta) {
                                ipos2 = ldelta;
                            }
                            DeltaTab[ind] = temp.substring(ipos, ipos + 5);
                        }
                        derivs2found = true;
                        iline = iline + 3;
                        temp = Lines[iline];
                    }
                    if (derivs2found && temp.startsWith(" ")) {
                        int ntemp = temp.length();
                        alphaTab[nAlpha] = temp.substring(1, 7);
                        for (ind = 0; ind < ndelta; ind++) {
                            int ipos = 10 * ind + 15;
                            int ipos2 = ipos + 10;
                            if (ipos2 > ntemp) {
                                ipos2 = ntemp;
                            }
                            CdiFlapTab[iA][jM][nAlpha][ind] = temp.substring(ipos, ipos2);
                        }
                        nAlpha++;
                    } else {
                        derivs2found = false;
                    }
                }
            }
        }
        return nAlpha;
    }

    private int ReadAileronDerivatives(String DataSection,
            String printALT, String printMACH, int iA, int jM) {
        int iline = 0;
        boolean derivs1found;
        boolean derivs1done;
        boolean derivs2found;
        String temp;
        Lines = DataSection.split("\n");
        Nlines = Lines.length;
        int lmachaltstart = 0;
        int nAlpha = 0;
// scan through lines until mach and alt are found.
        for (iline = 0; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (temp.length() > 40) {
                if (temp.contains("  MACH    ALTITUDE   VELOCITY")) {
                    printMACH = Lines[iline + 3].substring(1, 7);
                    printALT = Lines[iline + 3].substring(8, 18);
                    lmachaltstart = iline;
                }
            }
        }
        derivs1found = false;
        derivs2found = false;
        derivs1done = false;
        int ndelta = 0;
        int ind = 0;
        for (iline = lmachaltstart; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (!derivs1done) {
                // now advance to start of first derivative data
                if (temp.contains("(DELTAL-DELTAR)")) {
                    ndelta = ((temp.length() - 18) / 12) + 1;
                    for (ind = 0; ind < ndelta; ind++) {
                        int ipos = 12 * ind + 17;
                        DeltaLRTab[ind] = temp.substring(ipos, ipos + 5);
                    }
                    derivs1found = true;
                    iline = iline + 3;
                    temp = Lines[iline];
                }
                if (derivs1found && temp.startsWith(" ")) {
                    int ntemp = temp.length();
                    alphaTab[nAlpha] = temp.substring(1, 7);
                    for (ind = 0; ind < ndelta; ind++) {
                        int ipos = 12 * ind + 12;
                        int ipos2 = ipos + 12;
                        if (ipos2 > ntemp) {
                            ipos2 = ntemp;
                        }
                        String deriv = temp.substring(ipos, ipos2);
                        CnAilTab[iA][jM][nAlpha][ind] = temp.substring(ipos, ipos2);
                    }
                    nAlpha++;
                } else {
                    if (derivs1found) {
                        derivs1done = true;
                    }
                    derivs1found = false;
                }
                //          }
            } else {
                // now advance to start of second derivative data
                if (temp.length() > 20) {
                    if (temp.contains("DELTAL          DELTAR          (CL)ROLL")) {
                        // Have found start of derivative data
                        // read lines and extract derivates
                        derivs2found = true;
                        iline = iline + 2;
                        temp = Lines[iline];
                        ind = 0;
                    }
                    if (derivs2found && temp.startsWith("                 ")) {
                        int ntemp = temp.length();
                        DeltaLTab[ind] = temp.substring(41, 51);
                        DeltaRTab[ind] = temp.substring(57, 67);
                        ClAilTab[iA][jM][ind] = temp.substring(77, ntemp);
                        ind++;
                    } else {
                        derivs2found = false;
                    }
                }
            }
        }
        return nAlpha;
    }//end ReadAileronDerivatives

    private int ReadDifferentialTailDerivatives(String DataSection,
            String printALT, String printMACH, int iA, int jM) {
        int iline = 0;
        boolean derivs1found;
        boolean derivs2found;
        String temp;
        Lines = DataSection.split("\n");
        Nlines = Lines.length;
        int lmachaltstart = 0;
        int nAlpha = 0;
// scan through lines until mach and alt are found.
        for (iline = 0; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (temp.length() > 40) {
                if (temp.contains("  MACH    ALTITUDE   VELOCITY")) {
                    printMACH = Lines[iline + 3].substring(1, 7);
                    printALT = Lines[iline + 3].substring(8, 18);
                    lmachaltstart = iline;
                }
            }
        }
        derivs1found = false;
        derivs2found = false;
        int ndelta = 0;
        int ind = 0;
        for (iline = lmachaltstart; iline < Nlines; iline++) {
            temp = Lines[iline];
            if (DataSection.contains("(DELTAL-DELTAR)")) {
                // now advance to start of derivative data
                if (temp.contains("(DELTAL-DELTAR)")) {
                    ndelta = ((temp.length() - 18) / 12) + 1;
                    for (ind = 0; ind < ndelta; ind++) {
                        int ipos = 12 * ind + 17;
                        DeltaLRTab[ind] = temp.substring(ipos, ipos + 5);
                        try {
                            float f = Float.valueOf(DeltaLRTab[ind].trim()).floatValue();
                            DeltaLTab[ind] = String.valueOf(f / 2.0);
                            DeltaRTab[ind] = String.valueOf(-f / 2.0);
                        } catch (NumberFormatException nfe) {
                            DeltaLTab[ind] = " NaN ";
                            DeltaRTab[ind] = " NaN ";
                        }
                    }
                    derivs1found = true;
                    iline = iline + 3;
                    temp = Lines[iline];
                }
                if (derivs1found && temp.startsWith(" ") && nAlpha < alphaTab.length) {
                    int ntemp = temp.length();
                    alphaTab[nAlpha] = temp.substring(1, 7);
                    for (ind = 0; ind < ndelta; ind++) {
                        int ipos = 12 * ind + 12;
                        int ipos2 = ipos + 12;
                        if (ipos2 > ntemp) {
                            ipos2 = ntemp;
                        }
                        String deriv = temp.substring(ipos, ipos2);
                        ClDiffTailTab[iA][jM][nAlpha][ind] = temp.substring(ipos, ipos2);
                    }
                    nAlpha++;
                } else {
                    if (derivs1found) {
                    }
                    derivs1found = false;
                }
            } else {
                // advance to start of derivative data
                if (temp.length() > 20) {
                    if (temp.contains("DELTAL          DELTAR          (CL)ROLL")) {
                        // Have found start of derivative data
                        // read lines and extract derivates
                        derivs2found = true;
                        iline = iline + 2;
                        temp = Lines[iline];
                        ind = 0;
                    }
                    if (derivs2found && temp.startsWith("                 ")) {
                        int ntemp = temp.length();
                        DeltaLTab[ind] = temp.substring(41, 51);
                        DeltaRTab[ind] = temp.substring(57, 67);
                        ClDiffTailTab[iA][jM][0][ind] = temp.substring(77, ntemp);
                        ind++;
                    } else {
                        derivs2found = false;
                    }
                }
            }
        }
        return nAlpha;
    }

    private int ReadPartials(String DataSection,
            String printALT, String printMACH, int iA, int jM) {
        return 0;
    }

    public void writemaster(String Path, String[] ALT, String[] MACH, int nALT, int nMACH, int NALPHA) {

        wrAlphaData(Path, "Cdtab.txt", "cdalpha", CdTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cltab.txt", "clalpha", ClTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cdtab.txt", "cdalpha", CdTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cmtab.txt", "cmalpha", CmTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cntab.txt", "cnalpha", CnTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Catab.txt", "caalpha", CaTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Xcptab.txt", "Xcpalpha", XcpTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Clatab.txt", "claalpha", ClaTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cmatab.txt", "cmaalpha", CmaTab, ALT, MACH, nALT, nMACH, NALPHA);
        if (CybTab[0][0][0] != null) {
            if ((!CybTab[0][0][0].trim().equals("")) && (CybTab[0][0][1].trim().equals(""))) {
                wrMachAltData(Path, "Cybtab1.txt", "cybalpha", CybTab, ALT, MACH, nALT, nMACH, NALPHA);
            }
        }
        if (CnbTab[0][0][0] != null) {
            wrAlphaData(Path, "Cybtab.txt", "cybalpha", CybTab, ALT, MACH, nALT, nMACH, NALPHA);
            if ((!CnbTab[0][0][0].trim().equals("")) && (CnbTab[0][0][1].trim().equals(""))) {
                wrMachAltData(Path, "Cnbtab1.txt", "cnbalpha", CnbTab, ALT, MACH, nALT, nMACH, NALPHA);
            }
        }
        wrAlphaData(Path, "Cnbtab.txt", "cnbalpha", CnbTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Clbtab.txt", "clbalpha", ClbTab, ALT, MACH, nALT, nMACH, NALPHA);

        if (ClqTab[0][0][0] != null) {
            if ((!ClqTab[0][0][0].trim().equals("")) && (ClqTab[0][0][1].trim().equals(""))) {
                wrMachAltData(Path, "Clqtab1.txt", "clqalpha", ClqTab, ALT, MACH, nALT, nMACH, NALPHA);
            }
        }
        wrAlphaData(Path, "Clqtab.txt", "clqalpha", ClqTab, ALT, MACH, nALT, nMACH, NALPHA);
        if (CmqTab[0][0][0] != null) {
            if ((!CmqTab[0][0][0].trim().equals("")) && (CmqTab[0][0][1].trim().equals(""))) {
                wrMachAltData(Path, "Cmqtab1.txt", "cmqalpha", CmqTab, ALT, MACH, nALT, nMACH, NALPHA);
            }
        }
        wrAlphaData(Path, "Cmqtab.txt", "cmqalpha", CmqTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cladtab.txt", "cladalpha", CladTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cmadtab.txt", "cmadalpha", CmadTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Clptab.txt", "clpalpha", ClpTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cnptab.txt", "cnpalpha", CnpTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaData(Path, "Cnrtab.txt", "cnralpha", CnrTab, ALT, MACH, nALT, nMACH, NALPHA);
        if (ClrTab[0][0][0] != null) {
            if ((!ClrTab[0][0][0].trim().equals("")) && (ClrTab[0][0][1].trim().equals(""))) {
                wrMachAltData(Path, "Clrtab1.txt", "clralpha", ClrTab, ALT, MACH, nALT, nMACH, NALPHA);
            }
        }
        wrAlphaData(Path, "Clrtab.txt", "clralpha", ClrTab, ALT, MACH, nALT, nMACH, NALPHA);

        wrDeltaData(Path, "ClFlapTab.txt", "ClFlapDelta", ClFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrDeltaData(Path, "CmFlapTab.txt", "CmFlapDelta", CmFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrDeltaData(Path, "ClMaxFlapTab.txt", "ClMaxFlapDelta", ClMaxFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrDeltaData(Path, "CdMinFlapTab.txt", "CdMinDelta", CdMinFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrDeltaData(Path, "ClaFlapTab.txt", "ClaFlapDelta", ClaFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrDeltaData(Path, "ChaFlapTab.txt", "ChaFlapDelta", ChaFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrDeltaData(Path, "ChdFlapTab.txt", "ChdFlapDelta", ChdFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrDeltaLData(Path, "ClAilTab.txt", "ClAilDelta", ClAilTab, ALT, MACH, nALT, nMACH, NALPHA);

        wrAlphaDeltaData(Path, "CdiFlapTab.txt", "CdiFlapAlphaDelta", CdiFlapTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaDeltaLData(Path, "CnAilTab.txt", "CnAilAlphaDelta", CnAilTab, ALT, MACH, nALT, nMACH, NALPHA);
        wrAlphaDeltaLData(Path, "ClDiffTailTab.txt", "ClDiffTailAlphaDelta", ClDiffTailTab, ALT, MACH, nALT, nMACH, NALPHA);
    }

    private void wrAlphaData(String Path, String filename, String varname,
            String[][][] derivtable, String[] ALT, String[] MACH,
            int nALT, int nMACH, int NALPHA) {
        if (derivtable[0][0][0] != null) {
            String print = "";
            int nALPHA = alphaTab.length;
            print = print + "<!-- Table produced from Datcom output by DD2JSB " + getDateTime() + "\r\n";
            print = print + Path + "\\" + filename + "-->\r\n\r\n";
            print = print + "<table name =  \"" + varname + "\">\r\n";
            if (nALPHA > 1) {
                print = print + "<independentVar lookup = \"row\"> aero/alpha-deg </independentVar>\r\n";
            }
            if (nMACH > 1) {
                print = print + "<independentVar lookup = \"column\"> aero/mach </independentVar>\r\n";
            }
            if (nALT > 1) {
                print = print + "<independentVar lookup = \"table\"> aero/alt </independentVar>\r\n";
            }
            for (int iALT = 0; iALT < nALT; iALT++) {
                print = print + "<tabledata breakpoint = " + ALT[iALT] + ">\r\n";
                print = print + "\t";
                for (int iM = 0; iM < nMACH; iM++) {
                    print = print + "\t" + MACH[iM];
                }
                print = print + "\r\n";
                for (int i = 0; i < nALPHA; i++) {
                    print = print + alphaTab[i] + "  ";
                    for (int iMACH = 0; iMACH < nMACH; iMACH++) {
                        print = print + derivtable[iALT][iMACH][i];
                    }
                    print = print + "\r\n";
                }
                print = print + "</tabledata>\r\n";
            }
            print = print + "</table>\r\n";
            print = print + "<!-- ******************************* -->";
            DUtil.writeFile(Path, filename, print);
        }
    }

    private void wrMachAltData(String Path, String filename, String varname,
            String[][][] derivtable, String[] ALT, String[] MACH,
            int nALT, int nMACH, int NALPHA) {
        if (derivtable[0][0][0] != null) {
            String print = "";
            print = print + "<!-- Table produced from Datcom output by DD2JSB " + getDateTime() + "\r\n";
            print = print + "\r\n" + Path + "\\" + filename + "-->\r\n\r\n";
            print = print + "<table name =  \"" + varname + "\">\r\n";
            if (nMACH > 1) {
                print = print + "<independentVar lookup = \"row\"> aero/mach</independentVar>\r\n";
            }
            if (nALT > 1) {
                print = print + "<independentVar lookup = \"column\"> aero/alt</independentVar>\r\n";
            }
            for (int iA = 0; iA < nALT; iA++) {
                print = print + "\t" + ALT[iA];
            }
            print = print + "\r\n";
            for (int iMACH = 0; iMACH < nMACH; iMACH++) {
                if (nMACH > 1) {
                    print = print + MACH[iMACH] + "  ";
                }
                for (int iALT = 0; iALT < nALT; iALT++) {
                    print = print + derivtable[iALT][iMACH][0];
                }
                print = print + "\r\n";
            }
            print = print + "</table>\r\n";
            print = print + "<!-- ******************************* -->";
            DUtil.writeFile(Path, filename, print);
        }
    }

    private void wrDeltaData(String Path, String filename, String varname,
            String[][][] derivtable, String[] ALT, String[] MACH,
            int nALT, int nMACH, int NALPHA) {
        if (derivtable[0][0][0] != null) {
            String print = "";
            print = print + "<!-- Table produced from Datcom output by DD2JSB " + getDateTime() + "\r\n";
            print = print + "\r\n" + Path + "\\" + filename + "-->\r\n\r\n";
            int nDelta = DeltaTab.length;
            print = print + "<table name = \"" + varname + "\">\r\n";
            if (nDelta > 1) {
                print = print + "<independentVar lookup = \"row\"> aero/deltacontrol </independentVar>\r\n";
            }
            if (nMACH > 1) {
                print = print + "<independentVar lookup = \"column\"> aero/mach </independentVar>\r\n";
            }
            if (nALT > 1) {
                print = print + "<independentVar lookup = \"table\"> aero/alt </independentVar>\r\n";
            }
            for (int iALT = 0; iALT < nALT; iALT++) {
                print = print + "<tabledata breakpoint = " + ALT[iALT] + ">\r\n";
                print = print + "\t";
                for (int iM = 0; iM < nMACH; iM++) {
                    print = print + "\t" + MACH[iM];
                }
                print = print + "\r\n";
                for (int i = 0; i < nDelta; i++) {
                    print = print + DeltaTab[i] + "  ";
                    for (int iMACH = 0; iMACH < nMACH; iMACH++) {
                        print = print + derivtable[iALT][iMACH][i];
                    }
                    print = print + "\r\n";
                }
                print = print + "</tabledata>\r\n";
            }
            print = print + "</table>\r\n";
            print = print + "<!-- ******************************* -->";
            DUtil.writeFile(Path, filename, print);
        }
    }

    private void wrDeltaLData(String Path, String filename, String varname,
            String[][][] derivtable, String[] ALT, String[] MACH,
            int nALT, int nMACH, int NALPHA) {
        if (derivtable[0][0][0] != null) {
            String print = "";
            print = print + "<!-- Table produced from Datcom output by DD2JSB " + getDateTime() + "\r\n";
            print = print + Path + "\\" + filename + "-->\r\n\r\n";
            int nDelta = DeltaLTab.length;
            print = print + "<table name =  \"" + varname + "\">\r\n";
            if (nDelta > 1) {
                print = print + "<independentVar lookup = \"row\"> aero/deltacontrol </independentVar>\r\n";
            }
            if (nMACH > 1) {
                print = print + "<independentVar lookup = \"column\"> aero/mach </independentVar>\r\n";
            }
            if (nALT > 1) {
                print = print + "<independentVar lookup = \"table\"> aero/alt </independentVar>\r\n";
            }
            for (int iALT = 0; iALT < nALT; iALT++) {
                print = print + "<tabledata breakpoint = " + ALT[iALT] + ">\r\n";
                print = print + "\t";
                for (int iM = 0; iM < nMACH; iM++) {
                    print = print + "\t" + MACH[iM];
                }
                print = print + "\r\n";
                for (int i = 0; i < nDelta; i++) {
                    print = print + DeltaLTab[i] + "  ";
                    for (int iMACH = 0; iMACH < nMACH; iMACH++) {
                        print = print + derivtable[iALT][iMACH][i];
                    }
                    print = print + "\r\n";
                }
                print = print + "</tabledata>\r\n";
            }
            print = print + "</table>\r\n";
            print = print + "<!-- ******************************* -->";
            DUtil.writeFile(Path, filename, print);
        }
    }

    private void wrAlphaDeltaData(String Path, String filename, String varname,
            String[][][][] derivtable, String[] ALT, String[] MACH,
            int nALT, int nMACH, int NALPHA) {
        if (derivtable[0][0][0][0] != null) {
            int nALPHA = alphaTab.length;
            int nDelta = DeltaTab.length;
            for (int iALT = 0; iALT < nALT; iALT++) {
                int lfname = filename.length();
                String print = "";
                String filenameAlt = filename.substring(0, lfname - 4) + ALT[iALT] + ".txt";
                print = print + "<!-- Table produced from Datcom output by DD2JSB " + getDateTime() + "\r\n";
                print = print + Path + "\\" + filenameAlt + "-->\r\n\r\n";
                print = print + "<table name =  \"" + varname + "\">\r\n";
                if (nDelta > 1) {
                    print = print + "<independentVar lookup = \"row\"> aero/deltacontrol </independentVar>\r\n";
                }
                if (nALPHA > 1) {
                    print = print + "<independentVar lookup = \"column\"> aero/alpha-deg </independentVar>\r\n";
                }
                if (nMACH > 1) {
                    print = print + "<independentVar lookup = \"table\"> aero/mach </independentVar>\r\n";
                }
                for (int iMACH = 0; iMACH < nMACH; iMACH++) {
                    print = print + "<tabledata breakpoint = " + MACH[iMACH] + ">\r\n";
                    print = print + "\t";
                    for (int iA = 0; iA < nALPHA; iA++) {
                        print = print + "\t" + alphaTab[iA];
                    }
                    print = print + "\r\n";
                    for (int i = 0; i < nDelta; i++) {
                        print = print + DeltaTab[i] + "  ";
                        for (int iAlpha = 0; iAlpha < nALPHA; iAlpha++) {
                            print = print + "\t" + derivtable[iALT][iMACH][iAlpha][i];
                        }
                        print = print + "\r\n";
                    }
                    print = print + "</tabledata>\r\n";
                }
                print = print + "</table>\r\n";
                print = print + "<!-- ******************************* -->";
                DUtil.writeFile(Path, filenameAlt, print);
            }
        }
    }

    private void wrAlphaDeltaLData(String Path, String filename, String varname,
            String[][][][] derivtable, String[] ALT, String[] MACH,
            int nALT, int nMACH, int NALPHA) {
        if (derivtable[0][0][0][0] != null) {
            String print;
            int nALPHA = alphaTab.length;
            int nDelta = DeltaLTab.length;
            for (int iALT = 0; iALT < nALT; iALT++) {
                print = "";
                int lfname = filename.length();
                String filenameAlt = filename.substring(0, lfname - 4) + ALT[iALT] + ".txt";
                print = print + "<!-- Table produced from Datcom output by DD2JSB " + getDateTime() + "\r\n";
                print = print + Path + "\\" + filenameAlt + "-->\r\n\r\n";
                print = print + "<table name =  \"" + varname + "\">\r\n";
                if (nDelta > 1) {
                    print = print + "<independentVar lookup = \"row\"> aero/deltacontrol </independentVar>\r\n";
                }
                if (nALPHA > 1) {
                    print = print + "<independentVar lookup = \"column\"> aero/alpha-deg </independentVar>\r\n";
                }
                if (nMACH > 1) {
                    print = print + "<independentVar lookup = \"table\"> aero/mach </independentVar>\r\n";
                }
                for (int iMACH = 0; iMACH < nMACH; iMACH++) {
                    print = print + "<tabledata breakpoint = " + MACH[iMACH] + ">\r\n";
                    print = print + "\t";
                    for (int iA = 0; iA < nALPHA; iA++) {
                        print = print + "\t" + alphaTab[iA];
                    }
                    print = print + "\r\n";
                    for (int i = 0; i < nDelta; i++) {
                        print = print + DeltaLTab[i] + "  ";
                        for (int iAlpha = 0; iAlpha < nALPHA; iAlpha++) {
                            print = print + "\t" + derivtable[iALT][iMACH][iAlpha][i];
                        }
                        print = print + "\r\n";
                    }
                    print = print + "</tabledata>\r\n";
                }
                print = print + "</table>\r\n";
                print = print + "<!-- ******************************* -->";
                DUtil.writeFile(Path, filenameAlt, print);
            }
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}


