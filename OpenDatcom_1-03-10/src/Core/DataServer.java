package Core;

import Services.StreamService;
import java.util.HashMap;

/**
 * Static global-data server. It stores all the links created in the various classes
 * in OpenDatcom and provides safe access to all data, from any class without requiring
 * knowledge of the object itself or its parent class.
 *
 * It is implemented as a hashmap based on the <K,V> model where K (the key) is used
 * as a string and V (the value) is an object implementing OAE_LinkInterface. The
 * server provides golbal value lookup through the OAE_Interface objects name string.
 *
 * It is EXTREMELY important that every value added to the server has a unique
 * name, the hashTable has undefined behavior when two keys share the same value,
 * and it WILL break and/or corrupt.
 * @author -B-
 */
public class DataServer {
    static DataServer self;
    static HashMap<String, OAE_LinkInterface> data;

    private DataServer()
    {
        data = new HashMap<String, OAE_LinkInterface>(200, 0.75f);
    }

    /**
     * Standard reference method. Instantiates the class if it is null and returns
     * a reference to the dataServer.
     * @return A reference to the server.
     */
    public static DataServer getInstance()
    {
        if(self == null)
        {
            self = new DataServer();
        }
        return self;
    }

    /**
     * Adds an OAE_LinkInterface object to the database according to it's name.
     * Reports has collisions to the error stream.
     * @param target The link to add, must have a unique name.
     */
    public static void addLink(OAE_LinkInterface target)
    {
       if(data.containsKey(target.getName()))
       {
           StreamService.printToStream("--Hash Collision: " + target.getName(), "err");
           return;
       }
       StreamService.print("Data Hashed: " + target.getName());
       data.put(target.getName(), target);
    }

    /**
     * Retrieves the OAE_LinkInterface value with the given name. If the link is
     * not found, it returns a null reference and reports the missing link to the
     * err stream.
     * @param name The lookup key for the hashmap, The link's name.
     * @return The link with the given name, or null if not found.
     */
    public static OAE_LinkInterface getLink(String name)
    {
        if(data.containsKey(name))
        {

            return data.get(name);
        }
        StreamService.printToStream("--Link Does Not Exist: " + name, "err");
        return null;
    }

    /**
     * Gets a reference to the actual hashMap.
     * @return A reference to the hashMap that contains all the data in OpenDatcom.W
     */
    public static HashMap<String, OAE_LinkInterface> getMap()
    {
        return data;
    }
}
