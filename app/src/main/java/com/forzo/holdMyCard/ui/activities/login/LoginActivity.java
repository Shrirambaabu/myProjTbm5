package com.forzo.holdMyCard.ui.activities.login;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_section)
    TextView loginSection;

    @BindView(R.id.register_button)
    TextView registerSection;

    @BindView(R.id.login_content)
    RelativeLayout loginContent;

    @BindView(R.id.register_content)
    RelativeLayout registerContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.login_section)
    public void loginSection() {
        loginSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
        registerSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorFadeLogin));

        loginContent.setVisibility(View.VISIBLE);
        registerContent.setVisibility(View.GONE);

    }

    @OnClick(R.id.register_button)
    public void registerSection() {
        registerSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary));
        loginSection.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorFadeLogin));

        registerContent.setVisibility(View.VISIBLE);
        loginContent.setVisibility(View.GONE);

    }


}
