package manager.util.curl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import manager.model.Chores;

/**
 * Chores Interface is the primary class to interact with a restful api. 
 * Obtain the XML values and build a list; To take an object and create a valid
 * query string to save values
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment
 */
public class ChoresInterface extends ServerInterface {

    public static final String CHORE = "chore";
    public static final String CHORES = "chores";

    /**
     * Takes the values and builds a query string to be passed to the api.
     * 
     * @param chore
     * @return
     * @throws IOException 
     */
    public String createChore(Chores chore) throws IOException {
        ArrayList<String> _qs = new ArrayList<>();
        _qs.add("name=" + chore.getName());
        _qs.add("description=" + chore.getDescription());
        _qs.add("duedate=" + chore.getDueDate());
        _qs.add("completed=" + chore.isCompleted());
        _qs.add("userId=" + chore.getUserId());
        return super.getResponse(super.BASE_URL + CHORE, _qs.toArray(new String[_qs.size()]), "POST");
    }

    /**
     * Will return an XML
     * {@code
     * <chores>
     *      <chore>
     *          <id>#</id>
     *          <name>NAME</name>
     *          <description>[DESCRIPTIONS]</description>
     *          <duedate>9999-99-99</duedate>
     *          <completed>0</completed>
     *      </chore>
     *      <chore>
     *          <id>#</id>
     *          <name>NAME</name>
     *          <description>[DESCRIPTIONS]</description>
     *          <duedate>9999-99-99</duedate>
     *          <completed>0</completed>
     *      </chore>
     * </chores>
     * }
     * @return
     * @throws IOException 
     */
    public String getChores() throws IOException {
        return super.getResponse(super.BASE_URL +  CHORES);
    }

    /**
     * Will return an XML 
     * {@code
     * <chores>
     *      <chore>
     *          <id>#</id>
     *          <name>NAME</name>
     *          <description>[DESCRIPTIONS]</description>
     *          <duedate>9999-99-99</duedate>
     *          <completed>0</completed>
     *      </chore>
     * </chores>
     * }
     * @param id
     * @return
     * @throws IOException 
     */
    public String getChore(int id) throws IOException {
        return super.getResponse(super.BASE_URL + CHORE + "/" + id);
    }

    /**
     * Takes the values and builds a query string to be passed to the api. 
     *
     * @param chore
     * @return
     * @throws IOException 
     */
    public String updateChore(Chores chore) throws IOException {
        ArrayList<String> _qs = new ArrayList<>();
        _qs.add("id=" + chore.getId());
        _qs.add("name=" + chore.getName());
        _qs.add("description=" + chore.getDescription());
        _qs.add("duedate=" + chore.getDueDate());
        _qs.add("completed=" + chore.isCompleted());
        _qs.add("userId=" + chore.getUserId());
        return super.getResponse(super.BASE_URL + CHORE, _qs.toArray(new String[_qs.size()]), "PUT");
    }

    /**
     * Takes the values and builds a query string to be passed to the api. 
     * 
     * @param id
     * @return
     * @throws MalformedURLException
     * @throws IOException 
     */
    public String deleteChore(int id) throws MalformedURLException, IOException {
        String[] _tmp = {"id="+Integer.toString(id)};
        return super.getResponse(super.BASE_URL + CHORE, _tmp, "DELETE");
    }
}
