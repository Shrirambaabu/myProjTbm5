package com.forzo.holdMyCard.error;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Shriram on 4/11/2018.
 */

public class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            throw new HMCError(
                    response.code(),
                    response.message()
            );
        }
        return response;
    }
}
