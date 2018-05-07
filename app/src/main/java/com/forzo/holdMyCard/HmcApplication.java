package com.forzo.holdMyCard;


import android.app.Activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.UUID;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Shriram on 4/11/2018.
 */

public class HmcApplication extends Application {

     //public static final String BASE_URL = "http://192.168.43.29:8080/";
    public static final String BASE_URL = "http://52.14.81.16:8080/holdmycard-0.1/";

    // public static final  String IMAGE_URL="http://52.14.81.16:8080/holdmycard-0.1/upload-dir/";
    public static final String IMAGE_URL = "http://52.14.81.16:8080/holdmycard-0.1/upload-dir/";
    private static Context context;
    @Inject
    Retrofit retrofit;
    @Inject
    Gson gson;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    OkHttpClient okHttpClient;
    private HmcComponent hmcComponent;

    public static HmcApplication get(Activity activity) {
        return (HmcApplication) activity.getApplication();
    }

    public static HmcApplication getApplication(Application application) {
        return (HmcApplication) application;
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // LeakCanary.install(this);
        // JodaTimeAndroid.init(this);

        context = this;

        hmcComponent = DaggerHmcComponent.builder()
                .contextModule(new ContextModule(context))
                .hmcAppModule(new HmcAppModule(this))
                .build();

        hmcComponent.inject(this);


    }


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Gson getGson() {
        return gson;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public HmcComponent getSwsComponent() {
        return hmcComponent;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

}
