/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AIRFOIL_Component;

import Abstracts.AbstractController;
import Services.ImportExportService;
import java.io.File;
import javax.swing.JPanel;

/**
 *
 * @author -B-
 */
public class AirfoilController extends AbstractController
{
    AirfoilView view;
    ImportExportService ies;

    public AirfoilController()
    {
        view = new AirfoilView(this);
        registerForMe();
        registerWithService("Import Export");
        ies = ImportExportService.getInstance();
    }

    @Override
    public void gatherData()
    {
        
    }

    @Override
    public JPanel getView() {
        return view;
    }

    void importAirfoil()
    {
        String [] data = ies.importFile_FC().split("\n");
        String [] d1 = new String[50];
        String [] d2 = new String[50];

        for (int i = 0; i < data.length; i++)
        {
            // Regex time, toss away anything that isnt related to a number
            data[i] = data[i].replaceAll("[^0-9|.|-] ", "");
            System.out.println(data[i]);
        }
    }
}
