package com.forzo.holdMyCard.ui.activities.userprofile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.jackandphantom.circularimageview.CircleImage;
import com.wang.avi.AVLoadingIndicatorView;


import java.io.File;
import java.util.List;

import javax.inject.Inject;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class UserProfileActivity extends AppCompatActivity implements UserProfileContract.View {


    @BindView(R.id.edit_profile_image)
    CircleButton editProfileImage;
    @BindView(R.id.edit_profile)
    CircleButton editButton;
    @BindView(R.id.circleImage)
    CircleImage circleImage;
    @BindView(R.id.profile_library_image)
    ImageView imageView;
    @BindView(R.id.update_user_profile)
    Button updateButton;

    @BindView(R.id.textInputEditTextName)
    TextInputEditText textInputEditTextName;
    @BindView(R.id.textInputEditTextJobTitle)
    TextInputEditText textInputEditTextJobTitle;
    @BindView(R.id.textInputEditTextCompanyName)
    TextInputEditText textInputEditTextCompanyName;
    @BindView(R.id.textInputEditTextPhonePrimary)
    TextInputEditText textInputEditTextMobile;
    @BindView(R.id.textInputEditTextPhoneSecondary)
    TextInputEditText textInputEditTextMobile2;/*
    @BindView(R.id.textInputEditTextMobile3)
    TextInputEditText textInputEditTextMobile3;*/
    @BindView(R.id.textInputEditTextAddress)
    TextInputEditText textInputEditTextAddress;
    @BindView(R.id.textInputEditTextWebsite)
    TextInputEditText textInputEditTextWebsite;
    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText textInputEditTextEmail;
    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @Inject
    UserProfilePresenter userProfilePresenter;
    private Context mContext = UserProfileActivity.this;

    private File businessFile = null;
    private File profileImageFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        ButterKnife.bind(this);
        backButtonOnToolbar(UserProfileActivity.this);
        DaggerUserProfileComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);
        userProfilePresenter.attach(this);
        userProfilePresenter.getIntentValues(getIntent());
        // ImagePicker.setMinQuality(600, 600);
    }

    @OnClick(R.id.edit_profile_image)
    public void imageSelect() {
        //  ImagePicker.pickImage(this, "Select your image:");
        EasyImage.openChooserWithGallery(UserProfileActivity.this, "Select the image", 0);
    }

    @OnClick(R.id.edit_profile)
    public void profileImageSelect() {
        //  ImagePicker.pickImage(this, "Select your image:");
        EasyImage.openChooserWithGallery(UserProfileActivity.this, "Select the image", 1);
    }

    @OnClick(R.id.update_user_profile)
    public void updateUserProfile() {

        if (businessFile != null) {
            userProfilePresenter.updateUserBusinessImage(businessFile,"BCF");
        }

        if (profileImageFile!=null){
            userProfilePresenter.updateUserBusinessImage(profileImageFile,"DP");
        }

        userProfilePresenter.updateUserProfile(textInputEditTextName.getText().toString(), textInputEditTextJobTitle.getText().toString(), textInputEditTextCompanyName.getText().toString(), textInputEditTextMobile.getText().toString(), textInputEditTextMobile2.getText().toString(), "", textInputEditTextEmail.getText().toString(), textInputEditTextAddress.getText().toString(), textInputEditTextWebsite.getText().toString());

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(UserProfileActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     /*   Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        // TODO do something with the bitmap

        circleImage.setImageBitmap(bitmap);*/

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Log.e("type", "" + type);
                onPhotosReturned(imageFile, type);
            }

        });
    }

    private void onPhotosReturned(File imageFile, int type) {
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        if (type == 0) {
            profileImageFile = imageFile;
            circleImage.setImageBitmap(bitmap);
        } else if (type == 1) {
            businessFile = imageFile;
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void setUserProfileName(String userProfileName) {
        textInputEditTextName.setText(userProfileName);
    }

    @Override
    public void setUserProfileJob(String job) {
        textInputEditTextJobTitle.setText(job);
    }

    @Override
    public void setUserProfileCompanyName(String companyName) {
        textInputEditTextCompanyName.setText(companyName);
    }

    @Override
    public void setUserProfilePhonePrimary(String phonePrimary) {
        textInputEditTextMobile.setText(phonePrimary);
    }

    @Override
    public void setUserProfilePhoneSecondary(String phoneSecondary) {
        textInputEditTextMobile2.setText(phoneSecondary);
    }

    @Override
    public void setUserProfilePhone3(String profilePhone3) {
        //  textInputEditTextMobile3.setText(profilePhone3);
    }

    @Override
    public void setUserProfileEmail(String userProfileEmail) {
        textInputEditTextEmail.setText(userProfileEmail);
    }

    @Override
    public void setUserProfileWebsite(String profileWebsite) {
        textInputEditTextWebsite.setText(profileWebsite);
    }

    @Override
    public void setUserProfileAddress(String profileAddress) {
        textInputEditTextAddress.setText(profileAddress);
    }

    @Override
    public void activityLoader() {
        relativeLayout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();
    }

    @Override
    public void hideLoader() {
        relativeLayout.setVisibility(View.GONE);
        avLoadingIndicatorView.setVisibility(View.GONE);
        avLoadingIndicatorView.hide();
    }

    @Override
    public void updateSuccess() {

        Intent myLibrary = new Intent(UserProfileActivity.this, MyLibraryActivity.class);
        myLibrary.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myLibrary);

    }

    @Override
    public void setBackGroundImage(String backGroundImage) {

        Glide.with(mContext)
                .load(IMAGE_URL + backGroundImage)
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public void setDpImage(String dpImage) {
        Glide.with(mContext)
                .load(IMAGE_URL + dpImage)
                .thumbnail(0.1f)
                .into(circleImage);
    }
}
