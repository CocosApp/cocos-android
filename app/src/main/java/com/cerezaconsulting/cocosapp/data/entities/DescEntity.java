package com.cerezaconsulting.cocosapp.data.entities;

import java.io.Serializable;

/**
 * Created by kath on 28/12/17.
 */

public class DescEntity implements Serializable {
    private int id;
    private String name;
    private CardEntity card;
    private Double porc;
    private Double price;
    private String photo;
    private String promotion;
    private String terms_condition;
    private String descrip;
    private boolean is_owner;

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public CardEntity getCard() {
        return card;
    }

    public void setCard(CardEntity card) {
        this.card = card;
    }

    public Double getPorc() {
        return porc;
    }

    public void setPorc(Double porc) {
        this.porc = porc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTerms_condition() {
        return terms_condition;
    }

    public void setTerms_condition(String terms_condition) {
        this.terms_condition = terms_condition;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public boolean isIs_owner() {
        return is_owner;
    }

    public void setIs_owner(boolean is_owner) {
        this.is_owner = is_owner;
    }
}
