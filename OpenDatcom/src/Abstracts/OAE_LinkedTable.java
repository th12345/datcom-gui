/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Abstracts;

import Services.FormatUtility;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author -B-
 */
public class OAE_LinkedTable extends OAE_DataLink
{
    int columnIndex;
    int rowCount;
    boolean autoSort;
    JTable table;
    Vector<Double> data;

    public OAE_LinkedTable(JTable viewComponent, int columnIndex, String name)
    {
        fu = FormatUtility.getInstance();
        this.name = name;
        this.columnIndex = columnIndex;
        this.rowCount = viewComponent.getRowCount();
        this.table = viewComponent;
        if(columnIndex > table.getColumnCount())
        {
            columnIndex = table.getColumnCount();
        }
        data = new Vector<Double>(20);
    }

    @Override
    public void refresh() 
    {
        data.clear();
        for (int i = 0; i < rowCount; i++)
        {
            if(!String.valueOf(table.getValueAt(i, columnIndex)).equals("null"))
            {
                data.add(Double.parseDouble(table.getValueAt(i, 1).toString()));
            }
        }
    }

    @Override
    public String generateXML_Element()
    {
        fu.xmlWrite(name, data);
        return "";
    }

    public String datcomFormat(String offset)
    {
        return fu.datcomFormat(offset + name + "(1)=", data, data.size());
    }
    
    @Override
    public Object getData()
    {
        return data;
    }
    
    @Override
    public void load(String target)
    {
        String temp = fu.xmlParse(name, target);
        if(temp.isEmpty())
        {
            return;
        }
        
        String [] parsedData = temp.split(",");

        for(int i = 0; i < parsedData.length; i++)
        {
            table.setValueAt(Double.parseDouble(parsedData[i]), i, columnIndex);
            data.add(Double.parseDouble(parsedData[i]));
        }
    }
}
