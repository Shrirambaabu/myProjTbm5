package com.forzo.holdMyCard.ui.fragments.mylogin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.User;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyLoginPresenter extends BasePresenter<MyLoginContract.View> implements MyLoginContract.Presenter {

    private static final String TAG = "MyCurrentLibraryFragmen";
    private Context context;
    private ApiService mApiService;

    MyLoginPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void loginToHmc(String emailEditText, String passwordEditText) {

        Log.e("Email", "" + emailEditText);
        Log.e("Password", "" + passwordEditText);
        mApiService.loginToApp(emailEditText, passwordEditText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getView().showProgressBar();
                    }

                    @Override
                    public void onNext(User user1) {

                        Log.e("Result", "" + user1.getNewUser());
                        Log.e("Result", "" + user1.getUserName());
                        Log.e("Result", "" + user1.getUserEmail());
                        Log.e("Result", "" + user1.getUserPassword());
                        PreferencesAppHelper.setUserId(user1.getNewUser());
                        //getView().hideProgressBar();
                        getView().loginSuccess();
                        Log.e("error", "HMCnext");
                        Log.e("HMCLogin", "" + PreferencesAppHelper.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                        getView().hideProgressBar();
                        Log.e("error", "HMCerror");
                    }

                    @Override
                    public void onComplete() {
                        getView().hideProgressBar();
                        Log.e("error", "HMCcomplete");
                    }
                });
    }
}
