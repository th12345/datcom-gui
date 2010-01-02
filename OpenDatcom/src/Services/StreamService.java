/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class manages the applications streams. It offers functionality to create,
 * modify, and delete filestreams. The the manager is implemented statically so
 * that it can be accessed without prior declaration/instantiation/etc..
 * @author -B-
 */
public class StreamService {

    /**
     * Helper class that wraps standard PrintStream and adds more
     * control functionality.
     */
    private class OAE_Stream
    {
        String name;
        PrintStream stream;
        boolean isEnabled;

        public OAE_Stream(String name, PrintStream stream)
        {
            this.name       = name;
            this.stream     = stream;
            this.isEnabled  = true;
        }

        /**
         * Prints the text to the stream.
         * @param text
         */
        public void print(String text)
        {
            // Make sure the stream is set as enabled, then print
            if(isEnabled)
            {
                stream.println(text);
            }
        }

        /**
         * Enables or disables the stream.
         * @param isEnabled
         */
        public void setEnabled(boolean isEnabled)
        {
            this.isEnabled = isEnabled;
        }

        /**
         * Overrided version of the default equals method, it compares the object
         * vs the name of the string.
         * @param target The object to compare
         * @return True if the object equals the stream's name
         */
        @Override
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        public boolean equals(Object target)
        {
            return (this.name.equalsIgnoreCase(target.toString()));
        }

        /**
         * Overriden hash code method, the hash code is computed using the stream's
         * name.
         * @return The hash code computed with thes stream name
         */
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 73 * hash + (this.name != null ? this.name.hashCode() : 0);
            return hash;
        }

        /**
         * Overrided version of the default toString that returns the name of the
         * stream only.
         * @return
         */
        @Override
        public String toString()
        {
            return name;
        }
    }

    static StreamService self;
    static ArrayList<OAE_Stream> oStreams;
    static OAE_Stream std;

    /**
     * Private constructor, initializes the manager and stream arrays. It also
     * creates the default and error streams.
     */
    private StreamService()
    {
        oStreams = new ArrayList<OAE_Stream>();
        try {
            std = new OAE_Stream("std", System.out);
            oStreams.add(std);
            oStreams.add(new OAE_Stream("err", new PrintStream("err.txt")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StreamService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Static implementation of the class, returns the instance of the manager and
     * initializes everything if it has not already been instanced.
     * @return An reference to the Static streamService.
     */
    public static StreamService GetInstance()
    {
        if(self == null)
        {
            self = new StreamService();
        }
        return self;
    }

    /**
     * Prints the text to the std stream for the application.
     * @param string
     */
    public static void print(String string) {
        std.print(string);
    }

    /**
     * Prints the passed text to the streams indicated.
     * @param text The text to print.
     * @param streams Name of the stream(s) to print to.
     */
    public static void printToStream(String text, String...streams)
    {
        // Iterate through all the created streams and print to the ones that are
        // desired.
        for (String string : streams) {
            for (Iterator<OAE_Stream> it = oStreams.iterator(); it.hasNext();) {
                StreamService.OAE_Stream oAE_Stream = it.next();
                if(oAE_Stream.toString().equals(string))
                {
                    oAE_Stream.print(text);
                }
            }
        }
    }
}
