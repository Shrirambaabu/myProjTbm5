package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/17/2018.
 */

public class User {

    @SerializedName("userUiud")
    @Expose
    private String uuid;

    @SerializedName("userId")
    @Expose
    private String userId;

    public String getForgetPassword() {
        return forgetPassword;
    }

    public void setForgetPassword(String forgetPassword) {
        this.forgetPassword = forgetPassword;
    }

    @SerializedName("Forgot Password")
    @Expose

    private String forgetPassword;

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("password")
    @Expose
    private String userPassword;

    @SerializedName("userExists")
    @Expose
    private String uuidExists;
    @SerializedName("Registration Successful")
    @Expose
    private String regStatus;
    @SerializedName("getEnabled")
    @Expose
    private String isEnabled;
    @SerializedName("date")
    @Expose
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getExistingUser() {
        return existingUser;
    }

    public void setExistingUser(String existingUser) {
        this.existingUser = existingUser;
    }

    @SerializedName("Already Registered")
    @Expose

    private String existingUser;

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    public String getUuidExists() {
        return uuidExists;
    }

    public void setUuidExists(String uuidExists) {
        this.uuidExists = uuidExists;
    }

    public String getUuid() {
        return uuid;
    }


    public String getNewUser() {
        return userId;
    }

    public void setNewUser(String newUser) {
        this.userId = newUser;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User() {
    }
}
