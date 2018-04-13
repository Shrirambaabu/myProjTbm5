package com.forzo.holdMyCard.ui.activities.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.BaseView;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

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


    static Uri capturedImageUri = null;

    private Bitmap bitmap;


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

    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;

    @BindView(R.id.relative_main)
    RelativeLayout relativeLayoutMain;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;


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

    }

    @OnClick(R.id.button)
    public void captureImage() {

        Calendar cal = Calendar.getInstance();


        // fetching the root directory
        String root = Environment.getExternalStorageDirectory().toString()
                + "/HMC";

        // Creating folders for Image
        String imageFolderPath = root + "/saved_images";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file name
        String imageName = cal.getTimeInMillis() + ".png";

        // Creating image here
        File image = new File(imageFolderPath, imageName);

        capturedImageUri = Uri.fromFile(image);

        // imageView.setTag(imageFolderPath + File.separator + imageName);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);

        startActivityForResult(takePictureIntent, requestCode);

    }

    @OnClick(R.id.emul_button)
    public void emulator() {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bsk);
        homePresenter.callVisionApi(HomeActivity.this, b, feature, capturedImageUri, avLoadingIndicatorView, relativeLayout,relativeLayoutMain);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {

            //     bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), capturedImageUri);

            //  bitmap = BitmapFactory.decodeFile(capturedImageUri.getPath());

            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            avLoadingIndicatorView.show();
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayoutMain.setVisibility(View.GONE);
            Log.e("HM","Calling Vision");
            homePresenter.callVisionApi(HomeActivity.this, bitmap, feature, capturedImageUri, avLoadingIndicatorView, relativeLayout,relativeLayoutMain);


        } else {

            Toast.makeText(getApplicationContext(), "Action Cancelled", Toast.LENGTH_LONG).show();
        }
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
