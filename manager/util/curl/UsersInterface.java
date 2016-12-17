package manager.util.curl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import manager.model.Users;

/**
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment 
 */
public class UsersInterface extends ServerInterface{
    public static final String USERS = "users";
    public static final String USER = "user";
    
    /**
     * Will return an XML 
     * {@code
     * <users>
     *      <user>
     *          <id>#</id>
     *          <name>NAME</name>
     *      </user>
     *      <user>
     *          <id>#</id>
     *          <name>NAME</name>
     *      </user>
     * </users>
     * }
     * @return
     * @throws IOException 
     */
    public String getUsers() throws IOException {
        return super.getResponse(super.BASE_URL + USERS);
    }
    
    /**
     * Takes the values and builds a query string to be passed to the api.
     * 
     * @param id
     * @param name
     * @return
     * @throws MalformedURLException
     * @throws IOException 
     */
    public String deleteUser(int id, String name) throws MalformedURLException, IOException{
        String[] qp = {"name="+name, "id="+Integer.toString(id)};
        return super.getResponse(super.BASE_URL + USER, qp, "DELETE");
    }
    
    /**
     * Will return an XML 
     * {@code
     * <users>
     *      <user>
     *          <id>#</id>
     *          <name>NAME</name>
     *      </user>
     * </users>
     * }
     * @param id
     * @return
     * @throws IOException 
     */
    public String getUser(int id) throws IOException{
        return super.getResponse(super.BASE_URL + USER + "/" + id);
    }

    /**
     * Takes the values and builds a query string to be passed to the api.
     * 
     * @param name
     * @return
     * @throws IOException 
     */
    public String createUser(String name) throws IOException {
        String[] qp = {"name=" + name};
        return super.getResponse(super.BASE_URL + USER, qp, "POST");
    }

    /**
     * Takes the values and builds a query string to be passed to the api.
     * 
     * @param user
     * @return
     * @throws ProtocolException
     * @throws IOException 
     */
    public String updateUser(Users user) throws ProtocolException, IOException {
        String[] qp = {"name=" + user.getName(), "id=" + user.getId()};
        return super.getResponse(super.BASE_URL + USER, qp, "PUT"); // I.E. Update
    }
}
