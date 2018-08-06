package com.forzo.holdMyCard.ui.fragments.mylogin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.ui.activities.forgetpassword.ForgetPasswordActivity;
import com.forzo.holdMyCard.ui.activities.login.LoginActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.newLogin.NewLoginActivity;
import com.forzo.holdMyCard.ui.models.User;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MyLogin extends Fragment implements MyLoginContract.View {

    private String TAG = "Login";
    private String userId;
    private String firstName, lastName, email, birthday, gender;
    private URL profilePicture;


    @Inject
    MyLoginPresenter myLoginPresenter;
    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText textInputEditTextEmail;
    @BindView(R.id.textInputEditTextPassword)
    TextInputEditText textInputEditTextPassword;

    @BindView(R.id.relative_progress)
    RelativeLayout relativeProgress;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;
    private Context context;
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    @BindView(R.id.fb_button)
    LoginButton loginButton;
    CallbackManager callbackManager;
    private ApiService mApiService;

    public MyLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_login, container, false);
        ButterKnife.bind(MyLogin.this, view);
        myLoginPresenter.attach(this);
        callbackManager = CallbackManager.Factory.create();
        mApiService = ApiFactory.create(HmcApplication.get(Objects.requireNonNull(getActivity())).getRetrofit());
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
                                                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                                                Intent libraryIntent = new Intent(getActivity(), MyLibraryActivity.class);
                                                                libraryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(libraryIntent);
                                                                PreferencesAppHelper.setUserStatus("1");
                                                                Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                                Intent libraryIntent = new Intent(getActivity(), MyLibraryActivity.class);
                                                libraryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(libraryIntent);
                                                PreferencesAppHelper.setUserStatus("1");
                                                Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                Toast.makeText(getActivity(), "Login Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), "Login Error", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.forget_pass)
    public void forgotPassSection() {

        Intent intent = new Intent(getActivity(), ForgetPasswordActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.login_button)
    public void loginButton() {

        if (textInputEditTextEmail.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter the Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (textInputEditTextPassword.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter the Password", Toast.LENGTH_LONG).show();
            return;
        }
        myLoginPresenter.loginToHmc(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        DaggerMyLoginComponent.builder()
                .fragmentContext(new FragmentContext(context))
                .build()
                .inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myLoginPresenter.detach();
    }


    @Override
    public void showProgressBar() {
        relativeProgress.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();
    }

    @Override
    public void hideProgressBar() {
        relativeProgress.setVisibility(View.GONE);
        avLoadingIndicatorView.setVisibility(View.GONE);
        avLoadingIndicatorView.hide();
    }

    @Override
    public void loginSuccess() {

        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

        Intent libraryIntent = new Intent(getActivity(), MyLibraryActivity.class);
        libraryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(libraryIntent);
        PreferencesAppHelper.setUserStatus("1");
        Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
