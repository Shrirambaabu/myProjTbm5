package com.forzo.holdMyCard.utils;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Shriram on 4/4/2018.
 */

public class Utils {

    public static void backButtonOnToolbar(AppCompatActivity mActivity) {
        if (mActivity.getSupportActionBar() != null) {
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

}
