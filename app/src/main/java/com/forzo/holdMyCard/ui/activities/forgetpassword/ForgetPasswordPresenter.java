package com.forzo.holdMyCard.ui.activities.forgetpassword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.activities.forgetPasswordConfirmation.ForgetPasswordConfirmationActivity;
import com.forzo.holdMyCard.ui.models.User;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.View> implements ForgetPasswordContract.Presenter {


    private Context context;
    private static final String TAG = "CreateGroupPresenter";
    private ApiService mApiService;

    ForgetPasswordPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void checkForgetPassword(String email) {


        mApiService.forgetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user1) {

                        Log.e("forgetPass", "" + user1.getForgetPassword());
                        Log.e("forgetPass", "next");
                        Intent intent = new Intent(context, ForgetPasswordConfirmationActivity.class);
                        context.startActivity(intent);

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
