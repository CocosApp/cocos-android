package com.cerezaconsulting.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by kath on 8/03/18.
 */

public class MessageResponse implements Serializable {
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
