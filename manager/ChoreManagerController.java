package manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import manager.model.Chores;
import manager.model.Manager;
import manager.model.Users;
import manager.model.UsersChores;

/**
 * FXML Controller class
 *
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment
 */
public class ChoreManagerController implements Initializable {

    @FXML
    private Tab choreListTab;
    @FXML
    private TableView<Chores> listViewChores;
    @FXML
    private TableColumn<Chores, String> choreName;
    @FXML
    private TableColumn<Chores, String> choreDescription;
    @FXML
    private TableColumn<Chores, Boolean> choreCompleted;
    @FXML
    private TableColumn<Chores, Integer> choreDueDate;
    @FXML
    private TableView<Users> listViewUsers;
    @FXML
    private TableColumn<Users, String> userName;
    @FXML
    private Tab usersTab;
    @FXML
    private Button editUser;
    @FXML
    private Button deleteUser;
    @FXML
    private Tab choresTab;
    @FXML
    private Button editChore;
    @FXML
    private Button deleteChore;
    @FXML
    private TabPane managerTabPane;
    @FXML
    private MenuItem menuItemNewUser;
    @FXML
    private MenuItem menuItemNewChore;
    @FXML
    private TextArea usersChoreDescription;
    @FXML
    private TableView<UsersChores> listViewUsersChore;
    @FXML
    private TableColumn<UsersChores, String> usersChoreName;
    @FXML
    private TableColumn<UsersChores, String> usersChoreCompleted;
    @FXML
    private TableColumn<UsersChores, String> usersChoreDueDate;
    @FXML
    private TableColumn<UsersChores, String> usersChoreAssingee;
    @FXML
    private Menu mainMenu;

    /**
     * Contains the manager to insure that we have its presents at all points.
     */
    private Manager manager;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // For Users
        this.userName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        // For Chores
        this.choreName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        this.choreDescription.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        this.choreCompleted.setCellValueFactory(
                new PropertyValueFactory<>("completed"));
        this.choreDueDate.setCellValueFactory(
                new PropertyValueFactory<>("dueDate"));

        // For user chores or the main page
        this.usersChoreName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        this.usersChoreCompleted.setCellValueFactory(
                new PropertyValueFactory<>("completed"));
        this.usersChoreDueDate.setCellValueFactory(
                new PropertyValueFactory<>("dueDate"));
        this.usersChoreAssingee.setCellValueFactory(
                new PropertyValueFactory<>("assignee"));
        
        this.listViewUsersChore.getSelectionModel().selectedItemProperty().addListener((obs, oldS, newS)->{
            this.usersChoreDescription.setText(
                this.listViewUsersChore.getSelectionModel().getSelectedItem().getDescription()
            );
        });
        
        // For the Menu
        this.mainMenu.setAccelerator(
                new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN));
        this.menuItemNewChore.setAccelerator(
                new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        this.menuItemNewUser.setAccelerator(
                new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN));
    }

    /**
     * Set the manager and load the observer lists into the view
     *
     * @param manager
     */
    public void setManager(Manager manager) {
        this.manager = manager;
        this.listViewUsers.setItems(this.manager.getUserList());
        this.listViewChores.setItems(this.manager.getChoreList());
        this.listViewUsersChore.setItems(this.manager.getUsersChoreList());
    }

    /**
     * This will call the manager to load up the manager.view.UserController
     *
     * @throws java.lang.Exception
     */
    @FXML
    public void createUserHandle() throws Exception {
        this.manager.loadUserMenu();
        this.listViewUsers.refresh();
    }

    /**
     * This will call the manager to load up the manager.view.ChoreController
     *
     * @throws java.lang.Exception
     */
    @FXML
    private void createChoreHandle() throws Exception {
        this.manager.loadChoreMenu();
        this.listViewChores.refresh();
        this.listViewUsersChore.refresh();
    }

    /**
     * Delete button to remove the selected item based off of the current tab
     *
     * @throws IOException
     */
    @FXML
    public void delete() throws IOException {
        // find the selected element
        Tab t = this.managerTabPane.getSelectionModel().getSelectedItem();

        switch (t.textProperty().getValue()) {
            case "Users":
                Users users = this.listViewUsers.getSelectionModel().getSelectedItem();
                this.manager.delete(users);
                break;
            case "Chores":
                Chores chores = (Chores) this.listViewChores.getSelectionModel().getSelectedItem();
                this.manager.delete(chores);
                break;
        }
    }

    /**
     * Edit button to remove the selected item based off of the current tab
     *
     * @throws java.lang.Exception
     */
    @FXML
    public void edit() throws Exception {
        // find the selected element
        Tab t = this.managerTabPane.getSelectionModel().getSelectedItem();

        switch (t.textProperty().getValue()) {
            case "Users":
                Users users = this.listViewUsers.getSelectionModel().getSelectedItem();
                this.manager.setIndex(this.listViewUsers.getSelectionModel().getSelectedIndex());
                this.manager.edit(users);
                this.listViewUsers.refresh();
                break;
            case "Chores":
                Chores chores = this.listViewChores.getSelectionModel().getSelectedItem();
                this.manager.setIndex(this.listViewChores.getSelectionModel().getSelectedIndex());
                this.manager.edit(chores);
                this.listViewChores.refresh();
                break;
        }
    }

    @FXML
    private void closeButton() {
        Platform.exit();
    }

}
