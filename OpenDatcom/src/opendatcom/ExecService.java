/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author -B-
 */
public class ExecService extends AbstractService
{
    enum Stream_Type
    {
        INPUT,
        OUTPUT,
        ERROR
    }
    
    static ExecService self;
    String workingDirectory = "";
    LinkedList<Process> childrenProcesses;
    LinkedList<String> children;
    BufferedReader stdin;
    BufferedWriter stdout;

    private ExecService()
    {
        parent = OpenDatcomController.getInstance();
        workingDirectory = parent.getWorkingDirectory().getAbsolutePath();

        // Remove the . to get the true working directory
        workingDirectory = workingDirectory.substring(0, workingDirectory.length() - 1) + "\\Bin";

        childrenProcesses = new LinkedList<Process>();
    }

    public static ExecService getInstance()
    {
        if(self == null)
        {
            self = new ExecService();
        }
        return self;
    }

    /**
     * Runs the given executable
     * @param execName
     */
    public void execute(String exec, String name)
    {
        try {
            Process temp = Runtime.getRuntime().exec(exec);
            childrenProcesses.add(temp);
            children.add(name);
        } catch (IOException ex) {
            Logger.getLogger(ExecService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //TODO: Fix this function
    /**
     * ********************** THIS FUNCTION IS BROKEN ****************************
     * Returns the requested processes' stream. The type of stream returned is the
     * specified by the type enum.
     *
     * Enum Values:
     *   INPUT  - Returns the input stream.
     *   OUTPUT - Returns the output stream.
     *   ERROR  - Returns the error stream.
     *
     * Note, This function returns a generic object, you must cast it to the given
     * type. For example, if an inputStream was requested, the output must be cast
     * it with: (InputStream)output = getProcessIOEStream("Example", INPUT);
     * @param processName The name of the target process.
     * @param type The type of stream requested, see above.
     * @return Stream down-casted to an object class, see above.
     */
    public Object getProcessIOEStream(String processName, Stream_Type type)
    {
        Process target = null;
        for(int i = 0; i < children.size(); i++)
        {
            if(children.get(i).equalsIgnoreCase(processName))
            {
                target = childrenProcesses.get(i);
                break;
            }
        }

        if(target == null)
        {
            return null;
        }

        switch(type)
        {
            case INPUT:
                return target.getInputStream();
            case OUTPUT:
                return target.getOutputStream();
            case ERROR:
                return target.getErrorStream();
        }
        return null;
    }
}
