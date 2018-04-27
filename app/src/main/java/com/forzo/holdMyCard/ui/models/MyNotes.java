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

    @SerializedName("libraryProfile")
    @Expose
    private String libraryProfileId;


    @SerializedName("libraryProfileImage")
    @Expose
    private String libraryProfileImage;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("userId")
    @Expose
    private String userId;


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
