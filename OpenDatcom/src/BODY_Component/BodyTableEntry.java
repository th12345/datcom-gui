/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BODY_Component;

import javax.swing.JTable;

/**
 *
 * @author -B-
 */
public class BodyTableEntry
{
    double x;                   // Longitidunal Distance from LOCN
    double s;                   // Cross-sectional Area at x
    double p;                   // Perimeter at x
    double r;                   // Planform half width at x
    double zu;                  // Z-coordinate of upper surface
    double zl;                  // Z-coordinate of lower surface
    int tablePosition;
    JTable masterTable;
    BodyController controller;
    boolean isValid;
    boolean usingZ;

    /**
     * Initializes the table entry with a reference to the parent table and its
     * current table position. The table position value needs to be unique.
     * @param tablePosition
     * @param masterTable
     */
    public BodyTableEntry(int tablePosition, BodyController controller, JTable masterTable)
    {
        this.tablePosition = tablePosition;
        this.masterTable = masterTable;
        this.controller = controller;
        isValid = false;
    }

    /**
     * Updates all data from the masterTable.
     */
    public void refresh()
    {
        clear();
        try {
            // If things get rearranged in the table make sure to change the coloums
            if(!String.valueOf(masterTable.getValueAt(tablePosition, 0)).equals("null"))
            {
                x =   Double.parseDouble(masterTable.getValueAt(tablePosition, 0).toString());
                isValid = true;
            }
            else // The x value is a special case, it MUST be valid to continue
            {
                x = Double.NaN;
                return;
            }
            
            if(!String.valueOf(masterTable.getValueAt(tablePosition, 1)).equals("null"))
            {
                r =   Double.parseDouble(masterTable.getValueAt(tablePosition, 1).toString());
            }
            if(!String.valueOf(masterTable.getValueAt(tablePosition, 2)).equals("null"))
            {
                p =   Double.parseDouble(masterTable.getValueAt(tablePosition, 2).toString());
            }
            if(!String.valueOf(masterTable.getValueAt(tablePosition, 3)).equals("null"))
            {
                s =   Double.parseDouble(masterTable.getValueAt(tablePosition, 3).toString());
            }
            if(!String.valueOf(masterTable.getValueAt(tablePosition, 4)).equals("null"))
            {
                zu =  Double.parseDouble(masterTable.getValueAt(tablePosition, 4).toString());
            }
            if(!String.valueOf(masterTable.getValueAt(tablePosition, 5)).equals("null"))
            {
                zl =  Double.parseDouble(masterTable.getValueAt(tablePosition, 5).toString());
            }
            validate();
        } catch (NumberFormatException e) 
        {
            System.out.println("Parsing Body input table failed!");
        }
    }

    /**
     * Validates the refreshed data, abs' all values that cannot be negative.
     */
    private void validate()
    {
        // Abs the values to nuke illegial (negative) values...
        Math.abs(r);
        Math.abs(s);
        Math.abs(p);

        // Switch off the user's choice of DATCOM inputs
        switch(controller.getInputMode())
        {
            // Calculate the missing values according to the input choice
            case USING_P:
            {
                r = p / (2 * Math.PI);    // P = 2*Pi*R
                s = r * r * Math.PI;      // A = PI * r^2
                zl = -r;
                zu = r;
                break;
            }
            case USING_S:
            {
                if(Double.isNaN(s))
                {
                    isValid = false;
                    return;
                }

                r = Math.sqrt( s / Math.PI);
                p = 2 *  Math.PI * r;
                zl = 0;
                zu = 0;
                break;
            }
            case USING_R:
            {
                if(Double.isNaN(r))
                {
                    isValid = false;
                    return;
                }

                p = 2 * Math.PI * r;
                s = Math.PI * r * r;
                zl = -r;
                zu = r;
                break;
            }
            case USING_PR:
            {
                if(Double.isNaN(r) || Double.isNaN(p))
                {
                    isValid = false;
                    return;
                }
                s = Math.PI * r * r;
                zl = -r;
                zu = r;
                break;
            }
            case USING_SR:
            {
                if(Double.isNaN(r) || Double.isNaN(s))
                {
                    isValid = false;
                    return;
                }
                p = 2 *  Math.PI * r;
                zl = -r;
                zu = r;
                break;
            }
            case USING_SP:
            {
                if(Double.isNaN(s) || Double.isNaN(p))
                {
                    isValid = false;
                    return;
                }
                zl = -r;
                zu = r;
                r = Math.sqrt( s / Math.PI );
                break;
            }
            case USING_Z:
            {
                if(Double.isNaN(zl) || Double.isNaN(zu))
                {
                    isValid = false;
                    return;
                }
                r = (Math.abs(zu) + Math.abs(zl)) / 2;
                p = 2 * Math.PI * r;
                s = Math.PI * r * r;
                break;
            }
        }
        writeBack();
    }

    /**
     * Writes the filtered and calculated values back to the master table.
     */
    private void writeBack()
    {
        masterTable.setValueAt(r, tablePosition, 1);
        masterTable.setValueAt(p, tablePosition, 2);
        masterTable.setValueAt(s, tablePosition, 3);
        masterTable.setValueAt(zu, tablePosition, 4);
        masterTable.setValueAt(zl, tablePosition, 5);
    }

    /**
     * Compares two body table entries based on their x coordinate,
     * return true if the caller is smaller then the target.
     * @param target The entry to compare with
     * @return True if the target is larger then the caller object.
     */
    public boolean compare(BodyTableEntry target)
    {
        if(this.x < target.x)
        {
            return true;
        }
        return false;
    }

    /**
     * Clears the entry data but leaves the current position
     */
    public void clear()
    {
        p  = Double.NaN;
        s  = Double.NaN;
        x  = Double.NaN;
        r  = Double.NaN;
        zl = Double.NaN;
        zu = Double.NaN;
        isValid = false;
        usingZ  = false;
    }
}
