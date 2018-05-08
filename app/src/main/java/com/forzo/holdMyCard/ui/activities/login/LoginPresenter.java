package com.forzo.holdMyCard.ui.activities.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.User;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.wang.avi.AVLoadingIndicatorView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shriram on 5/7/2018.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    private Context context;
    private ApiService mApiService;

    LoginPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void loginToHmc(EditText emailText, EditText passwordText, RelativeLayout relativeLayout, AVLoadingIndicatorView avLoadingIndicatorView) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        mApiService.loginToHmc(PreferencesAppHelper.getUserId(),email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        relativeLayout.setVisibility(View.VISIBLE);
                        avLoadingIndicatorView.setVisibility(View.VISIBLE);
                        avLoadingIndicatorView.show();
                        // progressBar.smoothToShow();
                    }

                    @Override
                    public void onNext(User user1) {

                        Log.e("Result",""+user1.getNewUser());
                        Log.e("Result",""+user1.getUserName());
                        Log.e("Result",""+user1.getUserEmail());
                        Log.e("Result",""+user1.getUserPassword());

                        relativeLayout.setVisibility(View.GONE);
                        avLoadingIndicatorView.setVisibility(View.GONE);
                        avLoadingIndicatorView.hide();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                        //  progressBar.smoothToHide();

                        relativeLayout.setVisibility(View.GONE);
                        avLoadingIndicatorView.setVisibility(View.GONE);
                        avLoadingIndicatorView.hide();

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }

    @Override
    public void registerToHmc(EditText userName, EditText userEmail, EditText userPassword, EditText userConformPassword, RelativeLayout relativeLayout, AVLoadingIndicatorView avLoadingIndicatorView) {

        String registerUserName = userName.getText().toString();
        String registerUserEmail = userEmail.getText().toString();
        String registerUserPassword = userPassword.getText().toString();
        String registerUserConformPassword = userConformPassword.getText().toString();

        Log.e("RegUserID",""+PreferencesAppHelper.getUserId());
        Log.e("RegName",""+registerUserName);
        Log.e("RegEmail",""+registerUserEmail);
        Log.e("RegPass",""+registerUserPassword);

        User user = new User();

        user.setNewUser(PreferencesAppHelper.getUserId());
        user.setUserName(registerUserName);
        user.setUserEmail(registerUserEmail);
        user.setUserPassword(registerUserPassword);

        mApiService.registerToHmc(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        relativeLayout.setVisibility(View.VISIBLE);
                        avLoadingIndicatorView.setVisibility(View.VISIBLE);
                        avLoadingIndicatorView.show();
                    }

                    @Override
                    public void onNext(User user1) {

                        Log.e("Result",""+user1.getRegStatus());

                        relativeLayout.setVisibility(View.GONE);
                        avLoadingIndicatorView.setVisibility(View.GONE);
                        avLoadingIndicatorView.hide();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                        //  progressBar.smoothToHide();

                        relativeLayout.setVisibility(View.GONE);
                        avLoadingIndicatorView.setVisibility(View.GONE);
                        avLoadingIndicatorView.hide();

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
