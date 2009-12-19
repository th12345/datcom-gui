/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SYNTH_Component;

import Abstracts.AbstractController;
import opendatcom.*;

/**
 *
 * @author -B-
 */
public class SynthesisController extends AbstractController{

    SynthesisView view;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public SynthesisController() {
        this.view = new SynthesisView(this);
        this.xmlTag = "SYNTHESIS";
        this.name = "Synthesis";
        registerWithService("ImportExport");
        registerForMe();

        createLink("XCG",   view.getjXCGText(),     double.class);
        createLink("ZCG",   view.getjZCGText(),     double.class);
        createLink("XW",    view.getjXWText(),      double.class);
        createLink("ZW",    view.getjZWText(),      double.class);
        createLink("ALIW",  view.getjALIWText(),    double.class);
        createLink("XH",    view.getjXHText(),      double.class);
        createLink("ZH",    view.getjZHText(),      double.class);
        createLink("ALIH",  view.getjALIHText(),    double.class);
        createLink("XV",    view.getjXVText(),      double.class);
        createLink("XVF",   view.getjXVFText(),     double.class);
        createLink("ZV",    view.getjZVText(),      double.class);
        createLink("ZVF",   view.getjZVFText(),     double.class);
        createLink("HINAX", view.getjHINAX(),       double.class);
        createLink("SCALE", view.getjScale(),       double.class);
    }

    @Override
    public SynthesisView getView() {
        return view;
    }
}
