package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckRegister {
    @SerializedName("Already Registered")
    @Expose
    private Boolean regisgter;

    public CheckRegister() {
    }

    public Boolean getRegisgter() {
        return regisgter;
    }

    public void setRegisgter(Boolean regisgter) {
        this.regisgter = regisgter;
    }
}
