package com.example.dochere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMedicine {

    @SerializedName("med_name")
    @Expose
    private String med_name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("night")
    @Expose
    private String night;

    @SerializedName("morning")
    @Expose
    private String morning;

    @SerializedName("userId")
    @Expose
    private String userId;


    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setName(String name) {
        this.med_name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
