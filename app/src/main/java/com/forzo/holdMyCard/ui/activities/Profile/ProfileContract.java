package com.forzo.holdMyCard.ui.activities.Profile;

import android.graphics.Bitmap;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface ProfileContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

        void callCloudVision(Bitmap bitmap, Feature feature,AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeProgress);
        void setupNetworkCall(Bitmap bitmap, Feature feature);

    }

    interface View {

        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);


        void setUserName(String userName);

        void setPhoneNumber(String phoneNumber);

        void setEmailId(String emailId);
        void setWebsite(String website);


    }

}
