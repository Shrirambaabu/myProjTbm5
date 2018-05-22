package com.forzo.holdMyCard.ui.activities.Profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.utils.HttpHandler;
import com.forzo.holdMyCard.utils.NetworkController;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.language.v1beta2.CloudNaturalLanguage;
import com.google.api.services.language.v1beta2.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1beta2.model.AnnotateTextRequest;
import com.google.api.services.language.v1beta2.model.AnnotateTextResponse;
import com.google.api.services.language.v1beta2.model.Document;
import com.google.api.services.language.v1beta2.model.Entity;
import com.google.api.services.language.v1beta2.model.Features;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesResult;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;
import static com.forzo.holdMyCard.utils.NullUtils.notEmpty;
import static com.forzo.holdMyCard.utils.Utils.BaseUri;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_NATURAL_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_VISION_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.getBitmapLowFile;
import static com.forzo.holdMyCard.utils.Utils.getImageEncodeImage;
import static com.forzo.holdMyCard.utils.Utils.getResizedBitmapFile;
import static com.forzo.holdMyCard.utils.Utils.isPackageInstalled;
import static com.forzo.holdMyCard.utils.Utils.parseCompanyNameFromEmail;
import static com.forzo.holdMyCard.utils.Utils.parseNameFromEmail;
import static com.forzo.holdMyCard.utils.Utils.savebitmap;

