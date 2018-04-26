package com.forzo.holdMyCard.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.forzo.holdMyCard.HmcApplication;

import static android.os.Build.ID;

/**
 * Created by Shriram on 4/24/2018.
 */

public class PreferencesAppHelper {

    private static final String PREFS_NAME = "HMC_APP";
    private static SharedPreferences mSharedPreferences = null;

    public static final String CURRENT_USER_ID = "id";



    public static String getCurrentUserId() {
        return CURRENT_USER_ID;
    }


    public static void setUserId(String userId) {
        setDataInPrefs(ID, userId);
    }

    public static String getUserId() {
        return getSharedPreference().getString(ID, null);
    }


    private static void setDataInPrefs(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value);
        editor.apply();
    }


    private static SharedPreferences getSharedPreference() {
        if (mSharedPreferences == null) {
            mSharedPreferences = HmcApplication.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }

        return mSharedPreferences;
    }

}
