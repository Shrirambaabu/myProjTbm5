package com.forzo.holdMyCard.ui.activities.newcard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.notes.DaggerNotesComponent;
import com.forzo.holdMyCard.ui.activities.notes.NotesActivity;
import com.forzo.holdMyCard.ui.activities.remainder.ReminderActivity;
import com.forzo.holdMyCard.utils.CameraUtils;
import com.google.api.services.vision.v1.model.Feature;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class NewCardActivity extends AppCompatActivity implements NewCardContract.View {

    @Inject
    NewCardPresenter newCardPresenter;


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
    @BindView(R.id.profile_library_image)
    ImageView businessImage;
    @BindView(R.id.profile_library_image_add)
    ImageView previewImage;

    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;
    @BindView(R.id.scan_qr)
    LinearLayout scanQRLayout;
    @BindView(R.id.business_action_button)
    RelativeLayout businessActionButton;
    @BindView(R.id.card_function)
    LinearLayout cardFunctionLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @BindView(R.id.save_user_profile)
    Button saveButton;
    @BindView(R.id.update_user_profile)
    Button updateButton;

    @BindView(R.id.delete_user_profile)
    Button deleteButton;

    private Context mContext = NewCardActivity.this;
    private String TAG = "NewCardActivity";
    private static int qrCode = 0;
    private Feature feature;
    private String[] visionAPI = new String[]{"TEXT_DETECTION", "LOGO_DETECTION"};
    private static int PERMISSION_REQUEST_CODE = 1;
    private final int requestCode = 20;
    private String intentEmail = "";

    private String primaryValue = "";

    private String imageValue = "";

    private static Uri capturedImageUri = null;
    private String[] permissionList = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        ButterKnife.bind(this);

        backButtonOnToolbar(NewCardActivity.this);
        DaggerNewCardComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        newCardPresenter.attach(this);
        newCardPresenter.getIntentValues(getIntent());


        feature = new Feature();
        feature.setType(visionAPI[0]);
        feature.setMaxResults(15);
    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(NewCardActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewCardActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.qrcode)
    public void qrCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator
                .setOrientationLocked(true)
                .setCameraId(0)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                .setPrompt("Scan a QR Code")
                .initiateScan();
    }

    @Override
    public void activityLoader() {
        relativeLayout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();
    }

    @Override
    public void onPhotosReturned(Uri photoUri) {
        CropImage.activity(photoUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(16, 9)
                .setActivityMenuIconColor(Color.WHITE)
                .setAllowRotation(true)
                .setAllowFlipping(false)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .setOutputCompressQuality(100)
                .setAutoZoomEnabled(true)
                .setActivityTitle("Align Card")
                .setCropMenuCropButtonTitle("Process")
                .start(this);
    }

    @Override
    public void sendCroppedImage(Uri resultUri) {

        newCardPresenter.callGoogleCloudVision(resultUri, feature, resultUri);

    }

    @Override
    public void showDialog() {
        relativeLayout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();
    }

    @Override
    public void setUserName(String userName) {
        textInputEditTextName.setText(userName);
    }

    @Override
    public void setCompanyName(String companyName) {
        textInputEditTextCompanyName.setText(companyName);
    }

    @Override
    public void setJobTitle(String jobTitle) {
        textInputEditTextJobTitle.setText(jobTitle);
    }

    @Override
    public void setAddress(String address) {
        textInputEditTextAddress.setText(address);
    }

    @Override
    public void libraryUserId(String libraryUserId) {
        primaryValue=libraryUserId;
    }

    @Override
    public void newCardActivityType() {
        saveButton.setVisibility(View.VISIBLE);
        scanQRLayout.setVisibility(View.VISIBLE);
        cardFunctionLayout.setVisibility(View.GONE);
        businessActionButton.setVisibility(View.GONE);
    }

    @Override
    public void libraryActivityType() {
        previewImage.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        scanQRLayout.setVisibility(View.GONE);
        cardFunctionLayout.setVisibility(View.VISIBLE);
        businessActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmailFromAPI(String email) {
        Log.e("NewActivity", email);
        if (!email.equals("Error")) {
            intentEmail = email;
            textInputEditTextEmail.setText(email);
        }
    }

    @Override
    public void setWebsiteFromAPI(String website) {
        if (!website.equals("Error")) {
            textInputEditTextWebsite.setText(website);
        }
    }

    @Override
    public void dataFromAPI(String intentMessage) {
        newCardPresenter.callWatsonAPI(intentMessage, intentEmail);
    }

    @Override
    public void setProfileImageUri(Uri profileImageUri) {
        previewImage.setVisibility(View.GONE);
        Glide.with(this)
                .load(profileImageUri)
                .into(businessImage);
    }


    @Override
    public void setPhoneFromAPI(int phone, String phoneList) {

        Log.e("PhoneActivity", "" + phone);
        Log.e("PhoneActivity", "" + phoneList);

        textInputEditTextMobile.setText(phoneList);
       /* if (phone > 0) {
//                for (int i = 0; i <= size; i++)
//                    getView().setPhoneNumber(bundle.getString("phone" + i));


            if (phone == 3) {

                Log.e("PhoneActivity",""+phoneList);
                getView().setPhoneNumber(bundle.getString("phone" + 0));
                getView().setPhoneNumber2(bundle.getString("phone" + 1));
                getView().setPhoneNumber3(bundle.getString("phone" + 2));
                getView().showVisibilityPhoneNumber2();
                getView().showVisibilityPhoneNumber3();
            }
            if (phone == 2) {
                getView().setPhoneNumber(bundle.getString("phone" + 0));
                getView().setPhoneNumber2(bundle.getString("phone" + 1));
                getView().showVisibilityPhoneNumber2();
                getView().hideVisibilityPhoneNumber3();
            }
            if (phone == 1) {
                getView().setPhoneNumber(bundle.getString("phone" + 0));
                getView().hideVisibilityPhoneNumber2();
                getView().hideVisibilityPhoneNumber3();
            }
        }*/
    }

    @Override
    public void setMobileNumber(String mobileNumber) {
        textInputEditTextMobile.setText(mobileNumber);
    }

    @Override
    public void hideLoader() {
        relativeLayout.setVisibility(View.GONE);
        avLoadingIndicatorView.setVisibility(View.GONE);
        avLoadingIndicatorView.hide();
    }


    @OnClick(R.id.scan)
    public void captureImage() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (!newCardPresenter.checkPermission(permissionList)) {
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Start intent with Action_Image_Capture
                capturedImageUri = CameraUtils.getOutputMediaFileUri(this); //get fileUri from CameraUtils
                intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri); //Send fileUri with intent
                startActivityForResult(intent, requestCode); //start activity for result with CAMERA_REQUEST_CODE
            }
        } else {
            newCardPresenter.checkPermission(permissionList, PERMISSION_REQUEST_CODE);
        }
    }

    @OnClick(R.id.save_user_profile)
    public void saveBusinessCard() {
/*
        newCardPresenter.saveBusinessCard(textInputEditTextName.getText().toString(), textInputEditTextCompanyName.getText().toString(),
                textInputEditTextJobTitle.getText().toString(), textInputEditTextMobile.getText().toString(), textInputEditTextMobile2.getText().toString(),
                "", textInputEditTextEmail.getText().toString(), textInputEditTextWebsite.getText().toString(),
                textInputEditTextAddress.getText().toString());*/
        Toast.makeText(getApplicationContext(), "Save disabled currently", Toast.LENGTH_LONG).show();
        if (qrCode == 1) {
            //   newCardPresenter.saveBusiness
        }
    }

    @OnClick(R.id.update_user_profile)
    public void updateBusinessCard() {
/*
        newCardPresenter.updateBusinessCard(textInputEditTextName.getText().toString(), textInputEditTextCompanyName.getText().toString(),
                textInputEditTextJobTitle.getText().toString(), textInputEditTextMobile.getText().toString(), textInputEditTextMobile2.getText().toString(),
                "", textInputEditTextEmail.getText().toString(), textInputEditTextWebsite.getText().toString(),
                textInputEditTextAddress.getText().toString());*/
        Toast.makeText(getApplicationContext(), "Update disabled currently", Toast.LENGTH_LONG).show();

    }
    @OnClick(R.id.delete_user_profile)
    public void deleteBusinessCard() {
/*
        newCardPresenter.updateBusinessCard(textInputEditTextName.getText().toString(), textInputEditTextCompanyName.getText().toString(),
                textInputEditTextJobTitle.getText().toString(), textInputEditTextMobile.getText().toString(), textInputEditTextMobile2.getText().toString(),
                "", textInputEditTextEmail.getText().toString(), textInputEditTextWebsite.getText().toString(),
                textInputEditTextAddress.getText().toString());*/
        Toast.makeText(getApplicationContext(), "Delete disabled currently", Toast.LENGTH_LONG).show();

    }


    @OnClick(R.id.note_rel)
    public void noteSection() {

        Intent intent = new Intent(NewCardActivity.this, NotesActivity.class);
        intent.putExtra("libraryProfile", "" + primaryValue);
        intent.putExtra("libraryProfileImage", "" + imageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @OnClick(R.id.remaindar_rel)
    public void remainderSection() {

        Intent intent = new Intent(NewCardActivity.this, ReminderActivity.class);
        intent.putExtra("libraryProfile", "" + primaryValue);
        intent.putExtra("libraryProfileImage", "" + imageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                Log.e(TAG, "onActivityResult: " + result.getContents());

                try {
                    JSONObject jsonObj = new JSONObject(result.getContents());

                    textInputEditTextName.setText(jsonObj.getString("name"));
                    textInputEditTextJobTitle.setText(jsonObj.getString("jobName"));
                    textInputEditTextCompanyName.setText(jsonObj.getString("companyName"));
                    textInputEditTextMobile.setText(jsonObj.getString("phoneNumberProfile"));
                    textInputEditTextMobile2.setText(jsonObj.getString("phoneNumberProfile2"));
                    textInputEditTextWebsite.setText(jsonObj.getString("websiteProfile"));
                    textInputEditTextAddress.setText(jsonObj.getString("addressProfile"));
                    textInputEditTextEmail.setText(jsonObj.getString("email"));
                    if (!jsonObj.getString("businessImage").equals("") && !jsonObj.getString("businessImage").equals("null")) {
                        setImage(jsonObj.getString("businessImage"));
                        previewImage.setVisibility(View.GONE);
                    } else {
                        previewImage.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            newCardPresenter.handleResult(requestCode, resultCode, data, capturedImageUri);
        }
    }

    @Override
    public void setImage(String image) {
        imageValue=image;
        Glide.with(mContext)
                .load(IMAGE_URL + image)
                .thumbnail(0.1f)
                .into(businessImage);
        qrCode = 1;
    }


}
