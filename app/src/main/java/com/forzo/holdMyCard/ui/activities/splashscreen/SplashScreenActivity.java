package com.forzo.holdMyCard.ui.activities.splashscreen;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.Database.DatabaseHelper;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreenActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mDatabaseHelper = new DatabaseHelper(this);
        new Handler().postDelayed(() -> {
            Intent loginIntent = new Intent(SplashScreenActivity.this, MyLibraryActivity.class);
            startActivity(loginIntent);
            finish();
        }, 2000);

        /*boolean insertData = mDatabaseHelper.addData("2","HH","DP");

        Log.e("Insert",""+insertData);*/

    }
}
