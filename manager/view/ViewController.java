package manager.view;

import manager.model.Chores;
import manager.model.Manager;
import manager.model.Users;

/**
 * The foundation for the view controllers.
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Dec 7, 2016, CSC-240 Assignment 
 */
public abstract class ViewController {

    private Manager manager;

    private Users user;

    private Chores chore;
    
    private boolean editMode = false;
    
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Chores getChore() {
        return chore;
    }

    public void setChore(Chores chore) {
        this.chore = chore;
    }
    
    public Manager getManager() {
        return this.manager;
    }

    public void initValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
    
    
}
