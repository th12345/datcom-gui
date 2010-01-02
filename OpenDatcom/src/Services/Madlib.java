/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Core.DataServer;
import Core.OAE_DataLink;
import Core.OAE_LinkInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author -B-
 */
public class Madlib
{
    ImportExportService ies;
    FormatUtility fu;

    public Madlib()
    {
        fu = FormatUtility.getInstance();
        ies = ImportExportService.getInstance();
    }

    public String templateReplace()
    {
        String raw = ies.importFile_FC();
        String data = "";
        String token = "<&data>";
        String aToken = "";
        Collection<Entry<String, OAE_LinkInterface>> entries = DataServer.getInstance().getMap().entrySet();

        for (Iterator<Entry<String, OAE_LinkInterface>> it = entries.iterator(); it.hasNext();)
        {
            Entry<String, OAE_LinkInterface> entry = it.next();
            aToken = token.replace("data", entry.getKey());
            data = String.valueOf(entry.getValue().getValue());
            raw = raw.replaceAll(aToken, data);
        }
        //raw = raw.replaceAll(",.*{0,10}NaN,", "");    // Catch NaN
        //raw = raw.replaceAll(",.*{0,10}null,", "");   // Catch null
        StreamService.print("Madlib Output: " + raw);
        return raw;
    }
}
