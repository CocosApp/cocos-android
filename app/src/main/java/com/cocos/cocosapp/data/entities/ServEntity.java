package com.cocos.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by kath on 28/12/17.
 */

public class ServEntity implements Serializable {
    private int id;
    private String name;

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
