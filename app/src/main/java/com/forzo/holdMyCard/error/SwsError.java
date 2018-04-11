package com.forzo.holdMyCard.error;

import java.io.IOException;

/**
 * Created by Shriram on 4/11/2018.
 */

public class SwsError extends IOException {
    private int responseCode;
    private String message;

    SwsError(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

