package com.forzo.holdMyCard.ui.activities.newLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.models.CheckRegister;
import com.forzo.holdMyCard.ui.models.User;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewLoginActivity extends AppCompatActivity {
    private static final String TAG = "NewLoginActivity";
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    @BindView(R.id.login_button)
    LoginButton loginButton;
    CallbackManager callbackManager;
    private ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        mApiService = ApiFactory.create(HmcApplication.get(this).getRetrofit());
        loginButton.setReadPermissions(Arrays.asList(PUBLIC_PROFILE, EMAIL));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest data_request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), (object, response) -> {
                            String email = object.optString("email");
                            String name = object.optString("name");

                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(c);

                            mApiService.checkExistingUser(email)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<User>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(User CheckRegister) {
                                            if (CheckRegister.getUserId() == null) {
                                                User user = new User();
                                                user.setUserName(name);
                                                user.setUserEmail(email);
                                                user.setDate(formattedDate);

                                                mApiService.registerUsingFacebook(user)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Observer<User>() {
                                                            @Override
                                                            public void onSubscribe(Disposable d) {
                                                            }

                                                            @Override
                                                            public void onNext(User user1) {
                                                                PreferencesAppHelper.setUserId(user1.getUserId());
                                                                Toast.makeText(NewLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                                Intent libraryIntent = new Intent(NewLoginActivity.this, MyLibraryActivity.class);
                                                                libraryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(libraryIntent);
                                                                PreferencesAppHelper.setUserStatus("1");
                                                                Objects.requireNonNull(NewLoginActivity.this).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                            }

                                                            @Override
                                                            public void onError(Throwable e) {
                                                                Log.e("err", "" + e.getMessage());
                                                            }

                                                            @Override
                                                            public void onComplete() {
                                                            }
                                                        });
                                            }else{
                                                PreferencesAppHelper.setUserId(CheckRegister.getUserId());
                                                Toast.makeText(NewLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                Intent libraryIntent = new Intent(NewLoginActivity.this, MyLibraryActivity.class);
                                                libraryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(libraryIntent);
                                                PreferencesAppHelper.setUserStatus("1");
                                                Objects.requireNonNull(NewLoginActivity.this).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e(TAG, "onError: " + e);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });


                        });
                Bundle permission_param = new Bundle();
                permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
                data_request.setParameters(permission_param);
                data_request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(NewLoginActivity.this, "Login Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(NewLoginActivity.this, "Login Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
