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
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.ui.activities.forgetpassword.ForgetPasswordActivity;
import com.forzo.holdMyCard.ui.activities.login.LoginActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

        return view;
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
        myLoginPresenter.loginToHmc(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString());
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
        Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
