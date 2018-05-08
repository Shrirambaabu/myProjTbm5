package com.forzo.holdMyCard.ui.activities.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.forgetpassword.ForgetPasswordActivity;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {


    @BindView(R.id.login_section)
    TextView loginSection;

    @BindView(R.id.register_button)
    TextView registerSection;

    @BindView(R.id.forget_pass)
    TextView forgotPass;

    @BindView(R.id.email_id)
    EditText emailLogin;
    @BindView(R.id.password)
    EditText passwordLogin;
    @BindView(R.id.name)
    EditText userName;
    @BindView(R.id.email)
    EditText userEmail;
    @BindView(R.id.register_password)
    EditText userPassword;
    @BindView(R.id.conform_password)
    EditText userConformPassword;

    @BindView(R.id.login_content)
    RelativeLayout loginContent;

    @BindView(R.id.register_content)
    RelativeLayout registerContent;

    @BindView(R.id.relative_progress)
    RelativeLayout relativeProgress;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.register_side_button)
    Button registerButton;

    @Inject
    LoginPresenter loginPresenter;

    private Context mContext = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DaggerLoginComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        loginPresenter.attach(this);


    }

    @OnClick(R.id.login_section)
    public void loginSection() {
        loginSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
        registerSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorFadeLogin));

        loginContent.setVisibility(View.VISIBLE);
        registerContent.setVisibility(View.GONE);

    }

    @OnClick(R.id.login_button)
    public void loginButton() {

        if (emailLogin.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (passwordLogin.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the Password", Toast.LENGTH_LONG).show();
            return;
        }
        loginPresenter.loginToHmc(emailLogin, passwordLogin,relativeProgress,avLoadingIndicatorView);

    }

    @OnClick(R.id.register_side_button)
    public void registerButton() {


        if (userName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the Name", Toast.LENGTH_LONG).show();
            return;
        }

        if (userEmail.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the Email", Toast.LENGTH_LONG).show();
            return;
        }

        if (userPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (userConformPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter the Conform Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (!userPassword.getText().toString().equals(""+userConformPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Password and Conform Password Mismatch", Toast.LENGTH_LONG).show();
            return;
        }

        loginPresenter.registerToHmc(userName, userEmail,userPassword,userConformPassword,relativeProgress,avLoadingIndicatorView);
    }

    @OnClick(R.id.forget_pass)
    public void forgotPassSection() {

        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }

    @OnClick(R.id.register_button)
    public void registerSection() {
        registerSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
        loginSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorFadeLogin));

        registerContent.setVisibility(View.VISIBLE);
        loginContent.setVisibility(View.GONE);

    }


}
