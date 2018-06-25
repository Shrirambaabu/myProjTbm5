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

    @SerializedName("userContact2")
    @Expose
    private String phoneNumber2;

    @SerializedName("userContact3")
    @Expose
    private String phoneNumber3;


    @SerializedName("userEmail")
    @Expose
    private String emailId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SerializedName("date")
    @Expose

    private String date;

    @SerializedName("userWebsite")
    @Expose
    private String website;

    @SerializedName("modifiedTs")
    @Expose
    private String modifiedTs;

    @SerializedName("updateImage")
    @Expose
    private String updateImage;

    public String getUpdateImage() {
        return updateImage;
    }

    public void setUpdateImage(String updateImage) {
        this.updateImage = updateImage;
    }

    public String getModifiedTs() {
        return modifiedTs;
    }

    public void setModifiedTs(String modifiedTs) {
        this.modifiedTs = modifiedTs;
    }

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

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setPhoneNumber3(String phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
    }

    public BusinessCard(String id, String name, String company, String jobTitle, String phoneNumber,String phoneNumber2,String phoneNumber3, String emailId, String website, String image) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.jobTitle = jobTitle;
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumber3 = phoneNumber3;
        this.emailId = emailId;
        this.website = website;
        this.image = image;
    }

    public BusinessCard() {
    }
}
