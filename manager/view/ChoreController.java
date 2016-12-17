package manager.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import manager.model.Chores;
import manager.model.Users;

/**
 * FXML Controller class
 *
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment
 */
public class ChoreController extends ViewController implements Initializable {

    @FXML
    private Button choreCancel;
    @FXML
    private TextArea choreDescription;
    @FXML
    private TextField choreTitle;
    @FXML
    private DatePicker choreDueDate;
    @FXML
    private ChoiceBox<Users> choreMemberAssignment;
    @FXML
    private Label subTitle;
    @FXML
    private Button choreSave;
    @FXML
    private CheckBox choreCompleted;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void saveChore() throws Exception {
        try {
            System.out.println("Save");

            // See if the required values are present.
            this._checkValues();

            // If we are saving a new user
            if (!super.isEditMode()) {
                Map _map = new HashMap<>();
                _map.put("name", this.choreTitle.getText());
                _map.put("description", this.choreDescription.getText());
                _map.put("duedate", this.choreDueDate.getChronology().date(this.choreDueDate.getValue()).toString());
                _map.put("completed", String.valueOf(this.choreCompleted.isSelected()));
                _map.put("userid", String.valueOf(this.choreMemberAssignment.getSelectionModel().getSelectedItem().getId()));
                super.getManager().createChore(new Chores((HashMap) _map));
            } // If we are updating an existing user
            else {
                super.getManager().getChore().setCompleted(
                        this.choreCompleted.isSelected());
                super.getManager().getChore().setDescription(
                        this.choreDescription.getText());
                super.getManager().getChore().setDueDate(
                        this.choreDueDate.getChronology().date(this.choreDueDate.getValue()).toString());
                super.getManager().getChore().setName(this.choreTitle.getText());
                super.getManager().getChore().setUserId(this.choreMemberAssignment.getSelectionModel().getSelectedItem().getId());

                // Update the user
                super.getManager().updateChore(super.getManager().getChore());

                if (super.isEditMode()) {
                    super.setEditMode(false);
                }
            }

            // Load the Main Chore List Page
                // FIXME The assignee does not get updated until after restart
            this.getManager().loadUsersChores();
            
            this.closeButton();
        } catch (TitleNullException tne) {
            this.choreTitle.setText(tne.getMessage());
            this.choreTitle.requestFocus();
        } catch (DueDateNullException ddne) {
            this.choreDueDate.setPromptText(ddne.getMessage());
            this.choreDueDate.requestFocus();
        } catch (Exception e) {
            throw e;
        }
    }

    @FXML
    private void closeButton() {
        System.out.println(super.getManager());
        // get a handle to the stage
        Stage stage = (Stage) choreCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Plug and play
     */
    @Override
    public void initValues() {

        // This will override the default to show names rather than the object name
        this.choreMemberAssignment.setConverter(new StringConverter<Users>() {
            @Override
            public String toString(Users object) {
                return object.getName();
            }

            @Override
            public Users fromString(String string) {
                return null;
            }

        });

        // Add the user list into the assignment
        this.choreMemberAssignment.setItems(
                super.getManager().getUserList()
        );

        if (super.isEditMode()) {
            this.subTitle.setText("Change the chores values");
            this.choreTitle.setText(super.getManager().getChore().getName());
            this.choreDescription.setText(super.getManager().getChore().getDescription());

            String _tmp = super.getManager().getChore().getDueDate();
            if (_tmp != null) {
                this.choreDueDate.setValue(
                        LocalDate.parse(_tmp)
                );
            }
            this.choreCompleted.setSelected(super.getManager().getChore().isCompleted());

            ObservableList<Users> userList = this.getManager().getUserList();
            for (int i = 0; userList.size() > i; ++i) {
                if (userList.get(i).getId() == super.getManager().getChore().getUserId()) {
                    this.choreMemberAssignment.setValue(userList.get(i));
                    this.choreMemberAssignment.setItems(this.getManager().getUserList());
                    break;
                }
            }
        }
    }

    /**
     * Required values to save or update
     *
     * @throws manager.view.ChoreController.TitleNullException
     * @throws manager.view.ChoreController.DueDateNullException
     */
    private void _checkValues() throws TitleNullException, DueDateNullException {
        // Check for values
        if (this.choreTitle.getText() == null
                || this.choreTitle.getText().trim().isEmpty()) {
            throw new TitleNullException("Set the title");
        }

        LocalDate isoDate = this.choreDueDate.getValue();
        if ((LocalDate) this.choreDueDate.getValue() == null) {
            throw new DueDateNullException("Must be valid date");
        }

    }

    /**
     * Custom Exceptions to make sure we fill out the title
     */
    private class TitleNullException extends Exception {

        private TitleNullException(String message) {
            super(message);
        }

    }

    private class DueDateNullException extends Exception {

        private DueDateNullException(String message) {
            super(message);
        }

    }
}
