/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Core.DataServer;
import Core.OAE_LinkInterface;
import Core.OAE_LinkedTable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author -B-
 */
public class FortranFormat
{
    HashMap<String, OAE_LinkInterface> mapRef;
    public FormatUtility fu;
    boolean usingProp;
    boolean isJSBSimRun;
    
    public FortranFormat()
    {
        mapRef = DataServer.getMap();
    }

    public String generateForFile()
    {
        usingProp = false;
        String out = "CASEID TEST\n";
        out += generateFLTCON();
        out += generateSYNTHS();
        out += generateOPTINS();
        out += generateBODY();
        out += generatePLNFS();
        out += generateSCHRS();
        out += generatePROPWR();
        out += generateJETPWR();
        out = out.replaceAll("\t", "");
        return out;
    }

    public String generateFLTCON()
    {
        String out = "";
        String header = " $FLTCON\n";
        String offset = "  ";
        out += mapRef.get("MACH").datcomFormat(offset);
        out += mapRef.get("ALT").datcomFormat(offset);
        out += mapRef.get("ALSCHD").datcomFormat(offset);
        out += mapRef.get("WT").datcomFormat(offset);
        out += mapRef.get("STMACH").datcomFormat(offset);
        out += mapRef.get("TSMACH").datcomFormat(offset);
        out += mapRef.get("GAMMA").datcomFormat(offset);
        out += mapRef.get("TR").datcomFormat(offset);
        out += mapRef.get("LOOP").datcomFormat(offset);

        if(!out.isEmpty())
        {
            // Do a bunch of vodoo to get the N--- values correctly positioned (not pretty)
            OAE_LinkedTable temp = (OAE_LinkedTable) mapRef.get("MACH");
            if(temp != null)
            {
                String blah = "NMACH=" + temp.getSize() + ".0,\n" + offset + "MACH";
                out = out.replace("MACH", blah);
            }
            temp = (OAE_LinkedTable) mapRef.get("ALT");
            if(temp != null)
            {
                String blah = "NALT=" + temp.getSize() + ".0,\n" + offset + "ALT";
                out = out.replace("ALT", blah);
            }
            temp = (OAE_LinkedTable) mapRef.get("ALSCHD");
            if(temp != null)
            {
                String blah = "NALPHA=" + temp.getSize() + ".0,\n" + offset + "ALSCHD";
                out = out.replace("ALSCHD", blah);
            }
            out = out.substring(0, out.length() - 2 );
            out = header + out + "$\n";
        }
        return out;
    }

    public String generateOPTINS()
    {
        String out = "";
        String header = " $OPTINS\n";
        String offset = "  ";
        out += mapRef.get("BLREF").datcomFormat(offset);
        out += mapRef.get("SREF").datcomFormat(offset);
        out += mapRef.get("CBARR").datcomFormat(offset);

        if(!out.isEmpty())
        {
            out = out.substring(0, out.length() - 2 );
            out = header + out + "$\n";
        }
        return out;
    }

    public String generateBODY()
    {
        String out = "";
        String header = " $BODY \n";
        String offset = "  ";
        out += mapRef.get("X").datcomFormat(offset);
        out += mapRef.get("R").datcomFormat(offset);
        out += mapRef.get("S").datcomFormat(offset);
        out += mapRef.get("P").datcomFormat(offset);
        out += mapRef.get("ZU").datcomFormat(offset);
        out += mapRef.get("ZL").datcomFormat(offset);
        
        if(!out.isEmpty())
        {
            // Condition the output to datcom standards
            out = out.replaceAll("X=", "NX=" + ((List)mapRef.get("X").getValue()).size() + ".0,\n" + offset +"X(1)=");
            out = out.replaceAll("R=", "R(1)=");
            out = out.replaceAll("S=", "S(1)=");
            out = out.replaceAll("P=", "P(1)=");
            out = out.replaceAll("ZU=", "ZU(1)=");
            out = out.replaceAll("ZL=", "ZL(1)=");
            out = out.substring(0, out.length() - 2);
            out = header + out + "$\n";
        }
        return out;
    }

