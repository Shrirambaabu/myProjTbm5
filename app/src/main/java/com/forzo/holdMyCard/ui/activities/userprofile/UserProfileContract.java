package com.forzo.holdMyCard.ui.activities.userprofile;


import android.content.Intent;

import com.forzo.holdMyCard.base.BaseMvpPresenter;

import java.io.File;

public interface UserProfileContract {

    interface Presenter extends BaseMvpPresenter<UserProfileContract.View> {
        void getIntentValues(Intent intent);

        void postUserBusinessImage(File businessImage, String imageType);

        void updateUserBusinessImage(File businessImage, String imageType);

        void updateUserProfile(String name, String jobTitle, String companyName, String phoneOne, String phone2, String phone3, String email, String address, String website);
    }

    interface View {
        void setUserProfileName(String userProfileName);

        void setUserProfileJob(String job);

        void setUserProfileCompanyName(String companyName);

        void setUserProfilePhonePrimary(String phonePrimary);

        void setUserProfilePhoneSecondary(String phoneSecondary);

        void setUserProfilePhone3(String profilePhone3);

        void setUserProfileEmail(String userProfileEmail);

        void setUserProfileWebsite(String profileWebsite);

        void setUserProfileAddress(String profileAddress);

        void activityLoader();

        void hideLoader();

        void updateSuccess();

        void setBackGroundImage(String backGroundImage);

        void setDpImage(String dpImage);
    }
}
