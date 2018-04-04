package com.forzo.holdMyCard.ui.activities.forgetpassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class ForgetPasswordActivity extends AppCompatActivity {


    @BindView(R.id.email_forget)
    EditText emailForget;

    @BindView(R.id.submit_button)
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        backButtonOnToolbar(ForgetPasswordActivity.this);

    }

    @OnClick(R.id.submit_button)
    public void submitSection() {
        emailForget.setText("");
        Toast.makeText(getApplicationContext(),"Check Your Mail",Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

}
