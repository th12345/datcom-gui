package opendatcom;

/**
 * Controller method for the BodyModel model and the BodyModel View.
 * @author -B-
 */
public class BodyController implements AbstractController {

    // Utilities
    ParserUtility util = new ParserUtility();

    // References
    BodyView view;
    BodyModel model;

    /**
     * Standard constructor, set the model, view, controller references
     * @param view
     */
    public BodyController() {
        this.view = new BodyView(this);
        this.model = BodyModel.getInstance();
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
        temp += "NX=\t" + xValues.length + "\n";
        temp += "X(1)=\t";
        for(int i = 0; i < xValues.length; i++)
        {
            temp += xValues[i] + ", ";
            // Wrap around neatly
            if(i%4 == 0)
            {
                temp += "\n  ";
            }
        }
        temp += "\n" + "R(1)=\t";
        for(int i = 0; i < xValues.length; i++)
        {
            temp+= radii[i] + ", ";
            // Wrap around neatly
            if(i%4 == 0)
            {
                temp += "\n  ";
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
}
