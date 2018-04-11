package com.forzo.holdMyCard.utils;

import android.support.annotation.Nullable;

/**
 * Created by Shriram on 4/11/2018.
 */

public class NullUtils {


    public static boolean notEmpty(@Nullable String string) {
        return string != null && string.length() > 0;
    }

}
