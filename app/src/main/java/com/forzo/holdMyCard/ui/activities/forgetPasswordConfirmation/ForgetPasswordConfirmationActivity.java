package com.forzo.holdMyCard.ui.activities.forgetPasswordConfirmation;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.loginregister.LoginRegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordConfirmationActivity extends AppCompatActivity {


    @BindView(R.id.tick_mark)
    ImageView tickImage;
    @BindView(R.id.login_password)
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_confirmation);
        ButterKnife.bind(this);
        tickImage.setColorFilter(getApplicationContext().getResources().getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.login_password)
    public void loginButton(){
        Intent intent=new Intent(ForgetPasswordConfirmationActivity.this, LoginRegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
