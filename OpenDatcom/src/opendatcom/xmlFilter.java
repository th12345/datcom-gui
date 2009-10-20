/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Bullshit class required for filtering the files that the filechooser lets the
 * user see. Yeah, waste of space.
 * @author -B-
 */
public class xmlFilter extends FileFilter {

    @Override
    public boolean accept(File pathname) {
       return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(".xml");
    }

    @Override
    public String getDescription() {
        return ".xml";
    }
}
