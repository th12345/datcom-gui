/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Core.DataServer;
import Core.OAE_LinkInterface;
import Core.OAE_LinkedTable;
import com.sun.corba.se.spi.activation._ActivatorImplBase;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author -B-
 */
public class FortranFormat
{
    HashMap<String, OAE_LinkInterface> mapRef;
    FormatUtility fu;
    public FortranFormat()
    {
        mapRef = DataServer.getInstance().getMap();
    }

    public String generateForFile()
    {
        String out = "CASEID TEST\n";
        out += generateFLTCON();
        out += generateSYNTHS();
        out += generateOPTINS();
        out += generateBODY();
        out += generatePLNFS();
        out = out.replaceAll("\t", "");
        return out;
    }

    public String generateFLTCON()
    {
        String out = "";
        String header = " $FLTCON\n";
        String offset = "  ";
        out += ((OAE_LinkedTable)mapRef.get("MACH")).datcomFormat(offset);
        out += ((OAE_LinkedTable)mapRef.get("ALT")).datcomFormat(offset);
        out += ((OAE_LinkedTable)mapRef.get("ALSCHD")).datcomFormat(offset);
        out += mapRef.get("WT").datcomFormat(offset);
        out += mapRef.get("STMACH").datcomFormat(offset);
        out += mapRef.get("TSMACH").datcomFormat(offset);
        out += mapRef.get("GAMMA").datcomFormat(offset);
        out += mapRef.get("TR").datcomFormat(offset);
        out += mapRef.get("LOOP").datcomFormat(offset);

        if(!out.isEmpty())
        {
            out = out.substring(0, out.length() - 2 );
            out = header + out + "$\n";
            out = out.replaceAll("NALSCHD", "NALPHA");
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
        String offset = "   ";

        for (int i = 0; i < 4; i++)
        {
            String header = " $" + headers[i] + "\n";
            String prefix = prefixes[i];
            String section = "";

            String tag =  mapRef.get(prefix + "AIRFOIL").datcomFormat("");
            tag = tag.replace(prefix + "AIRFOIL=", "");
            tag = tag.replace(",", "");
            
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
        }
        return out;
    }
}
