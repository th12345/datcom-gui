/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Views.BodyComponent;

import Core.GlobalValue;
import Core.OAE_Component;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTable;
import opendatcom.OpenDatcomController;

/**
 *
 * @author -B-
 */
public class BodyTable implements OAE_Component
{
    BodyView view;
    JTable table;
    Vector<BodyTableEntry> entries;
    Vector<Double> data [];
    Vector<Double> x, r, p, s, zl, zu;

    // Control variables, values represent the column number for the data type
    static int xIndex  = 0;
    static int rIndex  = 1;
    static int pIndex  = 2;
    static int sIndex  = 3;
    static int zuIndex = 4;
    static int zlIndex = 5;


    public BodyTable(BodyView view)
    {
        this.view = view;
        table = this.view.getjBodyTable();
        entries = new Vector<BodyTableEntry>(30);

        // Initialize the data vectors
        x = new Vector<Double>(20);
        r = new Vector<Double>(20);
        s = new Vector<Double>(20);
        p = new Vector<Double>(20);
        zl = new Vector<Double>(20);
        zu = new Vector<Double>(20);

        // Add the entries
        for(int i = 0; i < 20; i++)
        {
            entries.add(new BodyTableEntry(i, view));
        }
    }

    public void refresh()
    {
        table.clearSelection();
        Double [] temp;
        for (BodyTableEntry bodyTableEntry : entries) {
            bodyTableEntry.refresh();
            temp = bodyTableEntry.getData();
            if((temp != null) && !(x.contains(temp[xIndex])))
            {
                x.add(temp[xIndex]);
                r.add(temp[rIndex]);
                p.add(temp[pIndex]);
                s.add(temp[sIndex]);
                zl.add(temp[zlIndex]);
                zu.add(temp[zuIndex]);
            }
        }
        writeBack();
    }

    public void writeBack()
    {
        for (int i = 0; i < x.size(); i++) {
            if(x.get(i) != null)
            {
                table.setValueAt(x.get(i), i, xIndex);
            }
        }
        for (int i = 0; i < r.size(); i++) {
            if(r.get(i) != null)
            {
                table.setValueAt(r.get(i), i, rIndex);
            }
        }
        for (int i = 0; i < s.size(); i++) {
            if(s.get(i) != null)
            {
                table.setValueAt(s.get(i), i, sIndex);
            }
        }
        for (int i = 0; i < p.size(); i++) {
            if(p.get(i) != null)
            {
                table.setValueAt(p.get(i), i, pIndex);
            }
        }
        for (int i = 0; i < zl.size(); i++) {
            if(zl.get(i) != null)
            {
                table.setValueAt(zl.get(i), i, zlIndex);
            }
        }for (int i = 0; i < zu.size(); i++) {
            if(zu.get(i) != null)
            {
                table.setValueAt(zu.get(i), i, zuIndex);
            }
        }
    }

    public String getName() {
        return "BODY_TABLE";
    }

    public void clearTable()
    {
        x.clear();
        r.clear();
        s.clear();
        p.clear();
        zl.clear();
        zu.clear();

        for (Iterator<BodyTableEntry> it = entries.iterator(); it.hasNext();) {
            it.next().clear();

        }
    }

    public void registerLinks()
    {
        OpenDatcomController parent = OpenDatcomController.getInstance();
        parent.addLink(new GlobalValue<Double>("X", x, this));
        parent.addLink(new GlobalValue<Double>("R", r, this));
        parent.addLink(new GlobalValue<Double>("S", s, this));
        parent.addLink(new GlobalValue<Double>("P", p, this));
        parent.addLink(new GlobalValue<Double>("ZL", zl, this));
        parent.addLink(new GlobalValue<Double>("ZU", zu, this));
    }
}
