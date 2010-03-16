/* Aleksey Matyushev / Alan Teeder
 *
 * This program is the sole property of Aleksey
 * Matyushev and Alan Teeder. This program is designed to be an
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear /JSBSim.
 */
package dd2jsb;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class dd2jsbApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new dd2jsbMAIN(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override  protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of dd2jsbApp
     */
    public static dd2jsbApp getApplication() {
        return Application.getInstance(dd2jsbApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(dd2jsbApp.class, args);
    }
}
