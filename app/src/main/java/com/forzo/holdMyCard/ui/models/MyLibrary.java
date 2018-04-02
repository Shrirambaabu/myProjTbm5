package com.forzo.holdMyCard.ui.models;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibrary {

    String cardImage;

    String cardName;

    String cardDescription;

    public MyLibrary(String cardName, String cardDescription, String cardDetails) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardDetails = cardDetails;
    }

    String cardDetails;

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
