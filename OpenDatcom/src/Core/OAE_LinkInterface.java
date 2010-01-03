/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 * Interface for all global values in OpenDatcom. It provides an interface for all
 * functionality expected of all global values. These include: value lookup, retrieval.
 * consistancy, and parsing. All Links are stored in the DataServer and their values
 * are exposed to all classes through the provided lookup functionality.
 * @see DataServer
 * @author -B-
 */
public interface OAE_LinkInterface extends OAE_Component{
    public Object getValue();
    public void link(Object v, Object m);
    public void load(String target);
    public String generateXML_Element();
    public void clear();

    // Datcom interfaces, leave blank if not needed
    public String datcomFormat(String offset);
}
