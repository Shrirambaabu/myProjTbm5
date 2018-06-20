package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Groups {


    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("image")
    @Expose
    private String imageName;

    @SerializedName("imageType")
    @Expose
    private String imageType;

    @SerializedName("libraryOwnerId")
    @Expose
    private String libraryOwnerId;
    @SerializedName("libraryContactId")
    @Expose
    private String libraryContactId;
    @SerializedName("added to our library")
    @Expose
    private String addedLibrary;
    @SerializedName("libraryGroupName")
    @Expose
    private String libraryGroupName;

    public Groups() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getLibraryOwnerId() {
        return libraryOwnerId;
    }

    public void setLibraryOwnerId(String libraryOwnerId) {
        this.libraryOwnerId = libraryOwnerId;
    }

    public String getLibraryContactId() {
        return libraryContactId;
    }

    public void setLibraryContactId(String libraryContactId) {
        this.libraryContactId = libraryContactId;
    }

    public String getAddedLibrary() {
        return addedLibrary;
    }

    public void setAddedLibrary(String addedLibrary) {
        this.addedLibrary = addedLibrary;
    }

    public String getLibraryGroupName() {
        return libraryGroupName;
    }

    public void setLibraryGroupName(String libraryGroupName) {
        this.libraryGroupName = libraryGroupName;
    }
}
