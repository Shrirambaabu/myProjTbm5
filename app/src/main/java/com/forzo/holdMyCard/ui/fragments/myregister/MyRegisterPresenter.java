package com.forzo.holdMyCard.ui.fragments.myregister;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.User;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyRegisterPresenter extends BasePresenter<MyRegisterContract.View> implements MyRegisterContract.Presenter {


    private static final String TAG = "MyCurrentLibraryFragmen";
    private Context context;
    private ApiService mApiService;

    MyRegisterPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void registerToHmc(String name, String email, String password, String confirmPassword) {

        Log.e("RegUserID", "" + PreferencesAppHelper.getUserId());
        Log.e("RegName", "" + name);
        Log.e("RegEmail", "" + email);
        Log.e("RegPass", "" + password);

        User user = new User();

        user.setNewUser(PreferencesAppHelper.getUserId());
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserPassword(password);

        mApiService.registerToHmc(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getView().showLoader();
                    }

                    @Override
                    public void onNext(User user1) {

                        Log.e("Result", "" + user1.getRegStatus());
                        getView().hideLoader();
                        if (user1.getRegStatus().equals("true")) {
                            getView().loginSuccess();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());

                        getView().hideLoader();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoader();
                    }
                });
    }

    @Override
    public void regSuccess() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Success !!!");
        alertDialog.setMessage("Check your mail, activate your account and login ");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();

                });
        alertDialog.show();
    }

    @Override
    public void checkExistingUser(String email) {

        mApiService.checkExistingUser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user1) {

                        Log.e("forgetPass", "" + user1.getExistingUser());
                        getView().existingUserStatus(user1.getExistingUser());
                        Log.e("forgetPass", "next");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                        Log.e("err", " errorMessage");

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
