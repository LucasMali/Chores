package manager.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.util.converter.XML;
import manager.util.curl.ChoresInterface;
import manager.util.curl.UsersInterface;
import manager.view.ViewController;

/**
 * The manager is the primary component to the application. It drives all the
 * essential logic to bridge the gap between the controllers.
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment
 */
public class Manager {

    /**
     * Observer lists allow FXML to load the contents of the lists into the
     * assigned FXML list type, in this case, tableview.
     */
    private ObservableList<Users> userList = FXCollections.observableArrayList();
    private ObservableList<Chores> choreList = FXCollections.observableArrayList();
    private ObservableList<UsersChores> usersChoreList = FXCollections.observableArrayList();

    /**
     * The APIs to be used, if need be, at any given time.
     */
    private UsersInterface usersApi;
    private ChoresInterface choresApi;

    /**
     * Holds the current user that is being edited
     */
    private Users user;

    /**
     * Holds the current chore that is being edited
     */
    private Chores chore;

    /**
     * Contains the index location of the selected item. TODO see if this is
     * deprecated.
     */
    private int index;

    /**
     * Container element for XML API results
     */
    private static final String XML_USER = "user";
    private static final String XML_CHORES = "chore";

    /**
     * Contains the current stage that is rendered.
     */
    private Stage stage;

    /**
     * Initializes the components for the application.
     *
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static Manager init() throws IOException, Exception {
        Manager _man = new Manager();
        _man.loadUsers();
        _man.loadChores();
        _man.loadUsersChores();
        return _man;
    }

    /**
     * Setting the apis to be used at will.
     */
    public Manager() {
        // Load the users.
        this.usersApi = new UsersInterface();
        // Load the chores
        this.choresApi = new ChoresInterface();
    }

    /**
     * Here the load users method will obtain all the users list from the api in
     * XML format. From there, using the XML builder it will transfer the
     * contents into Object Entities.
     *
     * @throws IOException
     * @throws Exception
     */
    public void loadUsers() throws IOException, Exception {
        // Make the call
        String xmlResults = this.usersApi.getUsers();

        // Convert the XML string into a Document object
        ArrayList users = XML.init().toList(xmlResults, XML_USER);

        // Build the list of users
        users.forEach(
                (u) -> {
                    this.userList.add(new Users((HashMap) u));
                }
        );

    }

    /**
     * Here the load chores method will obtain all the users list from the api
     * in XML format. From there, using the XML builder it will transfer the
     * contents into Object Entities.
     *
     * @throws IOException
     * @throws Exception
     */
    public void loadChores() throws IOException, Exception {
        // Make the call
        String xmlResults = this.choresApi.getChores();

        // Convert the XML string into a Document object
        ArrayList chores = XML.init().toList(xmlResults, XML_CHORES);

        // Build the list of chores
        chores.forEach(
                (c) -> {
                    choreList.add(new Chores((HashMap) c));
                }
        );
    }

    /**
     * The setup is to take the loaded users and the chores combining them to
     * display an X number of upcoming chores and their assigned users.
     */
    public void loadUsersChores() {
        this.usersChoreList.clear();
        // combine the users and chores.
        this.choreList.forEach((c) -> {
            UsersChores uc = new UsersChores();
            uc.setName(c.getName());
            uc.setDescription(c.getDescription());
            uc.setDueDate(c.getDueDate());
            uc.setCompleted(c.isCompleted());

            this.userList.forEach((Users u) -> {
                if (u.getId() == c.getUserId()) {
                    uc.setAssignee(u.getName());
                }
            });

            this.usersChoreList.add(uc);
        });
    }

    /**
     * Under this method, the user will be attempted to be created by the app
     * interface using the API. It will then look for an XML result to plug in
     * the new user into the list and display.
     *
     * @param name
     * @throws IOException
     * @throws Exception
     */
    public void createUser(String name) throws IOException, Exception {
        String xmlResults = this.usersApi.createUser(name);
        Map u = XML.init().toMap(xmlResults, XML_USER);
        this.userList.add(new Users((HashMap) u)); // adding to obsever
    }

    /**
     * Under this method, the user will be attempted to be update by the app
     * interface using the API. It will then change the value(s) of the entity
     * that resides assigned to the observer list and then refreshed.
     *
     * @param name
     * @throws IOException
     * @throws Exception
     */
    public void updateUser(String name) throws IOException, Exception {
        this.user.setName(name);
        this.usersApi.updateUser(this.user);
        this.userList.set(this.index, user);
    }

