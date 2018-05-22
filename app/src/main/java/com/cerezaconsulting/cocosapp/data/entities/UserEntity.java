package com.cerezaconsulting.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by junior on 30/09/16.
 */
public class UserEntity implements Serializable {

    private int id;
    private String email;
    private String last_name;
    private String first_name;
    private String password;
    private String picture;

    public UserEntity(String email, String last_name, String first_name, String password) {
        this.email = email;
        this.last_name = last_name;
        this.first_name = first_name;
        this.password = password;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName(){

        return first_name + " " + last_name ;
    }
}
