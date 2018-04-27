package com.forzo.holdMyCard.ui.activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface ProfileContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

        void callCloudVision(Bitmap bitmap, Feature feature, AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeProgress);

        void saveImage(File file,String s);
        void getIntentValues(Intent intent, RelativeLayout cardLayout);
        void saveBusinessCard( TextInputEditText nameTextInputEditText, TextInputEditText companyTextInputEditText, TextInputEditText jobTitleTextInputEditText, TextInputEditText mobileTextInputEditText, TextInputEditText emailTextInputEditText, TextInputEditText websiteTextInputEditText,TextInputEditText addressTextInputEditText,AVLoadingIndicatorView avLoadingIndicatorView,RelativeLayout relativeLayout);
    }

    interface View {

        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);


        void newContact();
        void setUserName(String userName);

        void setPhoneNumber(String phoneNumber);

        void setEmailId(String emailId);

        void setWebsite(String website);

        void setProfileImage(Bitmap profileImage);
        void setCompanyName(String companyName);

        void setJobTitle(String jobTitle);

        void setAddress(String address);

        void savedSuccessfully(String s);

        void saveImageFile(File imageFile);

        void setLibraryImage(String image);
        void profileSavedSuccessfully();
        void setDialog();
        void setSaveFalse(Boolean saveFalse);
        void setUserPrimaryValue(String  userPrimaryValue);

    }

}
