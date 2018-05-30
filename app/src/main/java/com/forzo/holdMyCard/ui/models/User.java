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
