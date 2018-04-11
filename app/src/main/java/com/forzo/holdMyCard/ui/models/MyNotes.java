package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotes {

    @SerializedName("notes")
    @Expose
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public MyNotes(String notes) {
        this.notes = notes;
    }

    public MyNotes() {
    }
}
