package com.forzo.holdMyCard.ui.activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.widget.EditText;
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

        void showProfileData(String profileData);

        void naturalProcess(String result);

        void addToCalendar(String email);

        void saveContactToPhone(EditText name, EditText mobileNumber, EditText emailText, EditText companyEditText, EditText jobEditText, EditText addressEditText);

        void saveImage(File file, String s);

        void getIntentValues(Intent intent, String emailTextInputEditText, String companyTextInputEditText, String nameTextInputEditText);

        void callWatsonApi(String send, String emailTextInputEditText, String companyTextInputEditText, String nameTextInputEditText);

        void saveBusinessCard(String nameTextInputEditText, String companyTextInputEditText, String jobTitleTextInputEditText, String mobileTextInputEditText, String emailTextInputEditText, String websiteTextInputEditText, String addressTextInputEditText);

        void updateCard(String userId, String nameTextInputEditText, String companyTextInputEditText, String jobTitleTextInputEditText, String mobileTextInputEditText, String emailTextInputEditText, String websiteTextInputEditText, String addressTextInputEditText);

        void deleteCard(String userId);

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

        void saveImageFile(File imageFile, Uri imageUri);

        void setLibraryImage(String image);

        void profileSavedSuccessfully();

        void setDialog();

        void updateSuccess();

        void deleteProfile();

        void setSaveFalse(Boolean saveFalse);

        void setUserPrimaryValue(String userPrimaryValue);

        void setProfileImageUri(Uri profileImageUri);

        void activityLoader(String loader);
    }

}
