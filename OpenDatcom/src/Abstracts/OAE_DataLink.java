package Abstracts;

import Services.FormatUtility;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class is the heart of the plug-play functionality of OpenDatcom. Essentially
 * it creates a link between storage data and a user input/output. In addition,
 * the class performs the type-casting, abstract mutator/accessor invocation, and
 * many other unique features that the programmer would normally have to worry about.
 * It calls the approprate utility functions for its' datatype, and handles XML saving
 * and loading internally.
 * @author -B-
 */
public class OAE_DataLink
{
    String name;
    
    // Abstract stufff
    Object view;
    Object data;

    // Function pointers
    Method setMethod;
    Method getMethod;

    FormatUtility fu;

    // Enums
    VIEW_TYPES vType;
    DATA_TYPES dType;

    enum VIEW_TYPES
    {
        JTEXT_A,
        JTEXT_F,
        UNKNOWN
    }

    enum DATA_TYPES
    {
        DOUBLE,
        STRING,
        LINKED_LIST,
        ARRAY_D,
        UNKNOWN
    }

    /**
     * Constructor takes in generic objects, sets the name for the component, and
     * then calls the link function.
     * @param name Name of the component, needs to be unique.
     * @param viewComponent The visible component, ie JTextArea, JTextField... etc
     * @param modelComponent The class of the data to story, ie Double.class, String.class
     */
    public OAE_DataLink(String name, Object viewComponent, Object modelComponent)
    {
        fu = FormatUtility.getInstance();
        try {
            this.name = name;
            link(viewComponent, modelComponent);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(OAE_DataLink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OAE_DataLink(){}

    /**
     * Function does all the dirty work of type determination for the given objects.
     * Once the types are determined (or not..) it sets the function(method) pointers
     * for the view class' accessor/mutator methods and instantiates the data field
     * to be the correct type. Function also sets the internal enum flags to the
     * correct type values.
     * @param viewComponent The visible component, ie JTextArea, JTextField... etc
     * @param modelComponent The class of the data to story, ie Double.class, String.class
     */
    public void link(Object viewComponent, Object modelComponent) throws NoSuchMethodException
    {
        System.out.println("Linking...." + name);

        // Not sure why Java doesn't let you switch off the class type, but oh well.
        // If/else if through all the possible view types and set the internal
        // references as needed.
        if(viewComponent.getClass().equals(JTextArea.class))
        {
            vType = VIEW_TYPES.JTEXT_A;
            view = viewComponent;
            setMethod = JTextArea.class.getMethod("setText", String.class);
            getMethod = JTextArea.class.getMethod("getText");
        }
        else if(viewComponent.getClass().equals(JTextField.class))
        {
            vType = VIEW_TYPES.JTEXT_F;
            view = viewComponent;
            setMethod = JTextField.class.getMethod("setText", String.class);
            getMethod = JTextField.class.getMethod("getText");
        }
        else // If it falls into this else, it is going to break the program...
        {
            setMethod = null; // Yup, null function pointers. Can't see how that
            getMethod = null; // could possibly come back to break things...
            vType = VIEW_TYPES.UNKNOWN;
            view = viewComponent;
            System.out.println("Found UNKNOWN!!!!!");
        }

        if(modelComponent.getClass().equals(Double.class))
        {
            dType = DATA_TYPES.DOUBLE;
            data = new Double(0.0);
        }
        else
        {
            dType = DATA_TYPES.STRING;
            data = new String("");
        }
    }

    /**
     * Updates the internal data reference.
     */
    public void refresh()
    {
        data = fu.processDataField((JTextField)view);
    }

    /**
     * Generates an XML element for the internal data-type.
     * @return A string containing only the XML element.
     */
    public String generateXML_Element()
    {
        if(dType == DATA_TYPES.DOUBLE)
        {
            return fu.xmlWrite(name, fu.processDataField((JTextField)view));
        }
        return fu.xmlWrite(name, fu.processTextField((JTextField)view));
    }

    /**
     * Parses the passed section for data according to the name of the object.
     * @param section The string to parse.
     */
    public void load(String section)
    {
        try {
            setMethod.invoke(view, fu.xmlParse(name, section));
        } catch (IllegalAccessException ex) {
            Logger.getLogger(OAE_DataLink.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(OAE_DataLink.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(OAE_DataLink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String datcomFormat(String offset)
    {
        switch(dType)
        {
            case DOUBLE:
                return fu.safeFormat(offset + name, fu.processDataField((JTextField)view));
            case STRING:
                return fu.safeFormat(offset + name, fu.processTextField((JTextField)view));
            default:
                return "";
        }
    }

    /**
     * Returns the stored object
     * @return The stored object... Cast it to what its supposed to be.
     */
    public Object getData()
    {
        return data;
    }
}
