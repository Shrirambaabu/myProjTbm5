package com.forzo.holdMyCard.ui.activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.notes.NotesActivity;
import com.forzo.holdMyCard.ui.activities.remainder.ReminderActivity;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;
import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {


    private static int ACTIVITY_NUM = 2;

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

    @BindView(R.id.textInputEditTextJobTitle)
    TextInputEditText jobTitleEditText;

    @BindView(R.id.textInputEditTextMobile)
    TextInputEditText mobileEditText;

    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText emailEditText;

    @BindView(R.id.textInputEditTextAddress)
    TextInputEditText addressEditText;

    @BindView(R.id.textInputEditTextWebsite)
    TextInputEditText websiteEditText;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.relative_progress)
    RelativeLayout relativeProgress;

    @BindView(R.id.card_action)
    RelativeLayout cardLayout;

    @BindView(R.id.action_card)
    RelativeLayout saveCancel;
    @BindView(R.id.update_card)
    RelativeLayout updateCard;

    private Context mContext = ProfileActivity.this;

    private String primaryValue = "";

    private String imageValue = "";

    private File file = null;

    private Uri imageUri = null;

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


        feature = new Feature();
        feature.setType(visionAPI[0]);
        feature.setMaxResults(10);


        profilePresenter.attach(this);
        profilePresenter.getIntentValues(getIntent(), cardLayout, emailEditText, companyNameEditText, nameEditText);
        profilePresenter.bottomNavigationViewSetup(bottomNavigationViewEx);


    }


    @OnClick(R.id.note_rel)
    public void noteSection() {

        Intent intent = new Intent(ProfileActivity.this, NotesActivity.class);
        intent.putExtra("libraryProfile", "" + primaryValue);
        intent.putExtra("libraryProfileImage", "" + imageValue);
        Log.e("NoteIntent", "" + primaryValue);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }

    @OnClick(R.id.remaindar_rel)
    public void remainderSection() {

        Intent intent = new Intent(ProfileActivity.this, ReminderActivity.class);
        intent.putExtra("libraryProfile", "" + primaryValue);
        intent.putExtra("libraryProfileImage", "" + imageValue);
        Log.e("ReminIntent", "" + primaryValue);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    }

    @OnClick(R.id.calendar_rel)
    public void calenderSection() {
        String email = emailEditText.getText().toString();

        if (isPackageInstalled("com.google.android.calendar", getApplicationContext())) {

            profilePresenter.addToCalendar(email);

        } else {
            Toast.makeText(getApplicationContext(), "Google Calendar not installed.", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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

        Intent intent = new Intent(ProfileActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {


        Intent intent = new Intent(ProfileActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @OnClick(R.id.save_text)
    public void saveToast() {

        profilePresenter.saveBusinessCard(nameEditText, companyNameEditText, jobTitleEditText, mobileEditText, emailEditText, websiteEditText, addressEditText, avLoadingIndicatorView, relativeProgress);

    }

    @OnClick(R.id.update_text)
    public void updateCardDetails() {
        profilePresenter.updateCard(primaryValue, nameEditText, companyNameEditText, jobTitleEditText, mobileEditText, emailEditText, websiteEditText, addressEditText, avLoadingIndicatorView, relativeProgress);

    }

    @OnClick(R.id.delete_text)
    public void deleteCard() {

        profilePresenter.deleteCard(primaryValue, avLoadingIndicatorView, relativeProgress);
    }

    @OnClick(R.id.new_contact_rel)
    public void saveContactToPhone() {

        profilePresenter.saveContactToPhone(nameEditText, mobileEditText, emailEditText, companyNameEditText, jobTitleEditText, addressEditText);

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


        if (file != null||imageUri!=null) {
            ACTIVITY_NUM = 1;
        }
        if (!primaryValue.equals("")||primaryValue!=null){
            ACTIVITY_NUM = 1;
        }


        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void newContact() {
        cardLayout.setVisibility(View.GONE);
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
        websiteEditText.setText(website);
    }

    @Override
    public void setProfileImage(Bitmap profileImage) {
        imageView.setImageBitmap(profileImage);
    }

    @Override
    public void setCompanyName(String companyName) {
        companyNameEditText.setText(companyName);
    }

    @Override
    public void setJobTitle(String jobTitle) {
        jobTitleEditText.setText(jobTitle);
    }

    @Override
    public void setAddress(String address) {
        addressEditText.setText(address);
    }

    @Override
    public void savedSuccessfully(String s) {
        profilePresenter.saveImage(file, s);


    }

    @Override
    public void saveImageFile(File imageFile, Uri uri) {
        file = imageFile;
        imageUri = uri;
    }

    @Override
    public void setLibraryImage(String image) {

        imageValue = image;
        Glide.with(getApplicationContext())
                .load(IMAGE_URL + image)
                .into(imageView);
    }

    @Override
    public void profileSavedSuccessfully() {
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        Intent intentSave = new Intent(ProfileActivity.this, MyLibraryActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void setDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        alertDialog.setTitle("Alert !!!");
        alertDialog.setMessage("Module disabled for Milestone 1 release");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent intentMain = new Intent(ProfileActivity.this, MyLibraryActivity.class);
                    intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentMain);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                });
        alertDialog.show();
    }

    @Override
    public void updateSuccess() {

        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();

        Intent intentSave = new Intent(ProfileActivity.this, MyLibraryActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void deleteProfile() {
        Toast.makeText(getApplicationContext(), "Profile Deleted", Toast.LENGTH_LONG).show();

        Intent intentSave = new Intent(ProfileActivity.this, MyLibraryActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void setSaveFalse(Boolean saveFalse) {

        if (!saveFalse) {
            saveCancel.setVisibility(View.GONE);
            cardLayout.setVisibility(View.GONE);
        } else {
            saveCancel.setVisibility(View.VISIBLE);
            cardLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setUserPrimaryValue(String userPrimaryValue) {
        primaryValue = userPrimaryValue;
        saveCancel.setVisibility(View.GONE);
        updateCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void setProfileImageUri(Uri profileImageUri) {

        Glide.with(this)
                .load(profileImageUri)
                .into(imageView);
    }
}
