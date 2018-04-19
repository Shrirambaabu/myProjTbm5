package com.forzo.holdMyCard.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shriram on 4/17/2018.
 */

public class User {

    @SerializedName("userUiud")
    @Expose
    private String uuid;

    @SerializedName("userExists")
    @Expose
    private String uuidExists;

    public String getUuidExists() {
        return uuidExists;
    }

    public void setUuidExists(String uuidExists) {
        this.uuidExists = uuidExists;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User() {
    }
}
