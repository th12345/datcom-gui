package opendatcom;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author aleksey
 */
public class runDatcom {

    String[] opensesame = null;
    String s = null;

    public runDatcom(){}

    public void executeDatcom(String[] opensesame){

        try {

	    // run the command
            Process p = Runtime.getRuntime().exec(opensesame);

            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));

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

        }catch (IOException e) {
            System.out.println("System error - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void executeDatcom(String opensesame){

        try {

	    // run the command
            Process p = Runtime.getRuntime().exec(opensesame);

            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));

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

        }catch (IOException e) {
            System.out.println("System Error - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}