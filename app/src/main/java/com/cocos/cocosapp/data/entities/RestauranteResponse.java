package com.cocos.cocosapp.data.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kath on 25/01/18.
 */

public class RestauranteResponse implements Serializable {
    private int id;
    private String name;
    private ArrayList<SubCatEntity> subcategory;
    private String photo1;

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

    public ArrayList<SubCatEntity> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(ArrayList<SubCatEntity> subcategory) {
        this.subcategory = subcategory;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }
}