    public String generateSYNTHS()
    {
        String out = "";
        String header = " $SYNTHS \n";
        String offset = "  ";
        out += mapRef.get("XCG").datcomFormat(offset);
        out += mapRef.get("ZCG").datcomFormat(offset);
        out += mapRef.get("XW").datcomFormat(offset);
        out += mapRef.get("ZW").datcomFormat(offset);
        out += mapRef.get("ALIW").datcomFormat(offset);
        out += mapRef.get("ALIH").datcomFormat(offset);
        out += mapRef.get("SCALE").datcomFormat(offset);
        out += mapRef.get("XH").datcomFormat(offset);
        out += mapRef.get("ZH").datcomFormat(offset);
        out += mapRef.get("XVF").datcomFormat(offset);
        out += mapRef.get("ZVF").datcomFormat(offset);
        out += mapRef.get("XV").datcomFormat(offset);
        out += mapRef.get("ZV").datcomFormat(offset);
        out += mapRef.get("HINAX").datcomFormat(offset);

        if(!out.isEmpty())
        {
            out = out.substring(0, out.length() - 2 );
            out = header + out + "$\n";
        }
        return out;
    }

    public String generatePLNFS()
    {
        String [] headers = {"WGPLNF", "HTPLNF", "VTPLNF", "VFPLNF"};
        String [] prefixes = {"WG_", "HT_", "VT_", "VF_"};
        String out = "";
        String offset = "  ";

        for (int i = 0; i < 4; i++)
        {
            String header = " $" + headers[i] + "\n";
            String prefix = prefixes[i];
            String section = "";


            String tag =  mapRef.get(prefix + "AIRFOIL").datcomFormat("");
            if(!tag.isEmpty())
            {
                tag = tag.replace(prefix + "AIRFOIL=", "");
                tag = tag.replace(",", "");
                offset = "   ";
            }
            
            section += mapRef.get(prefix + "SSPNOP").datcomFormat(offset);
            section += mapRef.get(prefix + "SSPNE").datcomFormat(offset);
            section += mapRef.get(prefix + "SSPN").datcomFormat(offset);
            section += mapRef.get(prefix + "CHRDBP").datcomFormat(offset);
            section += mapRef.get(prefix + "CHRDR").datcomFormat(offset);
            section += mapRef.get(prefix + "SAVSI").datcomFormat(offset);
            section += mapRef.get(prefix + "SAVSO").datcomFormat(offset);
            section += mapRef.get(prefix + "CHSTAT").datcomFormat(offset);
            section += mapRef.get(prefix + "CHRDTP").datcomFormat(offset);
            section += mapRef.get(prefix + "TWISTA").datcomFormat(offset);
            section += mapRef.get(prefix + "SSPNDO").datcomFormat(offset);
            section += mapRef.get(prefix + "DHDADI").datcomFormat(offset);
            section += mapRef.get(prefix + "DHDADO").datcomFormat(offset);

            if(!section.isEmpty())
            {
                section = section.substring(0, section.length() - 2 );
                section = section.replaceAll(prefix, "");
                section = tag + header + section + "$\n";
                out += section;

            }
            offset = "  ";
        }
        return out;
    }

