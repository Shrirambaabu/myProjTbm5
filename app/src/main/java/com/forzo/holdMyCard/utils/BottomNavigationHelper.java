package com.forzo.holdMyCard.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.activities.home.HomeActivity;
import com.forzo.holdMyCard.ui.activities.library.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.sort.SortCardActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


/**
 * Created by Shriram on 3/29/2018.
 */

public class BottomNavigationHelper {


    public static void setupBottomNavigationSetUp(BottomNavigationViewEx bnve) {
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setTextVisibility(true);
    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx bnve) {


        bnve.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.ic_camera:
                    Intent homeIntent = new Intent(context, HomeActivity.class);
                    context.startActivity(homeIntent);
                    callingActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case R.id.ic_library:
                    Intent libraryIntent = new Intent(context, MyLibraryActivity.class);
                    context.startActivity(libraryIntent);
                    callingActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;

                case R.id.ic_profile:
                    Intent profileIntent = new Intent(context, ProfileActivity.class);
                    context.startActivity(profileIntent);
                    callingActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case R.id.ic_sort_card:

                    Intent sortIntent = new Intent(context, SortCardActivity.class);
                    context.startActivity(sortIntent);
                    callingActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    break;

            }
            return false;

        });


    }
}
