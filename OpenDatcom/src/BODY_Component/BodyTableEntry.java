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
    Double x;                   // Longitidunal Distance from LOCN
    Double s;                   // Cross-sectional Area at x
    Double p;                   // Perimeter at x
    Double r;                   // Planform half width at x
    Double zu;                  // Z-coordinate of upper surface
    Double zl;                  // Z-coordinate of lower surface
    int tablePosition;
    JTable masterTable;
    BodyView view;

    // Control variables, values represent the column number for the data type
    static int xIndex  = 0;
    static int rIndex  = 1;
    static int pIndex  = 2;
    static int sIndex  = 3;
    static int zuIndex = 4;
    static int zlIndex = 5;

    /**
     * Initializes the table entry with a reference to the parent table and its
     * current table position. The table position value needs to be unique.
     * @param tablePosition
     * @param masterTable
     */
    public BodyTableEntry(int tablePosition, BodyView view)
    {
        this.tablePosition = tablePosition;
        this.view = view;
        this.masterTable = view.getjBodyTable();
        x  = new Double(Double.NaN);
        r  = new Double(Double.NaN);
        s  = new Double(Double.NaN);
        p  = new Double(Double.NaN);
        zl = new Double(Double.NaN);
        zu = new Double(Double.NaN);
    }

    public Double[] getData()
    {
        if((x == null) || Double.isNaN(x))
        {
            return null;
        }
        Double [] temp = {x, r, s, p, zl, zu};
        return temp;
    }

    /**
     * Updates all data from the masterTable.
     */
    public void refresh()
    {
        x = (Double) tableGetCheckCast(xIndex);
        if((x == null) || Double.isNaN(x))
        {
            x = null;
            return;
        }
        r = (Double) tableGetCheckCast(rIndex);
        s = (Double) tableGetCheckCast(sIndex);
        p = (Double) tableGetCheckCast(pIndex);
        zl = (Double) tableGetCheckCast(zlIndex);
        zu = (Double) tableGetCheckCast(zuIndex);
    }

    private Object tableGetCheckCast(int row)
    {
        Object temp = temp = masterTable.getValueAt(tablePosition, row);

        // Make sure the cell is populated
        if(temp == null)
        {
            return null;
        }
        return temp;
    }
}
