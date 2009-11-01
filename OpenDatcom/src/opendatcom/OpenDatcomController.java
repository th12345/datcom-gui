/*
 * OpenDatcomController.java
 */

package opendatcom;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application. Does not actually extend AbstractController
 * because of Java's single-inheritance limit but it is manually implemented as one.
 * The class is functions as the main controller for the entire application and
 * provides an API for module/service implementation and registration.
 */
public class OpenDatcomController extends SingleFrameApplication{

    // MVC stuff
    OpenDatcomView view;
    
    // Golbal Stuff
    private ParserUtility util = ParserUtility.getInstance();
    private LinkedList<AbstractController> controllers = new LinkedList<AbstractController>();
    private LinkedList<AbstractService> services    = new LinkedList<AbstractService>();

    // Files
    private File currentFile;
    private File workingDirectory = new File(System.getProperty("user.dir"));
    private JFileChooser fc;

    // Controllers
    private BodyController bodyC;
    private SynthesisController synthC;
    private FlightConditionsController flightC;
    private FlightSurfaceController wingC, hTailC, vTailC;
    private ImportExportService in;
    private FileViewerController fviewC;

    // Services
    private OutputView output;
    private ProjectService ps;

    private void initModule()
    {
        makeDirs();
        
        // Init the file chooser
        fc = new JFileChooser();
        fc.setFileFilter(new xmlFilter());
        fc.setCurrentDirectory(new java.io.File(".\\Saves"));
    }

    private void initServices()
    {
        in   = ImportExportService.getInstance();
        ps = ProjectService.getInstance();
        ps.startProject();
    }

