package com.forzo.holdMyCard.ui.activities.personalizedqr;

import com.forzo.holdMyCard.base.BaseMvpPresenter;

public interface QRContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void getPersonalDetails();

    }

    interface View {

        void userDetails(String userName, String jobName, String companyName, String phoneNumberProfile, String phoneNumberProfile2, String phoneNumberProfile3, String emailIdProfile, String websiteProfile, String addressProfile);
    }
}
