/* Aleksey Matyushev / Alan Teeder
 *
 * This program is the sole property of Aleksey
 * Matyushev and Alan Teeder. This program is designed to be an
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear /JSBSim.
 */
package dd2jsb;

import java.io.*;
import java.util.ArrayList;

public class dd2jsbUtils {

    private String textFromFile;
    private float f;
    final static double EPSILON = 0.0000001;

    public dd2jsbUtils() {
    }

    ;

    public String getText(String filePath) throws FileNotFoundException {
        try {
            FileInputStream file = new FileInputStream(filePath);
            byte[] b = new byte[file.available()];
            file.read(b);
            file.close();
            textFromFile = new String(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textFromFile;
    }
 
    public void writeFile(String path, String filename, String content) {
        BufferedWriter bufferedwriter = null;
        String seperator = System.getProperty("file.separator");
        PrintWriter printwriter = null;
        try {
            bufferedwriter = new BufferedWriter(
                    new FileWriter(path + seperator + filename));
            printwriter = new PrintWriter(bufferedwriter);
            printwriter.println(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            printwriter.close();
        }
    }

    public double GetDoublefromString(String S) throws Exception {
        try {
            f = Double.valueOf(S.trim()).floatValue();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return f;
    }

    /** Compare two doubles within a given epsilon */
    public boolean equals(double a, double b, double eps) {
        if (a == b) {
            return true;
        }
        // If the difference is less than epsilon, treat as equal.
        return Math.abs(a - b) < eps;
    }

    /** Compare two doubles, using default epsilon */
    public boolean equals(double a, double b) {
        if (a == b) {
            return true;
        }
        // If the difference is less than epsilon, treat as equal.
        return Math.abs(a - b) < EPSILON * Math.max(Math.abs(a), Math.abs(b));
    }

    public void addtable(ArrayList derivTable, String line, int i, int j) {
        String temp = line.substring(i, j);
        derivTable.add(temp);
    }
}
