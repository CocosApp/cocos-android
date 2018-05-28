package com.cocos.cocosapp.data.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kath on 25/01/18.
 */

public class FavouriteResponse implements Serializable {
    private int id;
    private ArrayList<RestauranteResponse> fav_restaurant;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<RestauranteResponse> getFav_restaurant() {
        return fav_restaurant;
    }

    public void setFav_restaurant(ArrayList<RestauranteResponse> fav_restaurant) {
        this.fav_restaurant = fav_restaurant;
    }
}