    /**
     * Under this method, the chore will be attempted to be created by the app
     * interface using the API. It will then look for an XML result to plug in
     * the new user into the list and display.
     *
     * @param chore
     * @throws IOException
     * @throws Exception
     */
    public void createChore(Chores chore) throws Exception {
        String xmlResults = this.choresApi.createChore(chore);
        Map u = XML.init().toMap(xmlResults, XML_CHORES);
        this.choreList.add(new Chores((HashMap) u)); // adding to obsever    
    }

    /**
     * Under this method, the chore will be attempted to be update by the app
     * interface using the API. It will then change the value(s) of the entity
     * that resides assigned to the observer list and then refreshed.
     *
     * @param chore
     * @throws IOException
     * @throws Exception
     */
    public void updateChore(Chores chore) throws Exception {
        this.choresApi.updateChore(chore);
        this.choreList.set(this.index, chore);
    }

    /**
     * This will delete the given user utilizing the api, then removing it from
     * the viewable list.
     *
     * @param u
     * @throws IOException
     */
    public void delete(Users u) throws IOException {
        this.usersApi.deleteUser(u.getId(), u.getName());
        this.userList.remove(u);
    }

    /**
     * This will delete the given chore utilizing the api, then removing it from
     * the viewable list.
     *
     * @param c
     * @throws IOException
     */
    public void delete(Chores c) throws IOException {
        this.choresApi.deleteChore(c.getId());
        this.choreList.remove(c);
    }

    /**
     * This is the edit component that makes use of the create scene. Rather
     * than displaying a blank result, it will call the method, and load in the
     * values accordingly.
     *
     * @param u
     * @throws Exception
     */
    public void edit(Users u) throws Exception {
        this._loadStage("/manager/view/User.fxml", u);
    }

    /**
     * This is the edit component that makes use of the create scene. Rather
     * than displaying a blank result, it will call the method, and load in the
     * values accordingly.
     *
     * @param c
     * @throws Exception
     */
    public void edit(Chores c) throws Exception {
        this._loadStage("/manager/view/Chore.fxml", c);
    }

    /**
     * This will be invoked when the new user request is made.
     * 
     * @throws IOException 
     */
    public void loadUserMenu() throws IOException {
        this._loadStage("/manager/view/User.fxml");
    }
    
    /**
     * This will be invoked when the new chore request is made.
     * 
     * @throws IOException 
     */
    public void loadChoreMenu() throws IOException {
        this._loadStage("/manager/view/Chore.fxml");
    }

    /*
        MUTATORS AND ACCESSORS
     */
    public ObservableList<Users> getUserList() {
        return userList;
    }

    public ObservableList<Chores> getChoreList() {
        return choreList;
    }

    public ObservableList<UsersChores> getUsersChoreList() {
        return usersChoreList;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getUser() {
        return this.user;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Chores getChore() {
        return chore;
    }

    public void setChore(Chores chore) {
        this.chore = chore;
    }

    /**
     * Dynamic mutator method that makes use of Generic types. It will determine
     * the type of the object and plug it into the associated class member.
     * 
     * @param <T>
     * @param t
     * @throws Exception 
     */
    private <T> void setEditObject(T t) throws Exception {
        if (t instanceof Users) {
            this.user = (Users) t;
        } else if (t instanceof Chores) {
            this.chore = (Chores) t;
        } else {
            System.out.println("Error: Not a valid object type");
            throw new Exception("Logic Exception, Not a valid object type");
        }
    }

    /*
        HELP METHODS
     */
    /**
     * For new entries
     *
     * @param fileLoc
     * @throws IOException
     */
    private void _loadStage(String fileLoc) throws IOException {
        // Loading the FXML file for the front end
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fileLoc));

        // Getting Parent FXML class (Not sure what this does)
        Parent root = (Parent) loader.load();

        // Build the scene
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Load the main controller
        ViewController controller = loader.getController();
        controller.setEditMode(false);

        // Set the initilized content to the controller
        controller.setManager(this);
        controller.initValues();

        stage.show();
    }

    /**
     * For editing mainly
     *
     * @param <T>
     * @param fileLoc
     * @param t
     * @throws IOException
     */
    private <T extends Object> void _loadStage(String fileLoc, T t) throws IOException, Exception {
        // Loading the FXML file for the front end
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fileLoc));

        // Getting Parent FXML class (Not sure what this does)
        Parent root = (Parent) loader.load();

        // Build the scene
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // store the object
        this.setEditObject(t);

        // Load the main controller
        ViewController controller = loader.getController();
        controller.setEditMode(true);

        // Set the initilized content to the controller
        controller.setManager(this);
        controller.initValues();

        stage.show();

    }

}
