/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.File;
import java.util.LinkedList;
import javax.swing.JApplet;
import javax.swing.JPanel;

/**
 *
 * @author -B-
 */
public class FileViewerController extends AbstractController{

    FileViewer view;
    FileViewerModel model;
    ProjectService ps;
    File [] files;
    File root;
    ImportExportService ies;

    public FileViewerController()
    {
        view = new FileViewer(this);
        ies = ImportExportService.getInstance();
        ps = ProjectService.getInstance();
        this.name = "File Viewer";
        populateFileSelect();
        registerForMe();
    }

    /**
     * Populates the combo box with all the files that are in the user's working
     * directory.
     */
    public void populateFileSelect()
    {
        if(!ps.isValid)
        {
            return;
        }
        files = null;
        view.getjFileSelect().removeAllItems();
        root = new File(ps.getProjectPath());

        if (root == null)
        {
            return;
        }
        
        files = root.listFiles();
        for(int i = 0; i < files.length; i++)
        {
            view.getjFileSelect().addItem(files[i].getName());
        }
    }

    /**
     * Sets the output window's text to the currently selected item.
     */
    public void changeOutputView()
    {
        if(files == null)
        {
            System.out.println("No Files to Select");
            return;
        }
        File tempF = files[view.getjFileSelect().getSelectedIndex()];
        String tS = ies.importFile(tempF);
        view.getjShow().setText(tS);
        view.getjShow().setCaretPosition(0);
    }

    @Override
    public JPanel getView() {
        return view;
    }
}
