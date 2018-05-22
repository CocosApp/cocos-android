package com.cerezaconsulting.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by kath on 15/03/18.
 */

public class IsMyFavouriteRestaurante implements Serializable {

    private int restaurant;
    private boolean is_color;

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isIs_color() {
        return is_color;
    }

    public void setIs_color(boolean is_color) {
        this.is_color = is_color;
    }
}
