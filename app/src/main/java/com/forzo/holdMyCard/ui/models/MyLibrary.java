package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibrary {

   private String cardImage;


    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("userName")
    @Expose
    private String cardName;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("imageType")
    @Expose
    private String imageType;


    @SerializedName("userEmail")
    @Expose
    private  String cardDescription;


    @SerializedName("userContact")
    @Expose
    private String cardDetails;

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("libraryGroupName")
    @Expose
    private String libraryGroupName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @SerializedName("groupId")
    @Expose

    private String groupId;


    private boolean groupAdded;

    public boolean isGroupAdded() {
        return groupAdded;
    }

    public void setGroupAdded(boolean groupAdded) {
        this.groupAdded = groupAdded;
    }

    public String getLibraryGroupName() {
        return libraryGroupName;
    }

    public void setLibraryGroupName(String libraryGroupName) {
        this.libraryGroupName = libraryGroupName;
    }

    private boolean setChecked;

    public boolean isSetChecked() {
        return setChecked;
    }

    public void setSetChecked(boolean setChecked) {
        this.setChecked = setChecked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public MyLibrary(String cardName, String cardDescription, String cardDetails, String userId) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardDetails = cardDetails;
        this.userId = userId;
    }
    public MyLibrary(String cardName, String cardDescription, String cardDetails) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardDetails = cardDetails;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardImage() {
        return cardImage;
    }
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(String cardDetails) {
        this.cardDetails = cardDetails;
    }

   /* public MyLibrary(String cardImage, String cardName, String cardDescription, String cardDetails) {
        this.cardImage = cardImage;
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardDetails = cardDetails;
    }*/

    public MyLibrary() {
    }
}
