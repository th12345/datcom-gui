/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SCHR_Component;

import Abstracts.AbstractController;
import javax.swing.JPanel;

/**
 *
 * @author -B-
 */
public class AirfoilController extends AbstractController {

    AirfoilView view;

    public AirfoilController() {
        view = new AirfoilView();
        this.name = "Airfoil Controller";
        this.xmlTag = "AFOIL";
        registerWithService("ImportExport");
        registerForMe();

        createLink("T0VC",      view.getjT0VC(),    double.class);
        createLink("DELTAY",    view.getjDELTAY(),  double.class);
        createLink("X0VC",      view.getjX0VC(),    double.class);
        createLink("CLI",       view.getjCLI(),     double.class);
        createLink("ALPHAI",    view.getjALPHAI(),  double.class);
        createLink("CM0",       view.getjCM0(),     double.class);
        createLink("LERI",      view.getjLERI(),    double.class);
        createLink("LER0",      view.getjLER0(),    double.class);
        createLink("T0VC0",     view.getjT0VC0(),   double.class);
        createLink("X0VC0",     view.getjX0VC0(),   double.class);
        createLink("CM0T",      view.getjCM0T(),    double.class);
        createLink("CLMAXL",    view.getjCLMAXL(),  double.class);
        createLink("CLAMO",     view.getjCLAM0(),   double.class);
        createLink("CM0T",      view.getjCM0T(),    double.class);
        createLink("TCEFF",     view.getjTCEFF(),   double.class);
        createLink("KSHARP",    view.getjKSHARP(),  double.class);
        createLink("ARCC",      view.getjARCC(),    double.class);

    }


    
    @Override
    public JPanel getView() {
        return view;
    }

}
