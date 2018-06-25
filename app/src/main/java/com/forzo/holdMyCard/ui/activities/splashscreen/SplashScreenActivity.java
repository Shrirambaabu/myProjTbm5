package com.forzo.holdMyCard.ui.activities.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent loginIntent = new Intent(SplashScreenActivity.this, MyLibraryActivity.class);
                    startActivity(loginIntent);

                // close this activity
                finish();
            }
        }, 2000);

    }
}
