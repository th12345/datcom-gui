/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import Services.StreamService;
import java.util.HashMap;

/**
 *
 * @author -B-
 */
public class DataServer {
    static DataServer self;
    HashMap<String, OAE_LinkInterface> data;

    private DataServer()
    {
        data = new HashMap<String, OAE_LinkInterface>(123, 0.75f);
    }

    public static DataServer getInstance()
    {
        if(self == null)
        {
            self = new DataServer();
        }
        return self;
    }

    public void addLink(OAE_LinkInterface target)
    {
       if(data.containsKey(target.getName()))
       {
           StreamService.printToStream("--Hash Collision: " + target.getName(), "err");
           return;
       }
       StreamService.print("Data Hashed: " + target.getName());
       data.put(target.getName(), target);
    }

    public OAE_LinkInterface getLink(String name)
    {
        if(data.containsKey(name))
        {

            return data.get(name);
        }
        StreamService.printToStream("--Link Does Not Exist: " + name, "err");
        return null;
    }

    public HashMap<String, OAE_LinkInterface> getMap()
    {
        return data;
    }
}
