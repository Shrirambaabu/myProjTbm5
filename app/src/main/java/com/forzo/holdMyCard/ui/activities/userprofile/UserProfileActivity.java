package com.forzo.holdMyCard.ui.activities.userprofile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.activities.imageFullScreen.ImageFullScreenActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.newcard.NewCardActivity;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.jackandphantom.circularimageview.CircleImage;
import com.snatik.storage.Storage;
import com.wang.avi.AVLoadingIndicatorView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    public static final String SELECT_THE_IMAGE = "Select the image";
    public static final int IMAGE_TYPE_DP = 0;
    public static final int IMAGE_TYPE_BCF = 1;
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
    @BindView(R.id.rel_home)
    RelativeLayout relativeHome;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @Inject
    UserProfilePresenter userProfilePresenter;
    private Context mContext = UserProfileActivity.this;

    private Storage storage;
    private String newDir;
    private int WRITE_EXTERNAL_STORAGE = 111;

    private String dpImageValue = "", bgImageValue = "";

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

        userProfilePresenter.loadCoverImage();
        userProfilePresenter.loadDisplayPicture();
        userProfilePresenter.loadProfileVales();
    }

    @OnClick(R.id.print_user_profile)
    public void printUserProfile() {
        activityLoader();
        storage = new Storage(getApplicationContext());
        // get external storage
        String path = storage.getExternalStorageDirectory();
        // new dir
        newDir = path + File.separator + "Convert to Pdf";
        storage.createDirectory(newDir);
        Log.e("path", "" + newDir);
        boolean hasPermission = (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

        if (!hasPermission) {
            ActivityCompat.requestPermissions(UserProfileActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS,
                            Manifest.permission.INTERNET
                    }, WRITE_EXTERNAL_STORAGE);
        }

        Bitmap bitmap = Bitmap.createBitmap(relativeHome.getWidth(), relativeHome.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasPng = new Canvas(bitmap);
        relativeHome.draw(canvasPng);

        boolean success = storage.createFile(newDir + File.separator + "image.jpg", bitmap);
        if (success) {

            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics displaymetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            float hight = displaymetrics.heightPixels;
            float width = displaymetrics.widthPixels;

            int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();


            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawPaint(paint);


            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);


            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());

            // write the document content
            String targetPdf = newDir + timeStamp + PreferencesAppHelper.getUserId() + ".pdf";
            File filePath = new File(targetPdf);
            try {
                document.writeTo(new FileOutputStream(filePath));
                Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Sorry, Something wrong !!", Toast.LENGTH_LONG).show();
            }
            hideLoader();
            // close the document
            document.close();
        } else {
            hideLoader();
            //  Toast.makeText(this, "Image couldn't be saved to " + newDir + File.separator + "image.jpg", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "File couldn't be downloaded", Toast.LENGTH_LONG).show();
        }


    }

    @OnClick(R.id.edit_profile)
    public void profileImageSelect() {
        EasyImage.openChooserWithGallery(UserProfileActivity.this, SELECT_THE_IMAGE, IMAGE_TYPE_BCF);
    }

    @OnClick(R.id.edit_profile_image)
    public void imageSelect() {
        EasyImage.openChooserWithGallery(UserProfileActivity.this, SELECT_THE_IMAGE, IMAGE_TYPE_DP);
    }

    @OnClick(R.id.update_user_profile)
    public void updateUserProfile() {
        userProfilePresenter.updateUserProfile(textInputEditTextName.getText().toString(), textInputEditTextJobTitle.getText().toString(), textInputEditTextCompanyName.getText().toString(), textInputEditTextMobile.getText().toString(), textInputEditTextMobile2.getText().toString(), "", textInputEditTextEmail.getText().toString(), textInputEditTextAddress.getText().toString(), textInputEditTextWebsite.getText().toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
        if (type == IMAGE_TYPE_DP) {
            userProfilePresenter.updateUserBusinessImage(imageFile, "DP");
            circleImage.setImageBitmap(bitmap);
        } else if (type == IMAGE_TYPE_BCF) {
            userProfilePresenter.updateUserBusinessImage(imageFile, "BCF");
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
        bgImageValue = IMAGE_URL + backGroundImage;
        Glide.with(mContext)
                .load(IMAGE_URL + backGroundImage)
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public void setDpImage(String dpImage) {
        dpImageValue = IMAGE_URL + dpImage;
        Glide.with(mContext)
                .load(IMAGE_URL + dpImage)
                .thumbnail(0.1f)
                .into(circleImage);
    }


    @OnClick(R.id.profile_library_image)
    public void onViewClicked() {
        if ((dpImageValue != null && !dpImageValue.equals("")||(bgImageValue != null && !bgImageValue.equals("")))) {
            Intent fullScreenIntent = new Intent(mContext, ImageFullScreenActivity.class);
            if (bgImageValue != null) {
                fullScreenIntent.putExtra("profImage", "yes");
                fullScreenIntent.putExtra("imageUri", bgImageValue);
            }
            startActivity(fullScreenIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @OnClick(R.id.circleImage)
    public void onViewClickedProfile() {
        if ((dpImageValue != null && !dpImageValue.equals("")||(bgImageValue != null && !bgImageValue.equals("")))) {
            Intent fullScreenIntent = new Intent(mContext, ImageFullScreenActivity.class);
            if (dpImageValue != null)
                fullScreenIntent.putExtra("image", dpImageValue);
            startActivity(fullScreenIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
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
    public void onBackPressed() {
        Intent intent = new Intent(UserProfileActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
