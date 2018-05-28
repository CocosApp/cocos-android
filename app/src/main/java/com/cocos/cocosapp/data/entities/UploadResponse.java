package com.cocos.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by miguel on 02/03/17.
 */

public class UploadResponse implements Serializable {
    private String picture;

    public String getPhoto() {
        return picture;
    }

    public void setPhoto(String photo) {
        this.picture = photo;
    }
}
