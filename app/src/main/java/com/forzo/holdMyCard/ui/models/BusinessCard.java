package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/11/2018.
 */

public class BusinessCard {

    @SerializedName("userName")
    @Expose
    private String name;

    @SerializedName("userId")
    @Expose

    private String userId;


    @SerializedName("imageType")
    @Expose
    private String imageType;


    @SerializedName("image")
    @Expose
    private String postImage;



    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("libraryUserId")
    @Expose
    private String id;


    @SerializedName("company")
    @Expose
    private String company;


    @SerializedName("jobTitle")
    @Expose
    private String jobTitle;


    @SerializedName("userContact")
    @Expose
    private String phoneNumber;


    @SerializedName("userEmail")
    @Expose
    private String emailId;

    @SerializedName("userWebsite")
    @Expose
    private String website;


    @SerializedName("userImageId")
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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
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