/**
 * Created by Shriram on 3/29/2018.
 */

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {


    private static final String TAG = "ProfileActivity";
    private ApiService mApiService;
    private Uri imageCaptured;
    private Uri imageUri = null;
    private Context context;

    private NaturalLanguageUnderstanding service;
    private AnalyzeOptions parameters;
    private String send;
    private String intentEmail;

    private String email, cName, pName;

    ProfilePresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());

    }

    @Override
    public void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx) {
        setupBottomNavigationSetUp(bottomNavigationViewEx);
        getView().viewBottomNavigation(bottomNavigationViewEx);
    }

    @Override
    public void showProfileData(String profile) {


        mApiService.getUserProfile(profile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BusinessCard businessCardList) {

                        String userNameProfile = businessCardList.getName();
                        String job = businessCardList.getJobTitle();
                        String companyNameProfile = businessCardList.getCompany();
                        String phoneNumberProfile = businessCardList.getPhoneNumber();
                        String emailIdProfile = businessCardList.getEmailId();
                        String websiteProfile = businessCardList.getWebsite();
                        String addressProfile = businessCardList.getAddress();

                        if (job == null) {
                            job = "";
                        }
                        if (userNameProfile == null) {
                            userNameProfile = "";
                        }
                        if (companyNameProfile == null) {
                            companyNameProfile = "";
                        }
                        if (phoneNumberProfile == null) {
                            phoneNumberProfile = "";
                        }
                        if (emailIdProfile == null) {
                            emailIdProfile = "";
                        }
                        if (websiteProfile == null) {
                            websiteProfile = "";
                        }
                        if (addressProfile == null) {
                            addressProfile = "";
                        }

                        getView().setUserName(userNameProfile);
                        getView().setJobTitle(job);
                        getView().setCompanyName(companyNameProfile);
                        getView().setPhoneNumber(phoneNumberProfile);
                        getView().setEmailId(emailIdProfile);
                        getView().setWebsite(websiteProfile);
                        getView().setAddress(addressProfile);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void naturalProcess(String result) {

        Log.e("natural", "doing");
        final CloudNaturalLanguage naturalLanguageService =
                new CloudNaturalLanguage.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(),
                        null
                ).setCloudNaturalLanguageRequestInitializer(
                        new CloudNaturalLanguageRequestInitializer(CLOUD_NATURAL_API_KEY)
                ).build();


        Document document = new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("en-US");
        document.setContent(result);

        Features features = new Features();
        features.setExtractEntities(true);
        features.setExtractDocumentSentiment(true);

        final AnnotateTextRequest request = new AnnotateTextRequest();
        request.setDocument(document);
        request.setFeatures(features);


        new AsyncTask<Object, Void, AnnotateTextResponse>() {
            @Override
            protected AnnotateTextResponse doInBackground(Object... params) {

                AnnotateTextResponse response = new AnnotateTextResponse();
                try {
                    Log.e(TAG, "doInBackground: sending Request ");

                    response = naturalLanguageService.documents()
                            .annotateText(request).execute();


                    return response;

                } catch (GoogleJsonResponseException e) {
                    Log.e(TAG, "failed to connect because " + e.getContent());
                } catch (IOException e) {
                    Log.e(TAG, "failed to connect because of other IOException " + e.getMessage());
                }
                return response;
            }

            protected void onPostExecute(AnnotateTextResponse response) {


                final List<Entity> entityList = response.getEntities();
                // final float sentiment = response.getDocumentSentiment().getScore();

                String entities = "";

                String person = "";
                String company = "";
                String address = "";


                Log.e("GoogleNlp ", "" + entityList.toString());
                for (Entity entity : entityList) {
                    entities += "\n" + entity.getName() + " " + entity.getType();

//                    if (entity.getType().equals("PERSON")) {
//                        person += entity.getName();
//                    }
//                    if (entity.getType().equals("ORGANIZATION")) {
//                        company += entity.getName();
//                    }
                    if (entity.getType().equals("LOCATION")) {
                        Log.e(TAG, "onPostExecute: Address " + entity.getType() + " Place " + entity.getName());
                        address += " " + entity.getName();
                    }
                }
//                getView().setUserName("" + person);
//                getView().setCompanyName("" + company);
                getView().setAddress("" + address);
            }
        }.execute();

    }

    @Override
    public void addToCalendar(String email) {

        if (isPackageInstalled("com.google.android.calendar", context)) {

            Calendar beginTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();

            endTime.add(Calendar.HOUR_OF_DAY, 1);

            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, "")
                    .putExtra(CalendarContract.Events.DESCRIPTION, "")
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, email);
            context.startActivity(intent);

        } else {
            Toast.makeText(context, "Google Calendar not installed.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void saveContactToPhone(String contactName, String contactNumber, String contactEmail, String contactCompanyName, String contactJobTitle, String contactAddressText) {


        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactNumber);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, contactEmail);
        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, contactCompanyName);
        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, contactJobTitle);
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, contactAddressText);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }


    }


    @Override
    public void getIntentValues(Intent intent, String emailTextInputEditText, String companyTextInputEditText, String nameTextInputEditText) {


        String email;
        String website;
        String phoneNumber;
        String result;

        String profile;

        email = intent.getStringExtra("email");
        // website = intent.getStringExtra("website");
        //  phoneNumber = intent.getStringExtra("phoneNumber");
        result = intent.getStringExtra("result");
        profile = intent.getStringExtra("libraryProfile");
        String profileLibraryImage = intent.getStringExtra("libraryProfileImage");
        String userProfile = intent.getStringExtra("profileMain");
        String newProfile = intent.getStringExtra("newContact");
        File imageFile = (File) intent.getSerializableExtra("imageFile");


        Bundle bundle = intent.getExtras();
        if (bundle != null) {
//            inputEmail.setText(bundle.getString("email"));
            if (!Objects.equals(bundle.getString("website"), "Error"))
                getView().setWebsite(bundle.getString("website"));
            if (!Objects.equals(bundle.getString("email"), "Error"))
                intentEmail = bundle.getString("email");
            int size = bundle.getInt("phone_size", 0);
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    getView().setPhoneNumber(bundle.getString("phone" + i));

                }
            }
            if (bundle.getString("intentUri") != null) {
                imageUri = Uri.parse(bundle.getString("intentUri"));
                if (imageUri != null) {
                    getView().setProfileImageUri(imageUri);
                    getView().newContact();
                    getView().saveImageFile(imageFile, imageUri);
                }
            }
            send = bundle.getString("parse");

            if (send != null) {
                callWatsonApi(send, emailTextInputEditText, companyTextInputEditText, nameTextInputEditText);
            }
        }

        //  imageCaptured = intent.getParcelableExtra("image");

        Bitmap bitmap = null;


        if (newProfile != null) {
            getView().newContact();
        }

        if (profileLibraryImage != null) {
            getView().setLibraryImage(profileLibraryImage);
        }
        if (imageFile != null) {

            getView().saveImageFile(imageFile, imageUri);
            getView().newContact();
        }


        if (userProfile != null) {
            getView().setDialog();
        }

        if (profile != null) {
            Log.e("ProfileValue", "" + profile);
            getView().setUserPrimaryValue(profile);
            showProfileData(profile);

        }


        if (result != null) {
            naturalProcess(result);
        } else {
            Log.e("intent", "" + intent.getStringExtra("result"));

            getView().setUserName("");
            getView().setCompanyName("");
            getView().setAddress("");
        }
        //getView().setJobTitle("");
        if (email == null) {
            email = "";
        }
      /*  if (website == null) {
            website = "";
        }
        if (phoneNumber == null) {
            phoneNumber = "";
        }*/


        getView().setEmailId(email);
        //  getView().setPhoneNumber(phoneNumber);
        //  getView().setWebsite(website);
