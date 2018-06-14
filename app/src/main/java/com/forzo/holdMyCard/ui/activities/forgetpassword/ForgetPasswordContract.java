package com.forzo.holdMyCard.ui.activities.forgetpassword;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupContract;

public interface ForgetPasswordContract {
    interface Presenter extends BaseMvpPresenter<ForgetPasswordContract.View> {

        void checkForgetPassword(String email);

    }

    // Action callbacks. Activity/Fragment will implement
    interface View {


    }
}
