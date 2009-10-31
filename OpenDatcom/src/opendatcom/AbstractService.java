/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.util.LinkedList;

/**
 * Abstract representation of a service. A service exists to provide extended
 * functionality to its registered components. The service will only effect
 * controllers that had been registered with it.
 * @author -B-
 */
public class AbstractService
{
    String name = "default";
    LinkedList<AbstractController> controllers;
    OpenDatcomController parent = OpenDatcomController.getInstance();

    /**
     * Registers the service with the main app & initializes all variables. Should
     * be the called in the constructor of the service.
     */
    public void registerForMe()
    {
        controllers = new LinkedList<AbstractController>();
        parent.registerService(this);
    }

    /**
     * Registers a controller with the service. Doing so allows the access to the
     * services' functionality to the controller.
     * @param target
     */
    public void registerController(AbstractController target)
    {
        // No duplicates allowed
        if(controllers.contains(target))
        {
            return;
        }
        // Register
        controllers.add(target);
    }

    public String getName() {
        return name;
    }
}
