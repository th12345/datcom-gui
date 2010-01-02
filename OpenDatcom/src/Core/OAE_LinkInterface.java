/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

/**
 *
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