    private void initModules()
    {
        // Initialize the panels. Note that the order matters here, the initialization
        // order determines the tab order
        flightC =   new FlightConditionsController();
        synthC  =   new SynthesisController();
        bodyC   =   new BodyController();
        wingC   =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.MAIN_WING);
        hTailC  =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.HORIZONTAL_TAIL);
        vTailC  =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.VERTICAL_TAIL);
        output  =   new OutputView();
        fviewC  =   new FileViewerController();

        // Iterate through and add the modules to the tab frame.
        JPanel tempJPanel;
        for(int x = 0; x < controllers.size(); x++)
        {
            tempJPanel = new JPanel();
            tempJPanel.setLayout(new GridLayout(1,0));
            tempJPanel.setName(controllers.get(x).getName());
            tempJPanel.add((controllers.get(x)).getView());
            view.addTab(tempJPanel);
        }

        tempJPanel = new JPanel();
        tempJPanel.setLayout(new GridLayout(1,0));
        tempJPanel.setName("Output");
        tempJPanel.add(output);

        output.registerController(flightC);
        output.registerController(synthC);
        output.registerController(bodyC);
        output.registerController(wingC);
        output.registerController(hTailC);
        output.registerController(vTailC);
        
        view.addTab(tempJPanel);

    }

    /**
     * Start of API methods
     */

    /**
     * Registers a service with the application. Services must be registered during 
     * inititalization. The target MUST have its name set to something uniquie.
     * @param target
     */
     public void registerService(AbstractService target)
     {
        services.add(target);
     }

     public AbstractService getService(String serviceName)
     {
         AbstractService temp = null;
         for(int i = 0; i < services.size(); i++)
         {
             if(services.get(i).getName().equalsIgnoreCase(serviceName))
             {
                 return services.get(i);
             }
         }
         return temp;
     }

    /**
     * Registers a module with the application. Modules must be registered during 
     * @param target
     */
    void registerModule(AbstractController target)
    {
        controllers.add(target);
    }

    /**
     * Registers a controller with a service by the services's name.
     * @param serviceName The service to register to.
     * @param self Reference to the controller to register (this)
     * @return True if the service is found and successfully registered.
     */
    boolean registerToService(String serviceName, AbstractController self)
    {
        for(int i = 0; i < services.size(); i++)
        {
            if(services.get(i).getName().equalsIgnoreCase(serviceName))
            {
                //TODO: prevent duplicate registrations here
                services.get(i).registerController(self);
                return true;
            }
        }
        return false;
    }

    /**
     * Refreshes the application from an xml save file.
     */
    public void refreshFromSave(String input)
    {
        String temp = util.xmlParse("Units", input);
        if(temp.equalsIgnoreCase("Metric"))
        {
            view.getjUnitsSelect().setSelectedIndex(1);
        }

        temp = util.xmlParse("Case", input);
        view.getjCaseName().setText(temp);

        for(int x = 0; x < controllers.size(); x++)
        {
            controllers.get(x).refreshFromSaved(input);
        }
        
        refresh();
    }

    public String generateXML()
    {
        String temp = "";
        temp += util.xmlWrite("Case", view.getjCaseName().getText());
        temp += util.xmlWrite("Units", view.getjUnitsSelect().getSelectedItem().toString());
        return temp;
    }

    /**
     * Refreshes the application & all registered modules.
     */
    public void refresh()
    {
        for(int i = 0; i < controllers.size(); i++)
        {
            controllers.get(i).refresh();
        }
    }

    /**
     * Saves controller data to an xml file. If a save has occured previously it
     * skips the file chooser dialog and saves over the previous file.
     */
    public void save()
    {
        // Check if user has saved to a file before
        if(currentFile == null)
        {
           saveAs();
        }
        // If user has already saved, don't display the select file box
        else
        {
            in.writeXML(currentFile);
        }
        
    }

    /**
     * Saves controller data to an xml file. Always shows the file chooser dialog
     */
    public void saveAs()
    {
        fc.setDialogTitle("Save:");
        int check = fc.showSaveDialog(view.getComponent());
        if(check == JFileChooser.APPROVE_OPTION)
        {
            try {
                currentFile = fc.getSelectedFile();
                if (!currentFile.getName().contains(".xml"))
                {
                    currentFile = new File(currentFile.getAbsoluteFile() + ".xml");
                }
                currentFile.createNewFile();
                in.writeXML(currentFile);
            } catch (IOException ex) {
                Logger.getLogger(OpenDatcomView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(check == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
    }

    /**
     * Opens an OpenDatcom format xml save. Imports all data into the approprate
     * fields and refreshes the module. Sets the program to save into the opened
     * file automatically when save is called.
     */
    public void open()
    {
        fc.setDialogTitle("Open Project:");
        int check = fc.showOpenDialog(view.getComponent());
        if(check == JFileChooser.APPROVE_OPTION)
        {
            currentFile = fc.getSelectedFile();
            refreshFromSave(in.importFile(currentFile));
        }
        else if(check == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
    }

    /**
     * Opens an OpenDatcom format xml save. Imports all data into the approprate
     * fields and refreshes the module. Does not set the imported file as the
     * working file (it will not be saved over).
     */
    public void openTemplate()
    {
        fc.setDialogTitle("Select Template:");
        int check = fc.showOpenDialog(view.getComponent());
        if(check == JFileChooser.APPROVE_OPTION)
        {
            File target = fc.getSelectedFile();
            in.importFile(target);
            refresh();
        }
        else if(check == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //GENERATED METHODS
    ////////////////////////////////////////////////////////////////////////////

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new OpenDatcomView(this));
        view = OpenDatcomView.getInstance();
        initModule();
        initServices();
        initModules();
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of OpenDatcomController
     */
    public static OpenDatcomController getInstance() {
        return Application.getInstance(OpenDatcomController.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(OpenDatcomController.class, args);
    }

    private void makeDirs()
    {
        File tempF = new File(System.getProperty("user.dir") + "\\Saves");
        tempF.mkdirs();
        tempF = new File(System.getProperty("user.dir") + "\\Projects");
        tempF.mkdirs();
    }

    public String getUnits()
    {
        return view.getUnits();
    }

    public String getCaseName()
    {
        return view.getCaseName();
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public JFileChooser getFc() {
        return fc;
    }
}
