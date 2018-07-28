package com.forzo.holdMyCard.ui.fragments.myregister;

import com.forzo.holdMyCard.base.BaseMvpPresenter;


public interface MyRegisterContract {

    interface Presenter extends BaseMvpPresenter<MyRegisterContract.View> {

        void registerToHmc(String name, String email, String password, String confirmPassword);

        void regSuccess();

        void emailAlreadyRegistered();

        void checkExistingUser(String email);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void showLoader();

        void hideLoader();

        void loginSuccess();

        void existingUserStatus(String status);

    }
}
