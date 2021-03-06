package com.forzo.holdMyCard.ui.activities.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface HomeContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

        void callGoogleCloudVision(Uri uri, Feature feature, AVLoadingIndicatorView avLoadingIndicatorView, Uri intentUri, RelativeLayout relativeLayout, RelativeLayout relativeLayoutMain);

        void callVisionApi(HomeActivity homeActivity, Bitmap bitmap, Feature feature, Uri uri, AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeLayout, RelativeLayout relativeLayoutTwo, File image);

        void requestPermissions(String[] permissions, int requestCode);

        boolean checkPermission(String[] permissions, int requestCode);

        boolean checkPermission(String[] permissions);

        int checkPermission(Context context, String permission);

        void checkVersion();

        void handleResult(int requestCode, int resultCode, Intent data, Uri capturedImageUri);

        void callGoogleCloudVision(Uri resultUri, Feature feature);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);

        void setVersionNumber(String versionNumber);

        void onPhotosReturned(Uri photoUri);

        void sendCroppedImage(Uri resultUri);

        void showDialog();

        void hideDialog();

    }
}
