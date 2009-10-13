/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OpenDatcom;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class that is really more of a set of like functions. The main purpose of the
 * class is parsing data from the user and making sure that input will not break
 * the program or the DATCOM. All inputs come through their respected function and
 * must eventually be run through the validate() method where applicable.
 * @author -B-
 */
public class ParserUtilities {

  /**
   * Takes the text from the target text field/Area, processes it for the correct
   *format, and then sets the text back to the corrected data. It returns a single,
   * parsed double from the text. If there is more then one number, or garbage
   * then it returns the first valid number. This function returns a SINGLE value.
   * @param target The input JTextField
   * @return The parsed double from the text, or Double.NaN if error
   */
 public double processDataField(JTextField target)
 {
    
     boolean verboseErrors = true;
     double value = 0;
     String temp = target.getText();

     // Catch empty & ignore empty fields.
     if(temp.equalsIgnoreCase(""))
     {
         return Double.NaN;
     }
     // Already parsed it, it errored, and still hasn't been changed...
     else if(temp.equalsIgnoreCase("Error, invalid value"))
     {
         return Double.NaN;
     }

     // Get rid of those silly multi-input strings.
     if(temp.contains(","))
     {
         // We just want the first number, toss the rest away
         temp = temp.split(",")[0]; 
     }

     // get rid of junk
     temp = temp.replaceAll("\t", "");
     temp = temp.replaceAll(" ", "");

     // Attempt to parse the information, if not throw/catch to make it behave
     try{
        value = Double.parseDouble(temp);
     }
     catch (IllegalArgumentException e)
     {
         // Error stuff
         System.out.println("Parsing of JTextField failed, String data: " + target.getText());
         if(verboseErrors)
         {
             System.out.println("Details:\n" + e.toString());
         }
         target.setText("Error, invalid value");
         return -1;
     }

     target.setText(String.valueOf(value));
     return value;
 }

 /**
  * Processes a comment header, breaks into multiple lines if needed.
  * @param target The target JTextArea
  * @return The formatted header
  */
 public String processHeader(JTextArea target)
 {
     //TODO: Implement processHeader
     return "";
 }
 /**
  * Takes the text from the target text field/Area, processes it for the correct
  * format, and then sets the text back to the corrected data. It also returns
  * the corrected string. This function return a SINGLE value.
  * @param target The target JTextField
  * @return The corrected string
  */
 public String processTextField(JTextField target)
 {
     String [] targetText = null;
     String temp = target.getText();
     if(temp.contains(","))
     {
        targetText = temp.split(",");
        target.setText(validate(targetText[0]));
     }
     else
     {
         target.setText(validate(temp));
     }
     
     return target.getText();
 }
 
 /**
  * Takes the text from the target text field/Area, processes it for the correct
  * format, and then sets the text back to the corrected data. It also returns
  * the corrected string. This function returns MULTIPLE values.
  * @param target The target JTextArea
  * @return The corrected string
  */
 public String processTextField(JTextArea target)
 {
     target.setText(validate(target.getText()));
     return target.getText();
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
            System.out.println(temp);

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
                System.out.println("Fires");
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

/**
 * Function checks the input string for the decimal point required
 * by DATCOM, if it is missing it appends it to the end.
 * @param input The input string
 * @return The correctly formatted input string
 */
public String validate(String input)
{
    String temp;
    // Check for empty fields
    if(input.isEmpty())
    {
        return "";
    }

    // Remove junk characters
    input = input.replaceAll(" ", "");
    input = input.replaceAll("\t", "");

    // Check for comma delimited arrays
    if(input.contains(","))
    {
        return processButtonTextArray(input);
    }

    // Not an array then check/add decimal
    if(input.contains("."))
    {
        // Check for leading zeros
        if(input.toCharArray()[0] == '.')
        {
            input = "0" + input;
        }
        temp = input;
    }
    else
    {
        temp = input + ".0";
    }
    return temp;
}

/**
 * Process arrays of strings. Adds the decimal if needed and
 * reassembles the data
 * @param input The input string
 * @return The input string checked and corrected for decimals
 */
private String processButtonTextArray(String input)
{
    // Split the input into arrays based on the comma
    String arrayHolder[] = input.split(",");
    input = "";

    // Iterate through and add decimals as necessary
    for(int i = 0; i < arrayHolder.length; i++)
    {
        if(!arrayHolder[i].contains("."))
        {
            arrayHolder[i] += ".0";
        }
        else
        {
            // Check for leading zeros
            if(arrayHolder[i].toCharArray()[0] == '.')
            {
                arrayHolder[i] = "0" + arrayHolder[i];
            }
        }
        // Rebuild the original input
        input += ", " + arrayHolder[i];
    }
    // Get rid of the first comma, cause it just has to be done :)
    input = input.replaceFirst(", ", "");
    return input;
}

/**
 * Removes all comment lines from a block of text.
 * @param input The input string
 * @return The input string - any comment lines
 */
public String removeComments(String input)
{
    String temp[];
    String output = "";
    temp = input.split("\\n");
    int trimPoint = -1;
    for(int i = 0; i < temp.length; i++)
    {
        if(temp[i].contains("#"))
        {
            // eventually find the point the comment starts at
            trimPoint = temp[i].indexOf('#');
            temp[i] = temp[i].substring(0, trimPoint);
        }
        else
        {
            // add back to the output string, !!! MAKE SURE TO ADD THE \n BACK
            // IN !!! The split command removes it.
            output += temp[i] + "\\n";
        }
    }
    return output;
}
}
