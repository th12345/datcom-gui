/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Abstracts.AbstractService;
import opendatcom.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author -B-
 */
public class ProjectService extends AbstractService
{
    static ProjectService self;
    ImportExportService ies;
    String projectName;
    String projectPath;
    File workingFolder;
    File projectFile;
    boolean isValid = false;

    private ProjectService()
    {
        registerForMe();
        ies = ImportExportService.getInstance();
        workingFolder = new File(System.getProperty("user.dir"));
        projectFile = new File(System.getProperty("user.dir"));
        projectPath = System.getProperty("user.dir");
        projectName = null;
    }

    public static ProjectService getInstance()
    {
       if(self == null)
       {
           self = new ProjectService();
       }
       return self;
    }

    public void startProject()
    {
        projectName = javax.swing.JOptionPane.showInputDialog("Project Name:");
        startProject(projectName);
    }

    public void startProject(String text) {
        try {
            if (text == null)
            {
                return;
            }
            projectName = text;
            projectPath = System.getProperty("user.dir") + "\\Projects\\" + projectName;
            System.out.println("Path: " + projectPath);
            projectFile = new File(projectPath + "\\" + projectName + ".xml");
            new File(projectPath).mkdirs();
            projectFile.createNewFile();
            System.out.println("Project File:" + projectFile.getName());
            projectFile.mkdirs();
            isValid = true;
        } catch (IOException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveProject()
    {

    }

    //////////////////////////////////////////////////

    public File getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(File projectFile) {
        this.projectFile = projectFile;
    }

    public File getWorkingFolder() {
        return workingFolder;
    }

    public void setWorkingFolder(File workingFolder) {
        this.workingFolder = workingFolder;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public Boolean isValid()
    {
        return isValid;
    }

}