    public String generateSCHRS()
    {
        boolean usingY = true;
        String [] headers = {"WGSCHR", "HTSCHR", "VTSCHR", "VFSCHR"};
        String [] prefixes = {"WG_", "HT_", "VT_", "VF_"};
        String out = "";
        String offset = "  ";

        for (int i = 0; i < 4; i++)
        {

            String header = " $" + headers[i] + "\n";
            String prefix = prefixes[i];
            String section = "";
            
            section += mapRef.get(prefix + "T0VC").datcomFormat(offset);
            section += mapRef.get(prefix + "DELTAY").datcomFormat(offset);
            section += mapRef.get(prefix + "X0VC").datcomFormat(offset);
            section += mapRef.get(prefix + "CLI").datcomFormat(offset);
            section += mapRef.get(prefix + "ALPHAI").datcomFormat(offset);
            section += mapRef.get(prefix + "CM0").datcomFormat(offset);
            section += mapRef.get(prefix + "LERI").datcomFormat(offset);
            section += mapRef.get(prefix + "LER0").datcomFormat(offset);
            section += mapRef.get(prefix + "T0VC0").datcomFormat(offset);
            section += mapRef.get(prefix + "X0VC0").datcomFormat(offset);
            section += mapRef.get(prefix + "CM0T").datcomFormat(offset);
            section += mapRef.get(prefix + "CLMAXL").datcomFormat(offset);
            section += mapRef.get(prefix + "CLAMO").datcomFormat(offset);
            section += mapRef.get(prefix + "TCEFF").datcomFormat(offset);
            section += mapRef.get(prefix + "KSHARP").datcomFormat(offset);
            section += mapRef.get(prefix + "ARCC").datcomFormat(offset);
            if(usingY)
            {
                section += mapRef.get(prefix + "XCORD").datcomFormat(offset);
                section += mapRef.get(prefix + "YUPPER").datcomFormat(offset);
                section += mapRef.get(prefix + "YLOWER").datcomFormat(offset);
            }
            else
            {
                section += mapRef.get(prefix + "MEAN").datcomFormat(offset);
                section += mapRef.get(prefix + "THICK").datcomFormat(offset);
            }

            // Fail safe, make sure that an airfoil is not defined in the PLNFS
            // and if it is, clear the namelist text and add nothing
            OAE_LinkInterface guard = mapRef.get(prefix + "AIRFOIL");
            if(guard != null)
            {
                // Make sure the airfoil value is empty
                if(!((String)guard.getValue()).isEmpty())
                {
                    section = "";
                }
            }

            // Make sure the section isnt empty
            if(!section.isEmpty())
            {   
                OAE_LinkedTable temp = (OAE_LinkedTable) mapRef.get(prefix + "XCORD");
                if(temp != null)
                {
                    String blah = "TYPEIN=1.0,\n" + offset + "NPTS=" + temp.getData().size() + ".0,\n" + offset + "XCORD";
                    section = section.replace("XCORD", blah);
                }
                section = section.substring(0, section.length() - 2 );
                section = section.replaceAll(prefix, "");
                section = header + section + "$\n";
                out += section;
            }

        }
        return out;
    }

    public String generatePROPWR()
    {
        String out = "";
        String header = " $PROPWR \n";
        String offset = "  ";
        out += mapRef.get("AIETLP").datcomFormat(offset);
        out += mapRef.get("NENGSP").datcomFormat(offset);
        out += mapRef.get("THSTCP").datcomFormat(offset);
        out += mapRef.get("PHALOC").datcomFormat(offset);
        out += mapRef.get("PHVLOC").datcomFormat(offset);
        out += mapRef.get("PRPRAD").datcomFormat(offset);
        out += mapRef.get("ENGFCT").datcomFormat(offset);
        out += mapRef.get("BWAPR3").datcomFormat(offset);
        out += mapRef.get("BWAPR6").datcomFormat(offset);
        out += mapRef.get("BWAPR9").datcomFormat(offset);
        out += mapRef.get("NOPBPE").datcomFormat(offset);
        out += mapRef.get("BAPR75").datcomFormat(offset);
        out += mapRef.get("YP").datcomFormat(offset);

        if(!out.isEmpty())
        {
            out = out.substring(0, out.length() - 2 );
            out = header + out + "$\n";
            usingProp = true;
        }
        return out;
    }

    public String generateJETPWR()
    {
        String out = "";
        String header = " $JETPWR \n";
        String offset = "  ";
        out += mapRef.get("AIETLJ").datcomFormat(offset);
        out += mapRef.get("NENGSJ").datcomFormat(offset);
        out += mapRef.get("THSTCJ").datcomFormat(offset);
        out += mapRef.get("JIALOC").datcomFormat(offset);
        out += mapRef.get("JEVLOC").datcomFormat(offset);
        out += mapRef.get("JEALOC").datcomFormat(offset);
        out += mapRef.get("JINLTA").datcomFormat(offset);
        out += mapRef.get("JEANGL").datcomFormat(offset);
        out += mapRef.get("JEVELO").datcomFormat(offset);
        out += mapRef.get("AMBTMP").datcomFormat(offset);
        out += mapRef.get("JESTMP").datcomFormat(offset);
        out += mapRef.get("JELLOC").datcomFormat(offset);
        out += mapRef.get("JETOTP").datcomFormat(offset);
        out += mapRef.get("AMBSTP").datcomFormat(offset);
        out += mapRef.get("JERAD").datcomFormat(offset);

        // Make sure a prop namelist hasnt been generated
        if(!out.isEmpty() & (!usingProp))
        {
            out = out.substring(0, out.length() - 2 );
            out = header + out + "$\n";
            return out;
        }
        return "";
    }
}
