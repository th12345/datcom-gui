package Services;

import Core.OAE_LinkInterface;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class containing various utility functions ranging from data-specific XML parxing
 * to Datcom-specific field formatting and input validation. Statically implemented.
 * @author -B-
 */
public class FormatUtility {

    static FormatUtility self;
    static int wrapNum = 5;
    public static FormatUtility getInstance()
    {
        if(self == null)
        {
            self = new FormatUtility();
        }
        return self;
    }

    private FormatUtility()
    {
    }

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
         StreamService.printToStream("Parsing of JTextField failed, String data: " + target.getText(), "err");
         target.setText("Error, invalid value");
         return -1;
     }

     target.setText(String.valueOf(value));
     return value;
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
     String temp = validate(target.getText());
     target.setText(temp.replaceAll("\t", " "));
     return temp;
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

    public String xmlParse(String targetElement, String data)
    {
        String temp = "";
        String startXml = "<" + targetElement + ">";
        String endXml = "</" + targetElement + ">";
        int start = data.indexOf(startXml);
        int end = data.indexOf(endXml);

        if(start == -1)
        {
            StreamService.print(targetElement + " not found in save file");
            return "";
        }
        else if(end == -1)
        {
            StreamService.print("Found non-terminated XML tag");
            return "";
        }

        temp = data.substring(start, end);
        temp = temp.replaceAll(startXml, "");
        temp = temp.replaceAll(endXml, "");
        return temp;
    }

    /**
     * Formats and returns a string containing the correctly formatted xml data
     * using the given tag and data. If the data value is blank, NaN, or null the
     * function returns an empty string "".
     * @param elementTag The xml tag, without < >
     * @param data The element data
     * @return A string containing the properly-formatted xml element.
     */
    public String xmlWrite(String elementTag, String data)
    {
        if(data.isEmpty())
        {
            return "";
        }
        String startXML = "<" + elementTag + ">";
        String endXML = "</" + elementTag + ">";
        return startXML + data + endXML + "\n";
    }

    /**
     * Formats and returns a string containing the correctly formatted xml data
     * using the given tag and data. If the data value is blank, NaN, or null the
     * function returns an empty string "".
     * @param elementTag The xml tag, without < >
     * @param data The element data
     * @return A string containing the properly-formatted xml element.
     */
    public String xmlWrite(String elementTag, double data)
    {
        if(Double.isNaN(data))
        {
            return "";
        }
        String startXML = "<" + elementTag + ">";
        String endXML = "</" + elementTag + ">";

        return startXML + data + endXML + "\n";
    }

    public String xmlWrite(String elementTag, List<?> data)
    {
        if(data == null || data.isEmpty())
        {
            return "";
        }
        String startXML = "<" + elementTag + ">";
        String endXML = "</" + elementTag + ">";
        String temp = "";
        for (Object object : data) {
            if(object != null && (!object.toString().equals("null")))
            {
                temp += String.valueOf(object) + ", ";
            }
        }
        if(temp.isEmpty())
        {
            return "";
        }
        temp = temp.substring(0, temp.length()-2);
        return startXML + temp + endXML + "\n";
    }

    public String xmlWrite(String elementTagS, OAE_LinkInterface target)
    {
        
        return "";
    }

     /**
     * All the safeFormat functions take the input data and format  it the following
     * way: <  Header \t Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and rejected if invalid. If valid, it is
     * appended to the end of the aggragateData string.
     * @param Header
     * @param Data
     */
    public String safeFormat(String Header, double Data)
    {
        String output = "";
        if(Double.isNaN(Data))
        {
            return output;
        }
        output += Header + "\t" +  Data + ",\n";
        return output;
    }

    /**
     * All the safeFormat functions take the input data and format  it the following
     * way: <  Header \t Data, \n >. The input data is checked for error conditions
     * (empty string or NaN double) and rejected if invalid. If valid, it is
     * appended to the end of the aggragateData string.
     * @param Header
     * @param Data
     */
    public String safeFormat(String Header, String Data)
    {
        String output = "";
        if(Data.isEmpty())
        {
            return output;
        }
        output += Header + "\t" +  Data + ",\n";
        return output;
    }

    /**
     * Generates the approprate datcom format for the given data type.
     * @param Header The header information to include ex "X(1)="
     * @param data The data to append after the header
     * @param length The length of the array/linked list
     * @return A string containing the datcom-formatted information.
     */
    public String datcomFormat(String Header, double data[], int length)
    {
        // Check to make sure the data isnt null & for bound exceptions
        if(data.length == 0 || data.length < length)
        {
            return "";
        }

        String temp = "";
        temp += Header + "\t";

        // Iterate through and appended the data
        for(int i = 0; i < length; i++)
        {
            temp += data[i] + ",\t";
            
            // Wrap to a new line after every wrapNum entries
            if(i%wrapNum == 0)
            {
                temp += "\n   ";
            }
        }
        temp += "\n";
        return temp;
    }

    public String datcomFormat(String Header, double data)
    {
        if(Double.isNaN(data))
        {
            return "";
        }
        return Header + String.valueOf(data) + ",\n";
    }

    public String datcomFormat(String Header, String data)
    {
        if(data.isEmpty())
        {
            return "";
        }
        return Header + String.valueOf(data) + ",\n";
    }

    public String datcomFormat(String Header, List<?> data, int length)
    {
        String temp = "";
        for(int i = 0; i < length; i++)
        {
            if(data.get(i) != null)
            {
                temp += String.valueOf(data.get(i)) + ",";
            }
            if((i % 3 == 2) &&(!temp.isEmpty()) && ((i + 1) < length))
            {
                temp += "\n  ";
            }
        }

        if(!temp.isEmpty())
        {
            temp = Header + temp;
            temp += "\n";
        }
        return temp;
    }

    public String datcomFormat(String Header, List<Double> data)
    {
        String temp = "";
        temp += Header + "\t";
        for(int i = 0; i < data.size(); i++)
        {
            temp += String.valueOf(data.get(i)) + ",\t";
            // Wrap to a new line after every wrapNum entries
            if(i%wrapNum == wrapNum)
            {
                temp += "\n   ";
            }
        }
        temp += "\n";
        return temp;
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
    if(input.isEmpty())
    {
        return "";
    }
    else if(input.contains("."))
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
public String processButtonTextArray(String input)
{
    // Split the input into arrays based on the comma
    String arrayHolder[] = input.split(",");
    input = "";

    // Iterate through and add decimals as necessary
    for(int i = 0; i < arrayHolder.length; i++)
    {
        if(arrayHolder[i].isEmpty())
        {
            // Do... nothing
        }
        else if(!arrayHolder[i].contains("."))
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
        input += ",\t" + arrayHolder[i];
    }
    // Get rid of the first comma, cause it just has to be done :)
    input = input.replaceFirst(",\t", "");
    return input;
}
}
