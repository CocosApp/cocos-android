package com.cerezaconsulting.cocosapp.data.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kath on 17/12/17.
 */

public class RestEntinty implements Serializable {

    private int id;
    private String name;
    private String ruc;
    private ArrayList<SubCatEntity> subcategory;
    private String longitude;
    private String latitude;
    private String address;
    private String whatsapp;
    private String facebook;
    private ArrayList<ScheduleEntity> schedule;
    private ArrayList<DescEntity> discount;
    private String food_letter;
    private String mobile;
    private String photo1;
    private String photo2;
    private String photo3;
    private ArrayList<ServEntity> service;

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public ArrayList<SubCatEntity> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(ArrayList<SubCatEntity> subcategory) {
        this.subcategory = subcategory;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<ScheduleEntity> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<ScheduleEntity> schedule) {
        this.schedule = schedule;
    }

    public ArrayList<DescEntity> getDiscount() {
        return discount;
    }

    public void setDiscount(ArrayList<DescEntity> discount) {
        this.discount = discount;
    }

    public String getFood_letter() {
        return food_letter;
    }

    public void setFood_letter(String food_letter) {
        this.food_letter = food_letter;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public ArrayList<ServEntity> getService() {
        return service;
    }

    public void setService(ArrayList<ServEntity> service) {
        this.service = service;
    }
}
