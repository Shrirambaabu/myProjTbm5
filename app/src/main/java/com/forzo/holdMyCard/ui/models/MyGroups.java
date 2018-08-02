package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyGroups {


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
    private String imageName;

    public String getRenameStatus() {
        return renameStatus;
    }

    public void setRenameStatus(String renameStatus) {
        this.renameStatus = renameStatus;
    }

    @SerializedName("Group Renamed")
    @Expose

    private String renameStatus;

    public String getLibraryGroupName() {
        return libraryGroupName;
    }

    public void setLibraryGroupName(String libraryGroupName) {
        this.libraryGroupName = libraryGroupName;
    }

    public String getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(String createdTs) {
        this.createdTs = createdTs;
    }

    public String getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(String totalMembers) {
        this.totalMembers = totalMembers;
    }

    public String getLibraryGroupId() {
        return libraryGroupId;
    }

    public void setLibraryGroupId(String libraryGroupId) {
        this.libraryGroupId = libraryGroupId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
