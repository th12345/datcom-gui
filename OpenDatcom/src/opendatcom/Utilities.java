/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class-like object used to store commonly used functions. I will probably
 * end up just making a single static object out of it but for now if needed
 * create an instance. If a function is needed but it isn't clear where it needs
 * to go, put it in here.
 * @author -B-
 */
public class Utilities {


 public String processHeader(JTextArea target)
 {
     String temp = target.getText();
     temp.replaceAll("\n", "\n#");
     return temp;
 }
 /**
  * Takes the text from the target text field/Area, processes it for the correct
  * format, and then sets the text back to the corrected data. It also returns
  * the corrected string. This function returns SINGLE values.
  * @param target
  * @return The corrected string
  */
 public String processTextField(JTextField target)
 {
     String [] targetText = null;
     String temp = target.getText();
     if(temp.contains(","))
     {
        targetText = temp.split(",");
        target.setText(validateInput(targetText[0]));
     }
     else
     {
         target.setText(validateInput(temp));
     }
     
     return target.getText();
 }
 
 /**
  * Takes the text from the target text field/Area, processes it for the correct
  * format, and then sets the text back to the corrected data. It also returns
  * the corrected string. This function returns MULTIPLE values.
  * @param target
  * @return The corrected string
  */
 public String processTextField(JTextArea target)
 {
     target.setText(validateInput(target.getText()));
     return target.getText();
 }
/**
 * Function checks the input string for the decimal point required
 * by DATCOM, if it is missing it appends it to the end.
 * @param input
 * @return The input string + .0 if needed
 */
public String validateInput(String input)
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
 * @param input
 * @return the input string checked for decimals
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
