package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibrary {

   private String cardImage;


    @SerializedName("name")
    @Expose
    private String cardName;

    @SerializedName("description")
    @Expose
    private  String cardDescription;


    @SerializedName("details")
    @Expose
    private String cardDetails;


    public MyLibrary(String cardName, String cardDescription, String cardDetails) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardDetails = cardDetails;
    }



    public String getCardImage() {
        return cardImage;
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

    public MyLibrary(String cardImage, String cardName, String cardDescription, String cardDetails) {
        this.cardImage = cardImage;
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardDetails = cardDetails;
    }

    public MyLibrary() {
    }
}
