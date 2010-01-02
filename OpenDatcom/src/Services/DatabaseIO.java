/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Core.DataServer;
import Core.OAE_DataLink;
import Core.OAE_LinkInterface;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author -B-
 */
public class DatabaseIO
{
    private ImportExportService inOut;
    private FormatUtility fu;

    public DatabaseIO()
    {
        inOut = ImportExportService.getInstance();
    }

    public void load(File target)
    {
        String input = inOut.importFile(target);
        Collection<OAE_LinkInterface> entries = DataServer.getInstance().getMap().values();
        for (Iterator<OAE_LinkInterface> it = entries.iterator(); it.hasNext();) {
            it.next().load(input);
        }
    }

    public void save(File target)
    {
        String output = "<OAE_SAVE>\n";
        
        Collection<OAE_LinkInterface> entries = DataServer.getInstance().getMap().values();
        for (Iterator<OAE_LinkInterface> it = entries.iterator(); it.hasNext();) {
            output += it.next().generateXML_Element();
        }
        output += "</OAE_SAVE>";
        
        inOut.writeFile(target, output);
    }
}
