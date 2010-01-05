/*
 * OpenDatcomController.java
 */

package opendatcom;

import Views.CheckView;
import Core.OAE_ViewComponent;
import Services.xmlFilter;
import Views.BodyComponent.BodyView;
import Core.DataServer;
import Core.GlobalValue;
import Core.OAE_LinkInterface;
import Views.FlightConditionsView;
import Views.FlightSurfaceView;
import Views.AirfoilView;
import Views.SynthesisView;
import Services.DatabaseIO;
import Services.ImportExportService;
import Services.ProjectService;
import Services.FormatUtility;
import Services.StreamService;
import Views.Propulsion.Jet;
import Views.Propulsion.Prop;
import java.awt.GridLayout;
import java.io.File;
import java.util.Vector;
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
    private FormatUtility util = FormatUtility.getInstance();
    private DataServer gDataServer;
    private DatabaseIO dio;
    private Vector<OAE_ViewComponent> views;

    // Files
    private File currentFile;
    private File workingDirectory = new File(System.getProperty("user.dir"));
    private JFileChooser fc;

    private SynthesisView           synthView;
    private BodyView                bodyView;
    private FlightSurfaceView       hwPlnf;
    private FlightSurfaceView       htPlnf;
    private FlightSurfaceView       vtPlnf;
    private FlightSurfaceView       vfPlnf;
    private FlightConditionsView    flightView;
    private AirfoilView             wgSchr;
    private AirfoilView             htSchr;
    private AirfoilView             vtSchr;
    private AirfoilView             vfSchr;
    private CheckView               cView;
    private Prop                    propView;
    private Jet                     jetView;


    // Services
    private ProjectService ps;
    private ImportExportService in;

    // Variables
    String caseName;
    String units;

    private void initModule()
    {
        StreamService.GetInstance();
        makeDirs();
        // Init the file chooser
        fc = new JFileChooser();
        fc.setFileFilter(new xmlFilter());
        fc.setCurrentDirectory(new java.io.File(".\\Projects"));
    }

    private void initServices()
    {
        gDataServer = DataServer.getInstance();
        dio         = new DatabaseIO();
        in          = ImportExportService.getInstance();
        ps          = ProjectService.getInstance();
    }

    private void initModules()
    {
        // Set the initial frame size to the system max res
        view.getFrame().setBounds(0, 0, 1200, 700);
        view.getFrame().setResizable(false);
        views = new Vector<OAE_ViewComponent>();

        addLink(new GlobalValue<String>("PROJECT_NAME", "UNDEFINED", null));
        addLink(new GlobalValue<String>("CASE_NAME", "UNDEFINED", null));
        
        // Initialize the panels. Note that the order matters here, the initialization
        // order determines the tab order
        flightView  = new FlightConditionsView();
        bodyView    = new BodyView();
        synthView   = new SynthesisView();
        hwPlnf      = new FlightSurfaceView(FlightSurfaceView.SURFACE_TYPE.WGPLNF);
        wgSchr      = new AirfoilView(AirfoilView.AIRFOIL_TYPE.WGSCHR);
        htPlnf      = new FlightSurfaceView(FlightSurfaceView.SURFACE_TYPE.HTPLNF);
        htSchr      = new AirfoilView(AirfoilView.AIRFOIL_TYPE.HTSCHR);
        vtPlnf      = new FlightSurfaceView(FlightSurfaceView.SURFACE_TYPE.VTPLNF);
        vtSchr      = new AirfoilView(AirfoilView.AIRFOIL_TYPE.VTSCHR);
        vfPlnf      = new FlightSurfaceView(FlightSurfaceView.SURFACE_TYPE.VFPLNF);
        vfSchr      = new AirfoilView(AirfoilView.AIRFOIL_TYPE.VFSCHR);
        propView    = new Prop();
        jetView     = new Jet();
        cView       = new CheckView();

        caseName = "";
        units = "DIM FT";
    }

    /**
     * Start of API methods
     */
    
    /**
     * Saves controller data to an xml file. If a save has occured previously it
     * skips the file chooser dialog and saves over the previous file.
     */
    public void save()
    {
        if(currentFile == null)
        {
            fc.setDialogTitle("Save Project:");
            int check = fc.showOpenDialog(view.getComponent());
            if(check == JFileChooser.APPROVE_OPTION)
            {
                currentFile = fc.getSelectedFile();
                ps.startProject(currentFile.getName().replace(".od", ""));
                // Set current file to the correct project path
                currentFile = ps.getProjectFile();
            }
            else if(check == JFileChooser.CANCEL_OPTION)
            {
                return;
            }
        }
       dio.save(currentFile);
    }

    /**
     * Saves controller data to an xml file. Always shows the file chooser dialog
     */
    public void saveAs()
    {
        fc.setDialogTitle("SaveAs Project:");
        int check = fc.showOpenDialog(view.getComponent());
        if(check == JFileChooser.APPROVE_OPTION)
        {
            currentFile = fc.getSelectedFile();
            ps.startProject(currentFile.getName().replace(".od", ""));
            // Set current file to the correct project path
            currentFile = ps.getProjectFile();
        }
        else if(check == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
        dio.save(currentFile);
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
            dio.load(currentFile);

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
        units = "DIM FT";
    }

    public void register(OAE_ViewComponent aThis)
    {
        JPanel tempJPanel;
        tempJPanel = new JPanel();
        tempJPanel.setLayout(new GridLayout(1,0));
        tempJPanel.setName(aThis.getName());
        tempJPanel.add(aThis);
        view.addTab(tempJPanel);
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

    public void addLink(OAE_LinkInterface link)
    {
        gDataServer.addLink(link);
    }

    public OAE_LinkInterface getLink(String name)
    {
        return gDataServer.getLink(name);
    }
}
