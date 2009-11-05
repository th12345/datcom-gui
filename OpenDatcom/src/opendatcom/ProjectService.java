/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opendatcom;

import java.io.File;

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

    private ProjectService()
    {
        registerForMe();
        ies = ImportExportService.getInstance();
        workingFolder = null;
        projectFile = null;
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
        if(projectName == null)
        {
            return;
        }

        projectPath = System.getProperty("user.dir") + "\\Projects\\" + projectName;
        System.out.println("Path: " + projectPath);
        projectFile = new File(projectPath);
        projectFile.mkdirs();
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


}
