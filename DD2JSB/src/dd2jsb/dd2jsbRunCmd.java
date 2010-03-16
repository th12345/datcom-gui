/* Aleksey Matyushev / Alan Teeder
 *
 * This program is the sole property of Aleksey
 * Matyushev and Alan Teeder. This program is designed to be an
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear /JSBSim.
 */
package dd2jsb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aleksey
 */
public class dd2jsbRunCmd {

    String[] opensesame = null;
    String s = null;

    public dd2jsbRunCmd() {
    }

    public void executeCmd(String[] opensesame) {

        try {

            // run the Unix command
            Process p = Runtime.getRuntime().exec(opensesame);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command

            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command

            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            //System.exit(0);

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void executeCmd(String opensesame) {

        try {

            // run the Unix command
            Process p = Runtime.getRuntime().exec(opensesame);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command

            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command

            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            //System.exit(0);

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
