package com.forzo.holdMyCard.ui.activities.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.BaseView;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.utils.CameraUtils;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, BaseView {

    private static final int ACTIVITY_NUM = 0;
    private static final String TAG = "HomeActivity";
    private static Uri capturedImageUri = null;
    private static Uri intentUri = null;
    private static int PERMISSION_REQUEST_CODE = 1;
    private final int requestCode = 20;
    File image = null;
    @Inject
    HomePresenter homePresenter;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.emul_button)
    Button buttonEmulator;
    @BindView(R.id.new_contact_button)
    Button newContact;
    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;
    @BindView(R.id.relative_main)
    RelativeLayout relativeLayoutMain;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.version_number)
    TextView versionNumberTextView;
    private Context mContext = HomeActivity.this;
    private Feature feature;
    private String[] visionAPI = new String[]{"TEXT_DETECTION", "LOGO_DETECTION"};
    private String[] permissionList = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerHomeComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        homePresenter.attach(this);
        homePresenter.bottomNavigationViewSetup(bottomNavigationViewEx);
        homePresenter.checkPermission(permissionList, PERMISSION_REQUEST_CODE);
        homePresenter.checkVersion();

        feature = new Feature();
        feature.setType(visionAPI[0]);
        feature.setMaxResults(15);
    }

    @Override
    public void setVersionNumber(String versionNumber) {
        versionNumberTextView.setText(String.format("%s %s", getString(R.string.version), versionNumber));
    }

    @OnClick(R.id.new_contact_button)
    public void newContact() {
        String imageType = "BCF";
        Log.e("img", "" + imageType.replaceAll("^\"|\"$", ""));
       /* Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("newContact","new");
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/
    }

    @OnClick(R.id.button)
    public void captureImage() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (!homePresenter.checkPermission(permissionList)) {
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Start intent with Action_Image_Capture
                capturedImageUri = CameraUtils.getOutputMediaFileUri(this); //get fileUri from CameraUtils
                intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri); //Send fileUri with intent
                startActivityForResult(intent, requestCode); //start activity for result with CAMERA_REQUEST_CODE
            }
        } else {
            homePresenter.checkPermission(permissionList, PERMISSION_REQUEST_CODE);
        }
    }

//    @OnClick(R.id.emul_button)
//    public void emulator() {
//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.abc_d);
//        avLoadingIndicatorView.setVisibility(View.VISIBLE);
//        avLoadingIndicatorView.smoothToShow();
//        relativeLayout.setVisibility(View.VISIBLE);
//        relativeLayoutMain.setVisibility(View.GONE);
//        homePresenter.callVisionApi(HomeActivity.this, b, feature, capturedImageUri, avLoadingIndicatorView, relativeLayout, relativeLayoutMain, image);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        homePresenter.handleResult(requestCode, resultCode, data, capturedImageUri);
    }

    @Override
    public void onPhotosReturned(Uri photoUri) {
        CropImage.activity(photoUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(16,9)
                .setActivityMenuIconColor(Color.WHITE)
                .setAllowRotation(true)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .setOutputCompressQuality(100)
                .setAutoZoomEnabled(true)
                .setActivityTitle("Crop Image")
                .start(this);
    }

    @Override
    public void showDialog() {
        avLoadingIndicatorView.smoothToShow();
        relativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        avLoadingIndicatorView.smoothToHide();
        relativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void sendCroppedImage(Uri resultUri) {
//        Intent intent = new Intent(mContext, ProfileActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("intentUri", resultUri.toString());
//        mContext.startActivity(intent);
        homePresenter.callGoogleCloudVision(resultUri, feature, avLoadingIndicatorView, resultUri, relativeLayout, relativeLayoutMain);
    }

    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
