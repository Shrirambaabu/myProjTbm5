package com.forzo.holdMyCard.ui.models;

/**
 * Created by Shriram on 4/5/2018.
 */

public class MyRemainder {

    private String name;

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

    private String dateTime;

    public MyRemainder(String name, String dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public MyRemainder() {
    }
}
