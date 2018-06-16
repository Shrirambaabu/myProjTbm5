package com.forzo.holdMyCard.ui.fragments.mylogin;



import com.forzo.holdMyCard.base.BaseMvpPresenter;


public interface MyLoginContract {

    interface Presenter extends BaseMvpPresenter<MyLoginContract.View> {

        void loginToHmc(String emailEditText,String passwordEditText);
void errorDialog();
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {


        void showProgressBar();
        void hideProgressBar();
        void loginSuccess();

    }

}
