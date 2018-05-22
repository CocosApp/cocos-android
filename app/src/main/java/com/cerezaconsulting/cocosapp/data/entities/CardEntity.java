package com.cerezaconsulting.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by kath on 28/12/17.
 */

public class CardEntity implements Serializable {
    private int id;
    private String name;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
