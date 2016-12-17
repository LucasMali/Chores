package manager.model;

import java.util.HashMap;

/**
 * This is a simplistic entity type. Even thought it breaks some DB entity
 * design pattern conventions, it is simply here to comply.
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment 
 */
public class Users {
    
    private String name;
    private int id;
    
    public Users(){}
    
    public Users(HashMap u) {
        this.id = Integer.valueOf((String) u.get("id"));
        this.name = (String) u.get("name");
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
