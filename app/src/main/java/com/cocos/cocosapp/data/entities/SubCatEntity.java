package com.cocos.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by kath on 18/12/17.
 */

public class SubCatEntity implements Serializable {

    private int id;
    private String name;
    private String descrip;
    private String image;


    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getPhoto() {
        return image;
    }

    public void setPhoto(String photo) {
        this.image = photo;
    }

    public SubCatEntity(String name) {
        this.name = name;
    }

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
}
