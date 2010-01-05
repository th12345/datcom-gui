/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author -B-
 */
public class DatcomThread extends Thread{

    class timer extends Thread
    {
        Thread target;
        public timer(Thread t) {
            target = t;
        }
        
        @Override
        public void run() {
            try {
            // Wait 1 minute then kill the thread if its being naughty
            target.join(60000);
            if(target.isAlive())
            {
                target.interrupt();
                StreamService.print("Datcom Thread stalled, aborting and killing thread");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DatcomThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    @Override
    public void run()
    {
        new timer(this).start();
        runDatcom();
        moveForFiles();
    }

    private void runDatcom()
    {
        Process p = null;
        try {
            String path = System.getProperty("user.dir") + "\\Bin\\Datcom\\datcom.exe";
            // Execute the datcom and wait until it returns
            p = new ProcessBuilder(path).start();
            p.waitFor();
        } catch (InterruptedException ex) {
            byte [] out = new byte[256];
            try {
                p.getInputStream().read(out);
                StreamService.print(out);
                p.getErrorStream().read(out);
                StreamService.print(out);
            } catch (IOException ex1) {
                Logger.getLogger(DatcomThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(DatcomThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Moves the datcom-generated for00X files from the working space to the user's
     * project directory.
     */
    private void moveForFiles()
    {
        File moveForSource = null;
        File moveForDest = null;
        ProjectService ps = ProjectService.getInstance();

        // Loop through and move the files
        for(int i = 5; i < 15; i++)
        {
            // Set the file names correctly
            if(i < 10)
            {
                moveForSource = new File("for00" + i + ".dat");
                moveForDest = new File(ps.getProjectPath() + "\\"+ ps.getProjectName() + "_out" + i +".txt");
            }
            else
            {
                moveForSource = new File("for0" + i + ".dat");
                moveForDest = new File(ps.getProjectPath() + "\\"+ ps.getProjectName() + "_out" + i +".txt");
            }

            // Delete old files so the move can be executed
            if(moveForDest.exists())
            {
                moveForDest.delete();
            }

            // only move the
            if(moveForSource != null)
            {
                moveForSource.renameTo(moveForDest);
            }
        }
    }
}
