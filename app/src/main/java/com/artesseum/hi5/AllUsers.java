package com.artesseum.hi5;

/**
 * Created by apoca on 30/11/2017.
 */

public class AllUsers {


    public String email;
    public String username;

    public AllUsers(){

    }

    //constructor
    public AllUsers(String email, String username) {
        this.email = email;
        this.username = username;
    }
    // parameters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
