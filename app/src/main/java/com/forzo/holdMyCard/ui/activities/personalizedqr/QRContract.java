package com.forzo.holdMyCard.ui.activities.personalizedqr;

import com.forzo.holdMyCard.base.BaseMvpPresenter;

public interface QRContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void getPersonalDetails();
        void getBackgroundImage();
        void getProfileImage();

    }

    interface View {

        void setBackGroundImage(String backGroundImage);
        void setDpImage(String dpImage);
        void userDetails(String userName, String jobName, String companyName, String phoneNumberProfile, String phoneNumberProfile2, String phoneNumberProfile3, String emailIdProfile, String websiteProfile, String addressProfile);
    }
}
