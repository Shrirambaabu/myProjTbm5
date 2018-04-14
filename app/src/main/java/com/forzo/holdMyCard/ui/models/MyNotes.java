package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotes {

    @SerializedName("notesDesc")
    @Expose
    private String notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MyNotes(String notes) {
        this.notes = notes;
    }

    @SerializedName("notesId")
    @Expose
    private String id;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public MyNotes(String notes,String id) {
        this.notes = notes;
        this.id = id;
    }

    public MyNotes() {
    }
}
