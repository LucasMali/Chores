package manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The manager to load the Chore interface.
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment 
 */
public class ChoreManager extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Loading the FXML file for the front end
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChoreManager.fxml"));

        // Getting Parent FXML class (Not sure what this does)
        Parent root = (Parent)loader.load();
        
        // Init the application.
        manager.model.Manager man = manager.model.Manager.init();
        
        // Store the stage
        man.setStage(stage);
        
        // Load the main controller
        ChoreManagerController controller = (ChoreManagerController) loader.getController();
        
        // Set the initilized content to the controller
        controller.setManager(man);
        
        // Build the scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
