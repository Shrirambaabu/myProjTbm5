package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/11/2018.
 */

public class BusinessCard {

    @SerializedName("notes")
    @Expose
    private String name;


    @SerializedName("notes")
    @Expose
    private String id;


    @SerializedName("notes")
    @Expose
    private String company;


    @SerializedName("notes")
    @Expose
    private String jobTitle;


    @SerializedName("notes")
    @Expose
    private String phoneNumber;


    @SerializedName("notes")
    @Expose
    private String emailId;

    @SerializedName("notes")
    @Expose
    private String website;


    @SerializedName("notes")
    @Expose
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BusinessCard(String id,String name, String company, String jobTitle, String phoneNumber, String emailId, String website, String image) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.jobTitle = jobTitle;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.website = website;
        this.image = image;
    }

    public BusinessCard() {
    }
}
