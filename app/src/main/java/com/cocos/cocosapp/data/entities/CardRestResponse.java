package com.cocos.cocosapp.data.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kath on 25/01/18.
 */

public class CardRestResponse implements Serializable {

    private int id;
    private String name;
    private Double porc;
    private String descrip;
    private String card;
    private ArrayList<RestauranteResponse> restaurants;

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

    public Double getPorc() {
        return porc;
    }

    public void setPorc(Double porc) {
        this.porc = porc;
    }

    public String getDecrip() {
        return descrip;
    }

    public void setDecrip(String decrip) {
        this.descrip = decrip;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public ArrayList<RestauranteResponse> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<RestauranteResponse> restaurants) {
        this.restaurants = restaurants;
    }
}
