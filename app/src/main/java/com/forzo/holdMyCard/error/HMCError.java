package com.forzo.holdMyCard.error;

import android.util.Log;

import java.io.IOException;

/**
 * Created by Shriram on 4/11/2018.
 */

public class HMCError extends IOException {
    private static final String TAG = "HMCError";
    private int responseCode;
    private String message;

    HMCError(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
        Log.e(TAG, "HMCError: " + message + " code " + responseCode);
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

