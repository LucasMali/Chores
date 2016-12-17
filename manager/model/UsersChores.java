package manager.model;

/**
 * The combined object of the users and chores to help build the list
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment 
 */
public class UsersChores {
    private String name;
    private boolean completed;
    private String dueDate;
    private String assignee;
    private String description;
    
    public UsersChores(){}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
