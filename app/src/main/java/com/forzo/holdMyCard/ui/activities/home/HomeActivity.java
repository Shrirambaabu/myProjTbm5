package com.forzo.holdMyCard.ui.activities.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.BaseView;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, BaseView {

    private final int requestCode = 20;
    private Context mContext = HomeActivity.this;
    private static final int ACTIVITY_NUM = 0;

    private Bitmap bitmap;


    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 103;
    private static final int READ_STORAGE_REQUEST_CODE = 104;

    @Inject
    HomePresenter homePresenter;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.button)
    Button button;

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


    }

    @OnClick(R.id.button)
    public void captureImage() {

        Intent photoCaptureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(photoCaptureIntent, requestCode);
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
        if (checkPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ) {

        } else {
            makeRequest(Manifest.permission.CAMERA);

        }
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){

        }else {
            makeStorageRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){

        }else {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {


            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //file path of captured image
           String filePath = cursor.getString(columnIndex);
            //file path of captured image
            File f = new File(filePath);
          String  filename= f.getName();

            Toast.makeText(HomeActivity.this, "Your Path:"+filePath, Toast.LENGTH_LONG).show();
            Toast.makeText(HomeActivity.this, "Your Filename:"+filename, Toast.LENGTH_LONG).show();
            cursor.close();

            //Convert file path into bitmap image using below line.
            // yourSelectedImage = BitmapFactory.decodeFile(filePath);


            //put bitmapimage in your imageview
            //yourimgView.setImageBitmap(yourSelectedImage);

            /*
            bitmap = (Bitmap) data.getExtras().get("data");

            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);

            intent.putExtra("image", bitmap);

            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
*/
        } else {

            Toast.makeText(getApplicationContext(), "Action Cancelled", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();    }

}
