package com.forzo.holdMyCard.ui.activities.forgetpassword;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class ForgetPasswordActivity extends AppCompatActivity  implements ForgetPasswordContract.View {


    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText emailForget;

    @Inject
    ForgetPasswordPresenter forgetPasswordPresenter;
    @BindView(R.id.submit_button)
    Button submitButton;
    private Context mContext = ForgetPasswordActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        backButtonOnToolbar(ForgetPasswordActivity.this);
        DaggerForgetPasswordComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

    }

    @OnClick(R.id.submit_button)
    public void submitSection() {

        if (emailForget.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Enter your Email", Toast.LENGTH_LONG).show();
            return;
        }
        forgetPasswordPresenter.checkForgetPassword(emailForget.getText().toString());
        emailForget.setText("");


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

}
