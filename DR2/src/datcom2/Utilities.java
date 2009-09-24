/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datcom2;

/**
 * Class-like object used to store commonly used functions. I will probably
 * end up just making a single static object out of it but for now if needed
 * create an instance. If a function is needed but it isn't clear where it needs
 * to go, put it in here.
 * @author -B-
 */
public class Utilities {

/**
 * Function checks the input string for the decimal point required
 * by DATCOM, if it is missing it appends it to the end.
 * @param input
 * @return The input string + .0 if needed
 */
public String validateInput(String input)
{
    String temp;
    if(input.contains("."))
    {
        temp = input;
    }
    else
    {
        temp = input + ".0";
    }

    return temp;
}
}
