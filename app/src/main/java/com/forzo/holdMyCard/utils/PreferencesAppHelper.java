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
    private static final String CURRENT_USER_ID = "id";
    private static final String FIRST_TIME = "firstTime";
    private static SharedPreferences mSharedPreferences = null;

    public static String getUserId() {
        return getSharedPreference().getString(CURRENT_USER_ID, null);
    }

    public static void setUserId(String userId) {
        setStringInPrefs(CURRENT_USER_ID, userId);
    }

    public static Boolean getFirstTime() {
        return getSharedPreference().getBoolean(FIRST_TIME, false);
    }

    public static void setFirstTime(boolean firstTime) {
        setBooleanInPrefs(FIRST_TIME, firstTime);
    }

    private static void setStringInPrefs(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static void setBooleanInPrefs(String key, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static SharedPreferences getSharedPreference() {
        if (mSharedPreferences == null) {
            mSharedPreferences = HmcApplication.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }
}
