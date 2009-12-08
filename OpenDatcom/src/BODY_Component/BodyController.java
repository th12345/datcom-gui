package BODY_Component;

import Abstracts.AbstractController;
import opendatcom.*;
import javax.swing.JTable;

/**
 * Controller method for the BodyModel model and the BodyModel View.
 * @author -B-
 */
public class BodyController extends AbstractController {

    BodyModel model;
    BodyView view;
    private int wrapNum = 4;
    
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
    }

    /**
     * Gathers input data from the view and updates the model
     */
    @Override
    public void gatherData()
    {
        double [][] temp = processBodyTable(view.getjBodyTable());
        model.setxValues(temp[0]);
        model.setRadii(temp[1]);
    }

    /**
     * Refreshes the entire model/view/controllers links & formats the datcom
     * output data.
     */
    @Override
    public void refresh()
    {
        gatherData();
        generateOutput();
    }

    /**
     * Creates and formats the datcom data. 
     */
    @Override
    public String generateOutput()
    {
        // Make sure the body parameters are defined, if not abort
        if(model.getRadii().length == 0 || model.getxValues().length == 0)
        {
            return "";
        }
        String temp = "";
        double [] xValues = model.getxValues();
        double [] radii = model.getRadii();
        
        temp += "# Start of Body Parameters\n";
        temp += " $BODY\n";
        temp += "  NX=\t" + xValues.length + ".0,\n";
        temp += "  X(1)=\t";
        for(int i = 0; i < xValues.length; i++)
        {
            temp += xValues[i] + ",\t";
            if(i%5 == wrapNum)
            {
                temp += "\n   ";
            }
        }

        temp += "\n  R(1)=\t";
        for(int i = 0; i < xValues.length; i++)
        {
            temp+= radii[i] + ",\t";
            // Wrap around neatly
            if(i%5 == wrapNum && !(i == xValues.length - 1))
            {
                temp += "\n   ";
            }
        }

        // Trim off the extra comma
        temp = temp.substring(0, temp.length() - 2);
        temp += "$\n#End of BODY Parameters\n\n";
        view.setOutputData(temp);
        return temp;
    }

    /**
     * Clears the data from the BodyModel Table and nulls the old values in the model.
     */
    public void clearTable() {
        model.setRadii(null);
        model.setxValues(null);
        view.getjBodyTable().removeAll();
    }

    public BodyModel getModel() {
        return model;
    }

    public BodyView getView() {
        return view;
    }

    @Override
    public void refreshFromSaved(String data) {
        String section = util.xmlParse(xmlTag, data);
        if(section.isEmpty())
        {
            return;
        }
        String radii = util.xmlParse("RADII", section);
        String xValues = util.xmlParse("XVALUES", section);

        radii = radii.replaceAll(" ", "");
        String [] radiiValues = radii.split(",");
        String [] XValues = xValues.split(",");
        for(int x = 0; x < XValues.length; x++)
        {
            view.getjBodyTable().setValueAt(Double.parseDouble(XValues[x]), x, 0);
            view.getjBodyTable().setValueAt(Double.parseDouble(radiiValues[x]), x, 1);
        }
        refresh();
    }

    @Override
    public String generateXML()
    {
        // Init temps
        String temp = "";
        String array = "";
        double [] radii = model.getRadii();
        double [] xValues = model.getxValues();

        if(radii.length == 0 || xValues.length == 0)
        {
            return "";
        }

        // Start of xml formatting
        temp += "<" + xmlTag + ">\n";

        // Fill in temp with values then parse
        for(int i = 0; i < xValues.length; i++)
        {
            array += xValues[i] + ", ";
        }
        
        // Get rid of extra comma
        array = array.substring(0, array.length() - 2);
        temp += util.xmlWrite("XVALUES", array);
        array = "";
        // Fill in temp with values then parse
        for(int i = 0; i < xValues.length; i++)
        {
            array += radii[i] + ", ";
        }

        // Get rid of extra comma
        array = array.substring(0, array.length() - 2);
        temp += util.xmlWrite("RADII", array);
        temp += "</" + xmlTag + ">\n";
        return temp;
    }

    /**
  * Takes the data from the BODY table, processes it for the correct
  * format, and then sets the table to the corrected and sorted data. It also returns
  * an 2d array containg the x values and the radii, respectively.
  * NOTE: This function is really thrown together and could use some optimization.
  * @param target The BODY data table
  * @return 2D array, array[0][i] is the x values, array[1][i] is the radii values
  */
 public double[][] processBodyTable(JTable target)
 {
     //TODO: Make processBodyTable run faster
     String temp = null;
     double [][] data= new double[2][20];
     double [] count  = new double [2];

     for(int c = 0; c < 2; c++) // Column... C++ hahahaha
     {
         for(int r = 0; r < 20; r++ ) // Row
         {
            temp = String.valueOf(target.getValueAt(r, c));
            // Clear the table value to get rid of any input errors
            target.setValueAt(null, r, c);

            // Prepare for strange Java behavior:
            // The JTable sets the value in temp to "null" and not actually the null
            // value if a box is left empty so the == null & isEmpty methods
            // do not work. So that leads to the strange syntax below.
            if(temp.equals("null"))
            {
                r = 20;
            }
            else
            {
                count[c]++;
                data[c][r] = Double.parseDouble((String.valueOf(temp)));
                temp = null;
            }
         }
     }

     // Check for radii values w/o associated x values and vis-a-vis
     if(count[0] > count[1])
     {
         count[0] = count[1];
     }

     // The data at this point can have null values still, remove them
     double [][] trimmedData;
     trimmedData = new double[2][(int)count[0]];

     // Fill the correctly-sized array in and set the values back to the table
     // & abs the radii cause they cant be negative.
     for(int i = 0; i < count[0]; i++)
     {
       target.setValueAt(data[0][i], i, 0);
       target.setValueAt(Math.abs(data[1][i]), i, 1);
       trimmedData[0][i] = data[0][i];
       trimmedData[1][i] = Math.abs(data[1][i]);
     }

     // and then (bubble) sort....
     for(int i = 0; i < count[0]; i++)
     {
         for(int j = i; j < count[0]; j++)
         {
             if(trimmedData[0][j] < trimmedData[0][i] )
             {
                 // recycling data as a temp variable
                 data[0][0] = trimmedData[0][i];
                 data[1][0] = trimmedData[1][i];
                 trimmedData[0][i] = trimmedData[0][j];
                 trimmedData[1][i] = trimmedData[1][j];
                 trimmedData[0][j] = data[0][0];
                 trimmedData[1][j] = data[1][0];
             }
         }
     }

     // Fill the table back in
     for(int i = 0; i < count[0]; i++)
     {
       target.setValueAt(trimmedData[0][i], i, 0);
       target.setValueAt(trimmedData[1][i], i, 1);
     }

     return trimmedData;
 }

    private double calculateSurfaceArea()
    {
        return 0.0;
    }
    private double calculateZValuesD()
    {
        return 0.0;
    }
}
