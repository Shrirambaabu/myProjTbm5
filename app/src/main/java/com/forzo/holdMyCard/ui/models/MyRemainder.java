package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/5/2018.
 */

public class MyRemainder {

    @SerializedName("remainderDesc")
    @Expose
    private String name;

    @SerializedName("libraryProfile")
    @Expose
    private String libraryProfileId;

    @SerializedName("libraryProfileImage")
    @Expose
    private String libraryProfileImage;

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("remainderId")
    @Expose
    private String id;
    @SerializedName("remainderdate")
    @Expose
    private String date;
    @SerializedName("remainderStatusId")
    @Expose
    private String statusId;
    @SerializedName("remainderTime")
    @Expose
    private String time;

    public MyRemainder(String name, String dateTime) {
        this.name = name;
        this.date = dateTime;
    }

    public MyRemainder() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibraryProfileId() {
        return libraryProfileId;
    }

    public void setLibraryProfileId(String libraryProfileId) {
        this.libraryProfileId = libraryProfileId;
    }

    public String getLibraryProfileImage() {
        return libraryProfileImage;
    }

    public void setLibraryProfileImage(String libraryProfileImage) {
        this.libraryProfileImage = libraryProfileImage;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return date;
    }

    public void setDateTime(String dateTime) {
        this.date = dateTime;
    }
}
