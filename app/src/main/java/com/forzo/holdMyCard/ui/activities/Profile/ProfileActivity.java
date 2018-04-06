package com.forzo.holdMyCard.ui.activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.home.HomeActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.notes.NotesActivity;
import com.forzo.holdMyCard.ui.activities.remainder.RemainderActivity;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;
import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {


    private static final int ACTIVITY_NUM = 2;

    private Feature feature;
    private String[] visionAPI = new String[]{"TEXT_DETECTION"};


    private String api = visionAPI[0];

    private Bitmap bitmap;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @Inject
    ProfilePresenter profilePresenter;


    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.calendar_rel)
    RelativeLayout calendarRelative;

    @BindView(R.id.remaindar_rel)
    RelativeLayout remainderRelative;

    @BindView(R.id.note_rel)
    RelativeLayout noteRelative;

    @BindView(R.id.textInputEditTextName)
    TextInputEditText nameEditText;

    @BindView(R.id.textInputEditTextCompanyName)
    TextInputEditText companyNameEditText;

    @BindView(R.id.textInputEditTextMobile)
    TextInputEditText mobileEditText;

    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText emailEditText;

    @BindView(R.id.textInputEditTextAddress)
    TextInputEditText addressEditText;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.relative_progress)
    RelativeLayout relativeProgress;


    private Context mContext = ProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);

        backButtonOnToolbar(ProfileActivity.this);

        DaggerProfileComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);


        bitmap = getIntent().getParcelableExtra("image");

        feature = new Feature();
        feature.setType(visionAPI[0]);
        feature.setMaxResults(10);


        profilePresenter.attach(this);

        profilePresenter.bottomNavigationViewSetup(bottomNavigationViewEx);


        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);

            profilePresenter.callCloudVision(bitmap, feature,avLoadingIndicatorView,relativeProgress);
            //profilePresenter.setupNetworkCall(bitmap, feature);

        } else {
            imageView.setImageResource(R.drawable.business_card);
        }


    }


    @OnClick(R.id.note_rel)
    public void noteSection() {

        Intent intent = new Intent(ProfileActivity.this, NotesActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }

    @OnClick(R.id.remaindar_rel)
    public void remainderSection() {

        Intent intent = new Intent(ProfileActivity.this, RemainderActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


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

        Intent intentSave = new Intent(ProfileActivity.this, MyLibraryActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.cancel_text)
    public void cancelToast() {
        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ProfileActivity.this, MyLibraryActivity.class);
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

    @Override
    public void setUserName(String userName) {

        nameEditText.setText(userName);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        mobileEditText.setText(phoneNumber);
    }

    @Override
    public void setEmailId(String emailId) {
        emailEditText.setText(emailId);
    }

    @Override
    public void setWebsite(String website) {
        companyNameEditText.setText(website);
    }
}
