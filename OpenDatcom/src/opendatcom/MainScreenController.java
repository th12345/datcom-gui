/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import Abstracts.AbstractController;
import javax.swing.JPanel;

/**
 *
 * @author -B-
 */
class MainScreenController extends AbstractController{

    MainScreenView view;
    MainScreenModel model;
    static MainScreenController self;

    public MainScreenController()
    {
        name = "Main";
        view = new MainScreenView(this);
        registerForMe();
    }

    public void test()
    {
        parent.runJSBSimTranslator();
    }

    public MainScreenController GetInstance()
    {
        if(self == null)
        {
            self = new MainScreenController();
        }
        return self;
    }

    @Override
    public JPanel getView() {
        return view;
    }
}
