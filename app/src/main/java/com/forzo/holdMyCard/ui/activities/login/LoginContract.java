package com.forzo.holdMyCard.ui.activities.login;

import android.content.Intent;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Shriram on 5/7/2018.
 */

public interface LoginContract {


    interface Presenter extends BaseMvpPresenter<LoginContract.View> {

        void loginToHmc(EditText emailText, EditText passwordText, RelativeLayout relativeLayout, AVLoadingIndicatorView avLoadingIndicatorView);
        void registerToHmc(EditText userName,EditText userEmail,EditText userPassword,EditText userConformPassword, RelativeLayout relativeLayout, AVLoadingIndicatorView avLoadingIndicatorView);
    }

    interface View {


    }

}
