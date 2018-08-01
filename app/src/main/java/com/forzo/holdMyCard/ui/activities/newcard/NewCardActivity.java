package com.forzo.holdMyCard.ui.activities.newcard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.customChooserDialog.CustomChooserDialog;
import com.forzo.holdMyCard.ui.activities.imageFullScreen.ImageFullScreenActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;

import com.forzo.holdMyCard.ui.activities.notes.NotesActivity;
import com.forzo.holdMyCard.ui.activities.remainder.ReminderActivity;
import com.forzo.holdMyCard.utils.CameraUtils;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.google.api.services.vision.v1.model.Feature;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.snatik.storage.Storage;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.crosswall.lib.coverflow.core.PagerContainer;
import me.relex.circleindicator.CircleIndicator;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

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
    @BindView(R.id.profile_library_image_back)
    ImageView businessBackImage;
    @BindView(R.id.edit_profile)
    ImageView editBusinessImage;

    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;
    @BindView(R.id.scan_qr)
    LinearLayout scanQRLayout;
    @BindView(R.id.business_action_button)
    RelativeLayout businessActionButton;
    @BindView(R.id.rel_one)
    RelativeLayout relativeHome;
    @BindView(R.id.card_function)
    LinearLayout cardFunctionLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;
    @BindView(R.id.scroll)
    NestedScrollView scrollView;

    @BindView(R.id.save_user_profile)
    Button saveButton;
    @BindView(R.id.update_user_profile)
    Button updateButton;

    @BindView(R.id.delete_user_profile)
    Button deleteButton;
    @BindView(R.id.time_ago)
    TextView textView;

    @BindView(R.id.carouselView)
    CarouselView carouselView;

    private ViewPager mPager;
    private Context mContext = NewCardActivity.this;
    private String TAG = "NewCardActivity";
    private static int qrCode = 0;
    private Feature feature;
    private String[] visionAPI = new String[]{"TEXT_DETECTION", "LOGO_DETECTION"};
    private static int PERMISSION_REQUEST_CODE = 1;
    private final int requestCode = 20;
    private String intentEmail = "";


    private Storage storage;
    private String newDir;
    private int WRITE_EXTERNAL_STORAGE = 111;
    private String primaryValue = "";
    private Uri profileImageUri = null;
    private Uri profileBackImageUri = null;

    private String imageValue = "";
    private String qrImageName = "";
    private Bitmap businessBitmap = null;
    Bitmap image1, image2;

    final Bitmap[] bitmaps = new Bitmap[2];

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

        image1 = BitmapFactory.decodeResource(getResources(), R.drawable.new_empty_image);
        image2 = BitmapFactory.decodeResource(getResources(), R.drawable.new_empty_image);

        bitmaps[0] = image1;
        bitmaps[1] = image2;
        carouselView.setPageCount(2);


        final ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                //  imageView.setImageResource(sampleImages[position]);
                imageView.setImageBitmap(bitmaps[position]);
            }
        };

        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {

                Log.e("Click", "" + position);
                // Toast.makeText(NewCardActivity.this, "Clicked item: " + position, Toast.LENGTH_SHORT).show();
                if (position == 1) {
                    EasyImage.openChooserWithGallery(NewCardActivity.this, "Select the image", position);
                }
                if (position == 0) {

                    if (imageValue != null && !imageValue.equals("")) {
                        Intent fullScreenIntent = new Intent(mContext, ImageFullScreenActivity.class);
                        fullScreenIntent.putExtra("imageUri", IMAGE_URL+imageValue);
                        fullScreenIntent.putExtra("profImage", "yes");
                        startActivity(fullScreenIntent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }
            }
        });

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

    @OnClick(R.id.save_contact)
    public void saveToPhoneContact() {
        newCardPresenter.saveContactToPhone(textInputEditTextName.getText().toString(), textInputEditTextMobile.getText().toString(),
                textInputEditTextEmail.getText().toString(), textInputEditTextCompanyName.getText().toString(),
                textInputEditTextJobTitle.getText().toString(), textInputEditTextAddress.getText().toString());
    }

    @OnClick(R.id.linked_in)
    public void searchOnLinkedIn() {

        if (!textInputEditTextName.getText().toString().equals("")) {
            newCardPresenter.searchUserOnLinkedIn(textInputEditTextName.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "UserName is empty !", Toast.LENGTH_LONG).show();
        }
    }


    @OnClick(R.id.twitter)
    public void searchOnTwitter() {

        if (!textInputEditTextName.getText().toString().equals("")) {
            newCardPresenter.searchUserOnTwitter(textInputEditTextName.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "UserName is empty !", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.facebook)
    public void searchOnFacebook() {

        if (!textInputEditTextName.getText().toString().equals("")) {
            newCardPresenter.searchUserOnFacebook(textInputEditTextName.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "UserName is empty !", Toast.LENGTH_LONG).show();
        }
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
        primaryValue = libraryUserId;
    }


    @Override
    public void savedQRProfileImage(String userId) {

        if (qrCode == 1) {
            newCardPresenter.saveQRImageName(userId, "BCF", qrImageName);
        } else {
            newCardPresenter.saveBusinessImage(profileImageUri, userId, "BCF");
            if (profileBackImageUri != null) {
                newCardPresenter.saveBusinessImage(profileBackImageUri, userId, "BCB");
            }
        }

    }

    @Override
    public void qrProfileSavedSuccessfully() {
        Toast.makeText(getApplicationContext(), "Business Card Saved", Toast.LENGTH_LONG).show();

        Intent intentSave = new Intent(NewCardActivity.this, MyLibraryActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void updateSuccess() {
        Toast.makeText(getApplicationContext(), "Business Card Updated", Toast.LENGTH_LONG).show();
        Intent intentSave = new Intent(NewCardActivity.this, MyLibraryActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void deleteProfile() {
        Toast.makeText(getApplicationContext(), "Business Card Deleted", Toast.LENGTH_LONG).show();
        Intent intentSave = new Intent(NewCardActivity.this, MyLibraryActivity.class);
        intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void setModifiedTs(String modifiedTs) {
        textView.setText("Last edit was made " + modifiedTs);
    }


    @Override
    public void newCardActivityType() {
        saveButton.setVisibility(View.VISIBLE);
        scanQRLayout.setVisibility(View.VISIBLE);
        cardFunctionLayout.setVisibility(View.GONE);
        businessActionButton.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        editBusinessImage.setVisibility(View.GONE);
    }

    @Override
    public void libraryActivityType() {

        saveButton.setVisibility(View.GONE);
        scanQRLayout.setVisibility(View.GONE);
        cardFunctionLayout.setVisibility(View.VISIBLE);
        businessActionButton.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        editBusinessImage.setVisibility(View.VISIBLE);
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
        // businessBitmap=
        this.profileImageUri = profileImageUri;
        Log.e("ProfImagURL", "" + this.profileImageUri);
      /*  Glide.with(this)
                .load(profileImageUri)
                .into(businessImage);

        businessImage.buildDrawingCache();
        Bitmap bmap = businessImage.getDrawingCache();*/
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profileImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmaps[0] = bitmap;
        carouselView.setPageCount(2);
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
    public void setPhoneNumber2(String mobileNumber2) {
        textInputEditTextMobile2.setText(mobileNumber2);
    }

    @Override
    public void hideLoader() {
        relativeLayout.setVisibility(View.GONE);
        avLoadingIndicatorView.setVisibility(View.GONE);
        avLoadingIndicatorView.hide();
    }


    @OnClick(R.id.edit_profile)
    public void editBussinessProfile() {

        cardFunctionLayout.setVisibility(View.GONE);
        scanQRLayout.setVisibility(View.VISIBLE);
        //   Toast.makeText(getApplicationContext(), "Editing Image is Currently disabled", Toast.LENGTH_LONG).show();

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

        if (profileImageUri == null && qrImageName.equals("")) {
            Toast.makeText(getApplicationContext(), "Select an image", Toast.LENGTH_LONG).show();
            return;
        }

        if (profileImageUri != null || !qrImageName.equals("")) {
            newCardPresenter.saveBusinessCard(textInputEditTextName.getText().toString(), textInputEditTextCompanyName.getText().toString(),
                    textInputEditTextJobTitle.getText().toString(), textInputEditTextMobile.getText().toString(), textInputEditTextMobile2.getText().toString(),
                    "", textInputEditTextEmail.getText().toString(), textInputEditTextWebsite.getText().toString(),
                    textInputEditTextAddress.getText().toString());
        }
        /*
        if (qrCode == 1) {
               newCardPresenter.saveQRImageName();
        }*/
    }

    @OnClick(R.id.update_user_profile)
    public void updateBusinessCard() {

        Log.e("updateBus", "" + primaryValue);
        newCardPresenter.updateCard(primaryValue, textInputEditTextName.getText().toString(), textInputEditTextCompanyName.getText().toString(),
                textInputEditTextJobTitle.getText().toString(), textInputEditTextMobile.getText().toString(), textInputEditTextMobile2.getText().toString(),
                "", textInputEditTextEmail.getText().toString(), textInputEditTextWebsite.getText().toString(),
                textInputEditTextAddress.getText().toString());
        if (profileImageUri != null) {
            Log.e("updateBCFReq", "BCF image");
            newCardPresenter.updateBusinessImage(profileImageUri, primaryValue, "BCF");
            Log.e("UpdateBCFImg", "" + profileImageUri + ":Id:" + primaryValue);

        }
        if (profileBackImageUri != null) {
            Log.e("updateReq", "BCB image");
            newCardPresenter.updateBusinessImage(profileBackImageUri, primaryValue, "BCB");
            Log.e("UpdateImg", "" + profileBackImageUri + ":Id:" + primaryValue);

        }


    }

    @OnClick(R.id.delete_user_profile)
    public void deleteBusinessCard() {
        newCardPresenter.deleteCard(primaryValue);

    }

    @OnClick(R.id.print)
    public void createPdf() {
        //init
        storage = new Storage(getApplicationContext());
        // get external storage
        String path = storage.getExternalStorageDirectory();

        // new dir
        newDir = path + File.separator + ".HMC Pdf";
        storage.createDirectory(newDir);
        Log.e("path", "" + newDir);
        boolean hasPermission = (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

        if (!hasPermission) {
            ActivityCompat.requestPermissions(NewCardActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS,
                            Manifest.permission.INTERNET
                    }, WRITE_EXTERNAL_STORAGE);
        }

        activityLoader();
/*
        Layout_to_Image layout_to_image = new Layout_to_Image(NewCardActivity.this, scrollView);
        Bitmap bitmap = layout_to_image.convert_layout();*/

        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), relativeHome.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasPng = new Canvas(bitmap);
        relativeHome.draw(canvasPng);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        boolean success = storage.createFile(newDir + File.separator + timeStamp + ".jpg", bitmap);
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

            String pathPdf = storage.getExternalStorageDirectory();
            // new dir
            String newDirPdf = pathPdf + File.separator + "HMC_pdf";
            storage.createDirectory(newDirPdf);

            File filePath = new File(newDirPdf + File.separator + timeStamp + PreferencesAppHelper.getUserId() + ".pdf");
            try {
                document.writeTo(new FileOutputStream(filePath));
                Toast.makeText(getApplicationContext(), "File Downloaded to "+newDirPdf + File.separator + timeStamp + PreferencesAppHelper.getUserId() + ".pdf", Toast.LENGTH_LONG).show();
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

    @OnClick(R.id.calendar_rel)
    public void calenderSection() {
      /*  Intent calendarIntent = new Intent(mContext, CustomChooserDialog.class);
        calendarIntent.putExtra("email", textInputEditTextEmail.getText().toString());
        startActivity(calendarIntent);
        */
        newCardPresenter.calendarOptions(textInputEditTextEmail.getText().toString());
    }

    @OnClick(R.id.profile_library_image)
    public void onViewClicked() {
        if (imageValue != null && !imageValue.equals("")) {
            Intent fullScreenIntent = new Intent(mContext, ImageFullScreenActivity.class);
            if (imageValue != null)
                fullScreenIntent.putExtra("image", IMAGE_URL + imageValue);
            if (profileImageUri != null)
                fullScreenIntent.putExtra("imageUri", profileImageUri.toString());
            startActivity(fullScreenIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
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
                        setQRImage(jsonObj.getString("businessImage"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            newCardPresenter.handleResult(requestCode, resultCode, data, capturedImageUri);
        }


        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Log.e("type", "" + type);

                setSliderImage(imageFile, type);
            }

        });


    }

    private void setSliderImage(File imageFile, int type) {

        profileBackImageUri = Uri.fromFile(imageFile);

        Log.e("profBackURI", "" + profileBackImageUri);

        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        if (type == 0) {
            bitmaps[0] = bitmap;
            carouselView.setPageCount(2);
        } else if (type == 1) {
            bitmaps[1] = bitmap;
            carouselView.setPageCount(2);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void setImage(String image) {
        imageValue = image;

    }

    @Override
    public void setQRImage(String qrImage) {
        qrImageName = qrImage;
        /*Glide.with(mContext)
                .load(IMAGE_URL + qrImage)
                .thumbnail(0.1f)
                .into(businessImage);*/
        newCardPresenter.setQRPresenter(qrImage);
        qrCode = 1;
    }

    @Override
    public void setBusinessCarosuilImage(Bitmap businessCarosuilImage, String imageName) {
        imageValue = imageName;
        bitmaps[0] = businessCarosuilImage;
        carouselView.setPageCount(2);
        Log.e("Activity", "businessCarosel: " + imageValue);
    }

    @Override
    public void setBusinessBackCarosuilImage(Bitmap businessBackCarosuilImage, String imageName) {
        bitmaps[1] = businessBackCarosuilImage;
        carouselView.setPageCount(2);
    }

    @Override
    public void setBusinessQRCarosuilImage(Bitmap businessBackCarosuilImage, String imageName) {
        bitmaps[0] = businessBackCarosuilImage;
        carouselView.setPageCount(2);
    }


}
