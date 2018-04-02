package com.forzo.holdMyCard.ui.activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.home.HomeActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;

public class ProfileActivity extends AppCompatActivity implements  ProfileContract.View {


    private static final int ACTIVITY_NUM = 2;


    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @Inject
    ProfilePresenter profilePresenter;

    private Context mContext = ProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        DaggerProfileComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        profilePresenter.attach(this);

        profilePresenter.bottomNavigationViewSetup(bottomNavigationViewEx);

        Bitmap bmp = getIntent().getParcelableExtra("image");

        TextView cancelText = findViewById(R.id.cancel_text);
        TextView saveText = findViewById(R.id.save_text);
        ImageView imageView = findViewById(R.id.image_view);

        if (bmp != null) {
            imageView.setImageBitmap(bmp);
        } else {
            imageView.setImageResource(R.drawable.business_card);
        }
        //  imageView.setImageResource(R.drawable.business_card);



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profilePresenter.detach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @OnClick(R.id.save_text)
    public void saveToast() {
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        Intent intentSave = new Intent(ProfileActivity.this, HomeActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.cancel_text)
    public void cancelToast() {
        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }




    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {

        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
