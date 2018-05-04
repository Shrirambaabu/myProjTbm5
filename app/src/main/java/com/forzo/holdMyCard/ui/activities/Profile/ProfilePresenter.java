package com.forzo.holdMyCard.ui.activities.Profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import static com.forzo.holdMyCard.utils.Utils.BaseUri;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_NATURAL_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_VISION_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.getBitmapLowFile;
import static com.forzo.holdMyCard.utils.Utils.getImageEncodeImage;
import static com.forzo.holdMyCard.utils.Utils.getResizedBitmapFile;
import static com.forzo.holdMyCard.utils.Utils.savebitmap;

/**
 * Created by Shriram on 3/29/2018.
 */

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {


    private static final String TAG = "ProfileActivity";
    private ApiService mApiService;
    private Uri imageCaptured;
    private Context context;

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
    public void updateCard() {

    }

    @Override
    public void deleteCard() {

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

                    if (entity.getType().equals("PERSON")) {
                        person += entity.getName();
                    }
                    if (entity.getType().equals("ORGANIZATION")) {
                        company += entity.getName();
                    }
                    if (entity.getType().equals("LOCATION")) {
                        address += " " + entity.getName();
                    }
                }
                getView().setUserName("" + person);
                getView().setCompanyName("" + company);
                getView().setAddress("" + address);
            }
        }.execute();

    }

    @Override
    public void saveContactToPhone(EditText name, EditText mobileNumber) {

        String contactName=name.getText().toString();
        String contactNumber=mobileNumber.getText().toString();

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, contactNumber);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }


    }


    @Override
    public void getIntentValues(Intent intent, RelativeLayout cardLayout) {
        imageCaptured = intent.getParcelableExtra("image");

        Bitmap bitmap = null;


        String email;
        String website;
        String phoneNumber;
        String result;

        String profile;

        email = intent.getStringExtra("email");
        website = intent.getStringExtra("website");
        phoneNumber = intent.getStringExtra("phoneNumber");
        result = intent.getStringExtra("result");
        profile = intent.getStringExtra("libraryProfile");
        String profileLibraryImage = intent.getStringExtra("libraryProfileImage");
        String userProfile = intent.getStringExtra("profileMain");
        String newProfile = intent.getStringExtra("newContact");
        File imageFile = (File) intent.getSerializableExtra("imageFile");

        if (newProfile != null) {
            getView().newContact();
        }

        if (profileLibraryImage != null) {
           getView().setLibraryImage(profileLibraryImage);
        }
        if (imageFile != null) {

            getView().saveImageFile(imageFile);
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
        getView().setJobTitle("");
        if (email == null) {
            email = "";
        }
        if (website == null) {
            website = "";
        }
        if (phoneNumber == null) {
            phoneNumber = "";
        }


        getView().setEmailId(email);
        getView().setPhoneNumber(phoneNumber);
        getView().setWebsite(website);


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

        }
    }



    @Override
    public void saveImage(File file, String newId) {
        Bitmap bitmapS = null;


        try {
            bitmapS = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageCaptured);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap newBitmap = getResizedBitmapFile(bitmapS, 480, 640);

        Log.e("bm", "" + newBitmap.getByteCount());

        File newFile = getBitmapLowFile(newBitmap);

        Log.e("Last", "" + newFile.length());
        Log.e("LastID", "" + newId);


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
    public void saveBusinessCard(TextInputEditText nameTextInputEditText, TextInputEditText companyTextInputEditText, TextInputEditText jobTitleTextInputEditText, TextInputEditText mobileTextInputEditText, TextInputEditText emailTextInputEditText, TextInputEditText websiteTextInputEditText, TextInputEditText addressTextInputEditText,AVLoadingIndicatorView avLoadingIndicatorView,RelativeLayout relativeLayout) {


        String name = nameTextInputEditText.getText().toString();
        String company = companyTextInputEditText.getText().toString();
        String jobTitle = jobTitleTextInputEditText.getText().toString();
        String mobileNumber = mobileTextInputEditText.getText().toString();
        String emailId = emailTextInputEditText.getText().toString();
        String website = websiteTextInputEditText.getText().toString();
        String address = addressTextInputEditText.getText().toString();


        BusinessCard businessCard = new BusinessCard();

        businessCard.setId(PreferencesAppHelper.getUserId());
        businessCard.setName(name);
        businessCard.setCompany(company);
        businessCard.setJobTitle(jobTitle);
        businessCard.setPhoneNumber(mobileNumber);
        businessCard.setEmailId(emailId);
        businessCard.setWebsite(website);
        businessCard.setAddress(address);

        relativeLayout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();

        Log.e("id","ss"+PreferencesAppHelper.getUserId());

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


                        relativeLayout.setVisibility(View.GONE);
                        avLoadingIndicatorView.setVisibility(View.GONE);
                        avLoadingIndicatorView.hide();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
