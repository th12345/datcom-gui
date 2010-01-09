/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Services.FormatUtility;
import java.util.Vector;

/**
 *
 * @author -B-
 */
public class GlobalValue <T> implements OAE_LinkInterface, OAE_Component{

    Vector<T> value;
    String name;
    OAE_Component view;

    public GlobalValue(String name, T value, OAE_Component view)
    {
        this.name = name;
        this.value = new Vector<T>();
        this.value.add(value);
        this.view = view;
    }

    public GlobalValue(String name, Vector<T> value, OAE_Component view)
    {
        this.name = name;
        this.value = value;
        this.view = view;
    }

    public Object getDataType()
    {
        return value.getClass();
    }

    public Vector<T> getValue() {
        refresh();
        return value;
    }

    public void refresh() {
        if(view != null)
        {
            view.refresh();
        }
    }

    public void link(Object v, Object m) {
        // Do nothing. Class implements an internal value, not a view based value
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings({"unchecked", "unchecked"})
    public void load(String target)
    {
        String temp = FormatUtility.getInstance().xmlParse(name, target);
        if(temp.isEmpty())
        {
            return;
        }

        String [] parsedData = temp.split(",");

        try {
            for(int i = 0; i < parsedData.length; i++)
            {
            value.add((T) Double.valueOf(parsedData[i]));
            }
        } catch (NumberFormatException e)
        {
            
        }


        // Refresh to write back to the view (if required)
        writeBack();
    }

    public String generateXML_Element() 
    {
        refresh();
        return FormatUtility.getInstance().xmlWrite(name, value);
    }

    public String datcomFormat(String offset) 
    {
        refresh();
        String header = offset + name + "=";
        return FormatUtility.getInstance().datcomFormat(header, value, value.size());
    }
    
    public void writeBack() {
        if(view != null)
        {
            view.writeBack();
        }
    }

    /**
     * Sets the first value.
     * @param newValue
     */
    public void setFirst(T newValue)
    {
        value.clear();
        value.add(newValue);
    }

    /**
     * Gets the first value.
     * @return The first object/value
     */
    public T getFirst()
    {
        if(value.size() >= 1)
        {
            return value.firstElement();
        }
        return null;
    }

    public void clear() {
        value.clear();
        writeBack();
    }
}
