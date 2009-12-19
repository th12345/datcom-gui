/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FLTCON_Component;

import Abstracts.AbstractController;
import Services.ImportExportService;
import javax.swing.JPanel;

/**
 *
 * @author -B-
 */
public class OptionsController extends AbstractController
{
    public OptionsController()
    {
        FlightConditionsView view = FlightConditionsView.getInstance();
        this.xmlTag = "OPTNS";
        this.name = "Flight Options";
        registerWithService("ImportExport");
        registerForMe();

        createLink("SREF",   view.getjSref(),   double.class);
        createLink("BLREF",  view.getjBlref(),  double.class);
        createLink("CBARR",  view.getjCbarr(),  double.class);
    }
    
    @Override
    public JPanel getView()
    {
        return null;
    }
}
