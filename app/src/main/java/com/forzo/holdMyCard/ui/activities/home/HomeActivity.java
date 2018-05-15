package com.forzo.holdMyCard.ui.activities.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.BuildConfig;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.BaseView;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.utils.CameraUtils;
import com.forzo.holdMyCard.utils.ImagePath_MarshMallow;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;
import static com.forzo.holdMyCard.utils.Utils.getImageEncodeImage;
import static com.forzo.holdMyCard.utils.Utils.getResizedBitmap;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, BaseView {

    private final int requestCode = 20;
    private Context mContext = HomeActivity.this;
    private static final int ACTIVITY_NUM = 0;
    public static final String OPEN_CAMERA_OR_GALLERY_TO_CHOOSE_AN_IMAGE = "Open Camera or Gallery to choose an Image";
    public static final int TYPE = 1001;
    private static final String TAG = "HomeActivity";
    static Uri capturedImageUri = null;
    private static Uri intentUri = null;
    private Bitmap bitmap;

    File image = null;
    private Feature feature;
    private String[] visionAPI = new String[]{"TEXT_DETECTION"};

    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 103;
    private static final int READ_STORAGE_REQUEST_CODE = 104;

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
    TextView versionNumber;

    private String getImageUrl = "";

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

        feature = new Feature();
        feature.setType(visionAPI[0]);
        feature.setMaxResults(10);


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            Log.e("versionName", "" + version);
            versionNumber.setText("Version: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

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
        // EasyImage.openChooserWithGallery(HomeActivity.this, OPEN_CAMERA_OR_GALLERY_TO_CHOOSE_AN_IMAGE, TYPE);
       /* Calendar cal = Calendar.getInstance();
        // fetching the root directory
        String root = Environment.getExternalStorageDirectory().toString()
                + "/HMC";

        // Creating folders for Image
        String imageFolderPath = root + "/saved_images";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();
        // Generating file name
        String imageName = cal.getTimeInMillis() + ""+ PreferencesAppHelper.getUserId() + ".png";
        // Creating image here
        image = new File(imageFolderPath, imageName);

        // capturedImageUri = Uri.fromFile(image);
        capturedImageUri = FileProvider.getUriForFile(HomeActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                image);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);

        startActivityForResult(takePictureIntent, requestCode);*/


        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Start intent with Action_Image_Capture
            capturedImageUri = CameraUtils.getOutputMediaFileUri(this); //get fileUri from CameraUtils
            intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri); //Send fileUri with intent
            startActivityForResult(intent, requestCode); //start activity for result with CAMERA_REQUEST_CODE
        }

    }

    @OnClick(R.id.emul_button)
    public void emulator() {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.abc_d);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayoutMain.setVisibility(View.GONE);
        homePresenter.callVisionApi(HomeActivity.this, b, feature, capturedImageUri, avLoadingIndicatorView, relativeLayout, relativeLayoutMain, image);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (this.requestCode == requestCode && resultCode == RESULT_OK) {

          /*  avLoadingIndicatorView.setVisibility(View.VISIBLE);
            avLoadingIndicatorView.show();
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayoutMain.setVisibility(View.GONE);
            Log.e("HM", "Calling Vision");
            homePresenter.callVisionApi(HomeActivity.this, bitmap, feature, capturedImageUri, avLoadingIndicatorView, relativeLayout, relativeLayoutMain, image);

*/

            try {
                if (Build.VERSION.SDK_INT > 22)
                    getImageUrl = ImagePath_MarshMallow.getPath(HomeActivity.this, capturedImageUri);
                else
                    getImageUrl = capturedImageUri.getPath();
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
            onPhotosReturned(Uri.parse("file://" + getImageUrl));

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.e(TAG, "onActivityResult: here");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                intentUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    homePresenter.callGoogleCloudVision(resultUri, feature, avLoadingIndicatorView, intentUri, relativeLayout, relativeLayoutMain);
                } catch (IOException e) {
                    Log.e(TAG, "onActivityResult: " + e.getMessage());
                }
               /* Glide.with(this)
                        .load(resultUri)
                        .into(imageView);*/

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e(TAG, "onActivityResult: ", result.getError());
            }
        }
    }


    private void onPhotosReturned(Uri imageFile) {
        Log.e(TAG, "onPhotosReturned: " + imageFile.getPath());
        CropImage.activity(imageFile)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setActivityMenuIconColor(Color.WHITE)
                .setAllowRotation(true)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .setOutputCompressQuality(100)
                .setAutoZoomEnabled(true)
                .setActivityTitle("Crop Image")
                .start(this);

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

        if (checkPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {
            makeRequest(Manifest.permission.CAMERA);

        }
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            makeStorageRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            makeReadStorageRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void makeReadStorageRequest(String readExternalStorage) {
        ActivityCompat.requestPermissions(this, new String[]{readExternalStorage}, READ_STORAGE_REQUEST_CODE);
    }

    private void makeStorageRequest(String writeExternalStorage) {
        ActivityCompat.requestPermissions(this, new String[]{writeExternalStorage}, STORAGE_REQUEST_CODE);
    }

    private int checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission);
    }

    private void makeRequest(String permission) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, RECORD_REQUEST_CODE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
