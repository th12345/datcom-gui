package BODY_Component;

import Abstracts.AbstractController;
import java.util.LinkedList;

/**
 * Controller method for the BodyModel model and the BodyModel View.
 * @author -B-
 */
public class BodyController extends AbstractController {

    BodyModel model;
    BodyView view;
    private int wrapNum = 3;
    INPUT_CHOICE inputMode;
    LinkedList<BodyTableEntry> tableEntries;
    LinkedList<Double> x;


    public enum INPUT_CHOICE
     {
         USING_S,
         USING_P,
         USING_R,
         USING_SP,
         USING_PR,
         USING_SR,
         USING_Z
     }
    
    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public BodyController() {
        this.view = new BodyView(this);
        this.model = BodyModel.getInstance();
        this.xmlTag = "BODY";
        this.name = "Body";
        registerWithService("ImportExport");
        registerForMe();
        tableEntries = new LinkedList<BodyTableEntry>();
        x = new LinkedList<Double>();
        
        for (int i = 0; i < 20; i++)
        {
            tableEntries.add(new BodyTableEntry(i, this, view.getjBodyTable()));
        }
    }

    /**
     * Gathers input data from the view and updates the model
     */
    @Override
    public void gatherData()
    {
        inputMode = view.getInputMode();
        model.getX().clear();
        double [] p = new double[20];
        double [] r = new double[20];
        double [] s = new double[20];
        double [] zl = new double[20];
        double [] zu = new double[20];

        model.setBla(util.processDataField(view.getjBLA()));
        model.setBln(util.processDataField(view.getjBLN()));
        model.setBnose(util.processDataField(view.getjBNOSE()));
        model.setBtail(util.processDataField(view.getjBTAIL()));

        // Update the table entries
        for (int i = 0; i < 20; i++)
        {
            tableEntries.get(i).refresh();
            if(tableEntries.get(i).isValid)
            {
                x.add(tableEntries.get(i).x);
                r[i]  = tableEntries.get(i).r;
                p[i]  = tableEntries.get(i).p;
                s[i]  = tableEntries.get(i).s;
                zl[i] = tableEntries.get(i).zl;
                zu[i] = tableEntries.get(i).zu;
            }
        }

        // Update the model
        model.setX(x);
        model.setP(p);
        model.setS(s);
        model.setR(r);
        model.setZl(zl);
        model.setZu(zu);
    }

     void sortTable()
     {
         BodyTableEntry temp;
         // Bubblesort....
         for (int i = 0; i < 20; i++)
         {
             for(int j = i; j < 20; j++)
             {
                 if(!tableEntries.get(i).compare(tableEntries.get(j)) & tableEntries.get(j).isValid)
                 {
                    temp = tableEntries.get(i);
                    tableEntries.set(i, tableEntries.get(j));
                    tableEntries.set(j, temp);
                 }
             }
         }
     }

    /**
     * Refreshes the entire model/view/controllers links & formats the datcom
     * output data.
     */
    @Override
    public void refresh()
    {
        view.getjBodyTable().clearSelection();
        gatherData();
        generateOutput();
        sortTable();
    }

    /**
     * Creates and formats the datcom data. 
     */
    @Override
    public String generateOutput()
    {
        // Make sure the body parameters are defined, if not abort
        if(model.getX().size() == 0)
        {
            return "";
        }
        
        String temp = "";
        x = model.getX();
        double [] zl = model.getZl();
        double [] zu = model.getZu();
        double [] r = model.getR();
        double [] p = model.getP();
        double [] s = model.getS();
        int length = x.size();
        
        temp += "# Start of Body Parameters\n";
        temp += " $BODY\n";
        temp += util.safeFormat("  BLN=", model.getBln());
        temp += util.safeFormat("  BLA=", model.getBla());
        temp += util.safeFormat("  BNOSE=", model.getBnose());
        temp += util.safeFormat("  BTAIL=", model.getBtail());
        temp += "  NX=\t" + length + ".0,\n";

        temp += util.datcomFormat("  X(1)=\t", x, length);
        temp += util.datcomFormat("  S(1)=\t", s, length);
        temp += util.datcomFormat("  P(1)=\t", p, length);
        temp += util.datcomFormat("  R(1)=\t", r, length);
        temp += util.datcomFormat("  ZU(1)=\t", zu, length);
        temp += util.datcomFormat("  ZL(1)=\t", zl, length);

        // Trim off the extra comma
        temp = temp.substring(0, temp.length() - 3);
        temp += "$\n#End of BODY Parameters\n\n";
        view.setOutputData(temp);
        return temp;
    }

    @Override
    public void refreshFromSaved(String data) {
        String section = util.xmlParse(xmlTag, data);
        if(section.isEmpty())
        {
            return;
        }

        view.getjBLA().setText(util.xmlParse("BLA", section));
        view.getjBLN().setText(util.xmlParse("BLN", section));
        view.getjBNOSE().setText(util.xmlParse("BNOSE", section));
        view.getjBTAIL().setText(util.xmlParse("BTAIL", section));
        
        String radii = util.xmlParse("RADII", section);
        String xValues = util.xmlParse("XVALUES", section);
        
        radii = radii.replaceAll(" ", "");
        String [] radiiValues = radii.split(",");
        String [] XValues = xValues.split(",");

        for(int i = 0; i < XValues.length; i++)
        {
            view.getjBodyTable().setValueAt(Double.parseDouble(XValues[i]), i, 0);
            view.getjBodyTable().setValueAt(Double.parseDouble(radiiValues[i]), i, 1);
        }
        refresh();
    }

    @Override
    public String generateXML()
    {
        // Init temps
        String temp = "";
        String array = "";
        double [] radii = model.getR();
        x = model.getX();

        if(radii.length == 0 || x.size() == 0)
        {
            return "";
        }


        // Start of xml formatting
        temp += "<" + xmlTag + ">\n";
        temp += util.xmlWrite("BLN", model.getBln());
        temp += util.xmlWrite("BLA", model.getBla());
        temp += util.xmlWrite("BNOSE", model.getBnose());
        temp += util.xmlWrite("BTAIL", model.getBtail());
        // Fill in temp with values then parse
        for(int i = 0; i < x.size(); i++)
        {
            array += x.get(i) + ", ";
        }
        
        // Get rid of extra comma
        array = array.substring(0, array.length() - 2);
        temp += util.xmlWrite("XVALUES", array);
        array = "";
        // Fill in temp with values then parse
        for(int i = 0; i < x.size(); i++)
        {
            array += radii[i] + ", ";
        }

        // Get rid of extra comma
        array = array.substring(0, array.length() - 2);
        temp += util.xmlWrite("RADII", array);
        temp += "</" + xmlTag + ">\n";
        return temp;
    }
    
    public INPUT_CHOICE getInputMode() {
        return inputMode;
    }

    /**
     * Clears the data from the BodyModel Table and nulls the old values in the model.
     */
    public void clearTable()
    {
        for (int i = 0; i < 20; i++)
        {
            tableEntries.get(i).clear();
        }

        view.getjBodyTable().removeAll();
    }

    public BodyModel getModel() {
        return model;
    }

    public BodyView getView() {
        return view;
    }
}
