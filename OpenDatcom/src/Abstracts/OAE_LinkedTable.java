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

    public OAE_LinkedTable(String name, JTable viewComponent, int columnIndex)
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
                data.add(Double.parseDouble(table.getValueAt(i, columnIndex).toString()));
            }
        }
    }

    @Override
    public String generateXML_Element()
    {
        return  fu.xmlWrite(name, data);
    }

    @Override
    public String datcomFormat(String offset)
    {
        String temp = fu.datcomFormat(offset + name + "(1)=", data, data.size());
        temp = offset + "N" + name + "=" + data.size() + ",\n" + temp;
        return temp;
    }
    
    @Override
    public Vector<Double> getData()
    {
        return data;
    }

    public void setData(Vector<Double> newData)
    {
        data = newData;
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

    public int getSize()
    {
        return data.size();
    }
}
