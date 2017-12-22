package com.artesseum.hi5.models;

/**
 * Created by apoca on 20/12/2017.
 */

public class Users {

    private String displayname;
    private String email;
    private String photoURL;

    public Users(){}

    public Users(String displayname, String email, String photoURL) {
        this.displayname = displayname;
        this.email = email;
        this.photoURL = photoURL;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
