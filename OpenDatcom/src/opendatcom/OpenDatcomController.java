/*
 * OpenDatcomController.java
 */

package opendatcom;

import Services.xmlFilter;
import Abstracts.AbstractController;
import SYNTH_Component.SynthesisController;
import PLNF_Component.FlightSurfaceController;
import PLNF_Component.FlightSurfaceModel;
import FLTCON_Component.FlightConditionsController;
import BODY_Component.BodyController;
import Abstracts.AbstractService;
import Services.ImportExportService;
import Services.ProjectService;
import Services.ParserUtility;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
    private MainScreenController mainC;

    // Services
    private DatcomService output;
    private ProjectService ps;
    private JSBSimService jSim;

    // Variables
    String caseName;
    String units;

    private void initModule()
    {
        makeDirs();
        // Init the file chooser
        fc = new JFileChooser();
        fc.setFileFilter(new xmlFilter());
        fc.setCurrentDirectory(new java.io.File(".\\Projects"));
    }

    private void initServices()
    {
        in   = ImportExportService.getInstance();
        ps   = ProjectService.getInstance();
        jSim = JSBSimService.getInstance();
    }

    private void initModules()
    {
        // Set the initial frame size to the system max res
        view.getFrame().setBounds(0, 0, 1200, 700);
        view.getFrame().setResizable(false);
        
        // Initialize the panels. Note that the order matters here, the initialization
        // order determines the tab order
        mainC   =   new MainScreenController();
        flightC =   new FlightConditionsController();
        synthC  =   new SynthesisController();
        bodyC   =   new BodyController();
        wingC   =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.MAIN_WING);
        hTailC  =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.HORIZONTAL_TAIL);
        vTailC  =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.VERTICAL_TAIL);
        output  =   new DatcomService();
        fviewC  =   new FileViewerController();
        caseName = "";
        units = "DIM FT";

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
      * Gets a registered controller by name.
      * @param serviceName
      * @return
      */
     public AbstractController getController(String serviceName)
     {
         AbstractController temp = null;
         for(int i = 0; i < services.size(); i++)
         {
             if(controllers.get(i).getName().equalsIgnoreCase(serviceName))
             {
                 return controllers.get(i);
             }
         }
         return temp;
     }

    /**
     * Registers a module with the application. Modules must be registered during 
     * @param target
     */
    public void registerModule(AbstractController target)
    {
        controllers.add(target);
    }

    /**
     * Registers a controller with a service by the services's name.
     * @param serviceName The service to register to.
     * @param self Reference to the controller to register (this)
     * @return True if the service is found and successfully registered.
     */
    public boolean registerToService(String serviceName, AbstractController self)
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
     * Refreshes the application from an .od save file.
     */
    public void refreshFromSave(String input)
    {
        for(int x = 0; x < controllers.size(); x++)
        {
            controllers.get(x).refreshFromSaved(input);
        }
        
        refresh();
    }

    public String generateXML()
    {
        String temp = "";
        temp += util.xmlWrite("CASE_NAME", caseName);
        temp +=util.xmlWrite("UNITS", units);
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
        // If the project service has been initialized save to the default location
        if(ps.isValid())
        {
            try {
                currentFile = new File(ps.getProjectPath() + "//data.od");
                currentFile.createNewFile();
                in.writeXML(currentFile);
                return;
            } catch (IOException ex) {
                Logger.getLogger(OpenDatcomController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            ps.startProject(currentFile.getName().replace(".od", ""));
            refreshFromSave(in.importFile(currentFile));

            // Set current file to the correct project path
            currentFile = ps.getProjectFile();
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
        fc.setDialogTitle("Select Template Data:");
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
        initServices();
        initModule();
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

    /**
     * Makes any required directories if they dont already exist
     */
    private void makeDirs()
    {
        File tempF = new File(System.getProperty("user.dir") + "\\Saves");
        tempF.mkdirs();
        tempF = new File(System.getProperty("user.dir") + "\\Projects");
        tempF.mkdirs();
    }

    /**
     * Starts the new Project process. Uses projectService routines.
     */
    public void newProject()
    {
        ps.startProject();
        caseName = ps.getName();
        units = "DIM FT";
    }

    public String getCaseName()
    {
        return caseName;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public JFileChooser getFc() {
        return fc;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public void runJSBSimTranslator()
    {
    }
}
