package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupsE {


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
    @SerializedName("createdTs")
    @Expose
    private String createdTs;
    @SerializedName("totalMembers")
    @Expose
    private String totalMembers;
    @SerializedName("libraryGroupId")
    @Expose
    private String libraryGroupId;
    @SerializedName("image")
    @Expose
    private String image;

    public GroupsE() {
    }
}
