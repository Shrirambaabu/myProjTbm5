package com.forzo.holdMyCard.api;

import retrofit2.Retrofit;

/**
 * Created by Shriram on 4/6/2018.
 */

public class ApiFactory {
    public static ApiService create(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
