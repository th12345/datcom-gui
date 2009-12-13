/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Abstracts;

import opendatcom.*;
import Services.FormatUtility;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author -B-
 */
public abstract class AbstractController {

    public String outputText = "";
    public String name = "Undefined";
    public String xmlTag = "";
    public AbstractModel model;
    public JPanel view;
    public LinkedList<AbstractController> otherControllers;
    public FormatUtility util = FormatUtility.getInstance();
    public OpenDatcomController parent = OpenDatcomController.getInstance();
    public LinkedList<MVC_DataLink> Links;
    boolean initialized = false;
    
    /**
     * Registers the module with the main app interface. Sets all internal and
     * external references and initializes runtime variables.
     */
    public void registerForMe()
    {
        otherControllers = new LinkedList<AbstractController>();
        parent.registerModule(this);
    }

    public void createLink(String name, Object viewComponent, Object dataType)
    {
        if(!initialized)
        {
            Links = Links = new LinkedList<MVC_DataLink>();
            initialized = true;
            System.out.println("Initialized");
        }
        Links.add(new MVC_DataLink(name, viewComponent, dataType));
    }

    public boolean registerWithService(String serviceName)
    {
        return parent.registerToService(serviceName, this);
    }
    /**
     * Pulls data from the view and stores it in the model.
     */
    public void gatherData(){}

    /**
     * Generates datcom-formatted output from the controller's variables.
     * @return String containing datcom-formatted data.
     */
    public String generateOutput() { return "";}

    /**
     * Refreshes the data in the controller. Cascades down and updates the model
     * and the view. Methods called: gather data, generate output, & view.refresh.
     */
    public void refresh(){};

    /**
     * Parses variable data from the input data. The input data should be in
     * XML format.
     * @param data XML-formatted controller node.
     */
    public void refreshFromSaved(String data)
    {
        String section = util.xmlParse(xmlTag, data);
        if(section.isEmpty())
        {
            return;
        }
        for (int i = 0; i < Links.size(); i++)
        {
            Links.get(i).load(section);
        }
        refresh();
    }

    /**
     * Generates the XML data for the controller's variables. Outputs the data
     * in a formatted string.
     * @return A string containing the XML-formatted variable data.
     */
    public String generateXML()
    {
        String temp = "";
        temp += "<" + xmlTag + ">\n";
        for (int i = 0; i < Links.size(); i++)
        {
            temp += Links.get(i).generateXML_Element();
        }
        temp += "</" + xmlTag + ">\n";
        return temp;
    }

    public abstract JPanel getView();

    /**
     * Gets the controller's datcom output data.
     * @return
     */
    public String getOutput() {
        return outputText;
    }

    /**
     * Sets the output data. DO NOT USE.
     * @param output
     */
    public void setOutput(String output) {
        this.outputText = output;
    }

    /**
     * Gets the controller's unique xml identifier
     * @return The identifier
     */
    public String getXmlTag() {
        return xmlTag;
    }

    /**
     * Sets the controller's unique xml identifier
     * @param xmlTag
     */
    public void setXmlTag(String xmlTag) {
        this.xmlTag = xmlTag;
    }

    public String getName() {
        return name;
    }
}
