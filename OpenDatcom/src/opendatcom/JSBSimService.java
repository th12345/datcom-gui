/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import Abstracts.AbstractService;
import Services.ImportExportService;
import Services.ProjectService;
import Services.FormatUtility;
import java.io.File;

/**
 *
 * @author -B-
 */
public class JSBSimService extends AbstractService
{
    static JSBSimService self;
    ImportExportService ies;
    FormatUtility pu;
    ProjectService ps;

    private JSBSimService()
    {
        ies = ImportExportService.getInstance();
        pu = FormatUtility.getInstance();
        ps = ProjectService.getInstance();
    }

    public static JSBSimService getInstance()
    {
        if(self == null)
        {
            self = new JSBSimService();
        }
        return self;
    }
}
   
