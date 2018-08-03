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


    @SerializedName("Group Renamed")
    @Expose
    private String renameStatus;

    @SerializedName("deleteGroupMember")
    @Expose
    private String deleteStatus;
    @SerializedName("deleteGroup")
    @Expose
    private String deleteGroup;
    @SerializedName("addGroupMember")
    @Expose
    private String addGroupMember;

    public String getAddGroupMember() {
        return addGroupMember;
    }

    public void setAddGroupMember(String addGroupMember) {
        this.addGroupMember = addGroupMember;
    }

    public String getDeleteGroup() {
        return deleteGroup;
    }

    public void setDeleteGroup(String deleteGroup) {
        this.deleteGroup = deleteGroup;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getLibraryGroupName() {
        return libraryGroupName;
    }

    public void setLibraryGroupName(String libraryGroupName) {
        this.libraryGroupName = libraryGroupName;
    }
    public String getRenameStatus() {
        return renameStatus;
    }

    public void setRenameStatus(String renameStatus) {
        this.renameStatus = renameStatus;
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
