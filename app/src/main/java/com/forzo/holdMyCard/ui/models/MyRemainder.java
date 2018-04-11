package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/5/2018.
 */

public class MyRemainder {

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("dateTime")
    @Expose
    private String dateTime;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }



    public MyRemainder(String name, String dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public MyRemainder() {
    }
}
