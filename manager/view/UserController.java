package manager.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment 
 */
public class UserController extends ViewController implements Initializable {

    @FXML
    private Button memberCancel;
    @FXML
    private Label memberNameLabel;
    @FXML
    private TextField memberNameInput;
    @FXML
    private Button userSave;
    @FXML
    private Label subTitle;

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
    public void saveUser() throws Exception {
        try {

            this._checkValues();

            String value = this.memberNameInput.getText();

            // If we are saving a new user
            if (!super.isEditMode()) {
                super.getManager().createUser(value);
                // If we are updating an existing user
            } else {
                super.getManager().updateUser(value);
                if (super.isEditMode()) {
                    super.setEditMode(false);
                }
            }
            this.closeButton();
        } catch (NameNullException nne) {
            this.memberNameInput.setText(nne.getMessage());
            this.memberNameInput.requestFocus();
        } catch (Exception e) {
            throw e;
        }
    }

    @FXML
    private void closeButton() {
        System.out.println(super.getManager());
        // get a handle to the stage
        Stage stage = (Stage) memberCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initValues() {
        if (this.isEditMode()) {
            this.subTitle.setText("Change the users name");
            this.memberNameInput.setText(super.getManager().getUser().getName());
        }
    }

    private void _checkValues() throws NameNullException {
        // Check for values
        if (this.memberNameInput.getText() == null
                || this.memberNameInput.getText().trim().isEmpty()) {
            throw new UserController.NameNullException("Set the title");
        }
    }

    private class NameNullException extends Exception {

        private NameNullException(String message) {
            super(message);
        }

    }

}
