package com.forzo.holdMyCard.utils;

/**
 * Created by Shriram on 4/27/2018.
 */

public class Constants {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";

    public static final String UN_REGISTERED = "UnRegistered";
    public static final String REGISTERED = "Registered";
    public static final String UI_STATUS_ONE = "1";
    public static final String UI_STATUS_ZERO = "0";
    public static final String IS_ENABLED_TRUE = "true";
    public static final String IS_ENABLED_FALSE = "false";

}
