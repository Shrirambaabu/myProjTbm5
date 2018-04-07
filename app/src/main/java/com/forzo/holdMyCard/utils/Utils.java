package com.forzo.holdMyCard.utils;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Shriram on 4/4/2018.
 */

public class Utils {

    public static String Base = "http://192.168.43.29:8080";
  //  public static String Base = "http://52.15.123.231:8080";

    public static String BaseUri = Base + "/basic/";
    public static void backButtonOnToolbar(AppCompatActivity mActivity) {
        if (mActivity.getSupportActionBar() != null) {
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

}
