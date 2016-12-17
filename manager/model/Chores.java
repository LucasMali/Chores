/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager.model;

import java.util.HashMap;

/**
 * This is a simplistic entity type. Even thought it breaks some DB entity
 * design pattern conventions, it is simply here to comply.
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment 
 */
public class Chores {

    private String name, dueDate, description;
    private boolean completed;
    private int id, userId;
    
    public Chores(){}
    
    public Chores(HashMap<String, String> u) {
        this.name = (String) u.get("name");
        this.description = (String) u.get("description");
        this.dueDate = (String) u.get("duedate");
        this.completed = Boolean.valueOf((String) u.get("completed"));
        
        if(u.get("id") != null){
            this.id = Integer.valueOf((String) u.get("id"));
        }
        if(u.get("userid") != null){
            this.userId = Integer.valueOf((String) u.get("userid"));
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setUserId(String userId){
        this.userId = Integer.valueOf(userId);
    }
    
}
