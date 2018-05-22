package com.cerezaconsulting.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by kath on 27/03/18.
 */

public class StatusColorEntity implements Serializable {
    private boolean is_color;

    public boolean isIs_color() {
        return is_color;
    }

    public void setIs_color(boolean is_color) {
        this.is_color = is_color;
    }
}
