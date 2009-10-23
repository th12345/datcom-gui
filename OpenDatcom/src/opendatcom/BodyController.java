package opendatcom;

/**
 * Controller method for the BodyModel model and the BodyModel View.
 * @author -B-
 */
public class BodyController extends AbstractController {

    BodyModel model;
    BodyView view;
    
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
        double [][] temp = util.processBodyTable(view.getjBodyTable());
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
        temp += "$BODY\n";
        temp += " NX=\t" + xValues.length + "\n";
        temp += " X(1)=\t";
        for(int i = 0; i < xValues.length; i++)
        {
            temp += xValues[i] + ", ";
        }
        temp += "\n R(1)=\t";
        for(int i = 0; i < xValues.length; i++)
        {
            temp+= radii[i] + ", ";
            // Wrap around neatly
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
}