/*

        if (imageCaptured != null) {

            Log.e("Profile", "Called");

            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageCaptured);

                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);

                getView().setProfileImage(scaled);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void callWatsonApi(String send, String emailTextInputEditText, String companyTextInputEditText, String nameTextInputEditText) {


        service = new NaturalLanguageUnderstanding("2018-03-16");
        service.setUsernameAndPassword("e7c2b904-2645-4b07-91bb-583f4997f066", "wxrTNDqJjGBn");

        EntitiesOptions entities = new EntitiesOptions.Builder()
                .sentiment(true)
                .build();

        com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features features
                = new com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features.Builder()
                .entities(entities)
                .build();

        parameters = new AnalyzeOptions.Builder()
                .text(send)
                .features(features)
                .build();

        new AsyncTask<Object, Void, AnalysisResults>() {

            @Override
            protected AnalysisResults doInBackground(Object... objects) {
                return service.analyze(parameters).execute();
            }

            @Override
            protected void onPostExecute(AnalysisResults results) {
                Log.e(TAG, "getEntities: " + results.getEntities().size());

                List<EntitiesResult> entitiesResults = results.getEntities();

                boolean person = false;
                boolean company = false;
                boolean jobTitle = false;
                boolean emailAddress = false;
                boolean location = false;
                for (EntitiesResult result : entitiesResults) {
//                        Log.e(TAG, "Type: "+result.getType() + " And text: "+result.getText());
                    if (result.getType().equals("Person") && !person) {
                        Log.e(TAG, "Type: " + result.getType() + " And text: " + result.getText());
                        //inputName.setText(result.getText());
                        getView().setUserName(result.getText());
                        pName = result.getText();
                        person = true;
                    }
                    if (result.getType().equals("Company") && !company) {
                        Log.e(TAG, "Type: " + result.getType() + " And text: " + result.getText());
                        // inputCompanyName.setText(result.getText());
                        getView().setCompanyName(result.getText());
                        cName = result.getText();
                        company = true;
                    }
                    if (result.getType().equals("JobTitle") && !jobTitle) {
                        Log.e(TAG, "Type: " + result.getType() + " And text: " + result.getText());
                        // inputJobTitle.setText(result.getText());
                        getView().setJobTitle(result.getText());
                        jobTitle = true;
                    }
                    if (result.getType().equals("EmailAddress") && !emailAddress) {
                        Log.e(TAG, "Type: " + result.getType() + " And text: " + result.getText());
                        //inputEmail.setText(result.getText());
                        getView().setEmailId(result.getText());
                        email = result.getText();
                        emailAddress = true;
                    }
//                    if (result.getType().equals("Location") && !location) {
//                        Log.e(TAG, "Type: " + result.getType() + " And text: " + result.getText());
//                        //  inputAddress.setText(result.getText());
//                        getView().setAddress(result.getText());
//                        location = true;
//                    }
                }

                if (notEmpty(intentEmail)) {
                    email = intentEmail;
                    getView().setEmailId(intentEmail);
                }

                if (notEmpty(email)) {
                    if (!notEmpty(cName)) {
                        getView().setCompanyName(parseCompanyNameFromEmail(email));
                    }
                    if (!notEmpty(pName)) {
                        getView().setUserName(parseNameFromEmail(email));
                    }
                }
                naturalProcess(send);
            }
        }.execute();
    }

    @Override
    public void saveImage(File file, String newId) {
        Bitmap bitmapS = null;


        try {
            bitmapS = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap newBitmap = getResizedBitmapFile(bitmapS, 480, 640);

        File newFile = getBitmapLowFile(newBitmap);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image"), newFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", newFile.getName(), reqFile);

        String imageType = "BCF";

        mApiService.postUserImage(body, Integer.parseInt(newId), imageType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }


                    @Override
                    public void onNext(BusinessCard userChangePassword) {

                        getView().profileSavedSuccessfully();

                        Log.e("Succ", "image");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  progressBar.smoothToHide();
                        Log.e("error", "" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void saveBusinessCard(String name, String company, String jobTitle, String mobileNumber, String emailId, String website, String address) {


        BusinessCard businessCard = new BusinessCard();

        businessCard.setId(PreferencesAppHelper.getUserId());
        businessCard.setName(name);
        businessCard.setCompany(company);
        businessCard.setJobTitle(jobTitle);
        businessCard.setPhoneNumber(mobileNumber);
        businessCard.setEmailId(emailId);
        businessCard.setWebsite(website);
        businessCard.setAddress(address);


        getView().activityLoader("show");

        Log.e("id", "ss" + PreferencesAppHelper.getUserId());

        mApiService.saveBusinessCard(businessCard)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BusinessCard userChangePassword) {

                        Log.e("uss", userChangePassword.getUserId());

                        getView().savedSuccessfully(userChangePassword.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  progressBar.smoothToHide();
                        Log.e("error", "" + e.getMessage());

                        getView().activityLoader("hide");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void updateCard(String userId, String name, String company, String jobTitle, String mobileNumber, String emailId, String website, String address) {


        BusinessCard businessCard = new BusinessCard();

        businessCard.setUserId(userId);
        businessCard.setName(name);
        businessCard.setCompany(company);
        businessCard.setJobTitle(jobTitle);
        businessCard.setPhoneNumber(mobileNumber);
        businessCard.setEmailId(emailId);
        businessCard.setWebsite(website);
        businessCard.setAddress(address);


        mApiService.updateBusinessCard(businessCard)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        getView().activityLoader("show");
                    }

                    @Override
                    public void onNext(BusinessCard userChangePassword) {

                        Log.e("uss", "done");

                        getView().updateSuccess();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());

                        getView().activityLoader("hide");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void deleteCard(String userId) {


        mApiService.deleteBusinessCard(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getView().activityLoader("show");
                    }

                    @Override
                    public void onNext(BusinessCard userChangePassword) {

                        Log.e("uss", "deleted");

                        getView().deleteProfile();

                    }

                    @Override
                    public void onError(Throwable e) {
                        //  progressBar.smoothToHide();
                        Log.e("error", "" + e.getMessage());
                        getView().activityLoader("hide");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
