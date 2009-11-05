/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.File;

/**
 *
 * @author -B-
 */
public class JSBSimService extends AbstractService
{
    static JSBSimService self;
    ImportExportService ies;
    ParserUtility pu;

    private JSBSimService()
    {
        ies = ImportExportService.getInstance();
        pu = ParserUtility.getInstance();
    }

    public JSBSimService getInstance()
    {
        if(self == null)
        {
            self = new JSBSimService();
        }
        return self;
    }

    public void generateOutput()
    {
        String data = ies.importFile(new File(parent.getWorkingDirectory() + "\\for006.dat"));
        
    }
}
