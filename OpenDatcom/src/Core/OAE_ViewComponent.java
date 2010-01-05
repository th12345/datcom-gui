/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import javax.swing.JPanel;
import opendatcom.OpenDatcomController;

/**
 *
 * @author -B-
 */
public abstract class OAE_ViewComponent extends JPanel
{
    public OpenDatcomController parent;
    public String name;

    public void initView(String name)
    {
        this.name = name;
        parent = OpenDatcomController.getInstance();
        parent.register(this);
        registerLinks();
        parent.addLink(new GlobalValue<Boolean>(name, true, null));
    }

    @Override
    public String getName(){ return name; }

    public abstract void registerLinks();
}
