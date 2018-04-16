package com.forzo.holdMyCard.ui.activities.Profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.utils.HttpHandler;
import com.forzo.holdMyCard.utils.NetworkController;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;
import static com.forzo.holdMyCard.utils.Utils.BaseUri;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_NATURAL_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_VISION_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.getImageEncodeImage;

/**
 * Created by Shriram on 3/29/2018.
 */

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {


    private static final String TAG = "ProfileActivity";
    private ApiService mApiService;


    private RequestQueue queue;

    private String[] visionAPI = new String[]{"TEXT_DETECTION"};

    private String api = visionAPI[0];

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

    @SuppressLint("StaticFieldLeak")
    @Override
    public void callCloudVision(final Bitmap bitmap, final Feature feature, AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeProgress) {


        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);


        avLoadingIndicatorView.show();
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        relativeProgress.setVisibility(View.VISIBLE);

        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    Log.e(TAG, "doInBackground: sending Request ");

                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(annotateImageRequests);

                    Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    BatchAnnotateImagesResponse response = annotateRequest.execute();


                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                    Log.e(TAG, "failed to connect because " + e.getContent());
                } catch (IOException e) {
                    Log.e(TAG, "failed to connect because of other IOException " + e.getMessage());
                }
                return "Request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {


                Log.e("result", "" + result);


                String email = "";
                String website = "";
                ArrayList<String> phone = new ArrayList<>();

                String phoneNumber = "";

                email = parseEmail(result);
                website = parseWebsite(result);
                phone = parseMobile(result);
                makeJsonRequest(result, avLoadingIndicatorView, relativeProgress);
                phoneNumber = phone.toString().replaceAll("\\[", "").replaceAll("\\]", "");

                if (phoneNumber.equals("")) {
                    phoneNumber = "No number found";
                }

                getView().setEmailId(email);
                getView().setPhoneNumber(phoneNumber);
                getView().setWebsite(website);

/*

                avLoadingIndicatorView.hide();
                avLoadingIndicatorView.setVisibility(View.GONE);
                relativeProgress.setVisibility(View.GONE);
*/
            }
        }.execute();


    }

    @Override
    public void getIntentValues(Intent intent, RelativeLayout cardLayout) {
        Uri imageCaptured = intent.getParcelableExtra("image");

        Bitmap bitmap;


        String email = "No email found";
        String website = "No website found";
        String phoneNumber = "No Phone Number found";
        String result = "null";

        String profile = "";

        email = intent.getStringExtra("email");
        website = intent.getStringExtra("website");
        phoneNumber = intent.getStringExtra("phoneNumber");
        result = intent.getStringExtra("result");
        profile = intent.getStringExtra("libraryProfile");
        String userProfile = intent.getStringExtra("profileMain");



        if (userProfile!=null){
            showProfileData("1");

           // getView().setSaveFalse(false);
        }

        if (profile != null) {
            Log.e("ProfileValue", "" + profile);

            showProfileData(profile);


        }

       /* if (profile!=null){
            cardLayout.setVisibility(View.GONE);
        }else {
            cardLayout.setVisibility(View.VISIBLE);
        }*/

        if (result != null) {
            naturalProcess(result);
        } else {
            Log.e("intent", "" + intent.getStringExtra("result"));

            getView().setUserName("No Name found");
            getView().setCompanyName("No Company name found");
            getView().setAddress("No Address found");
        }
        getView().setJobTitle("No Jobs found");
        if (email == null) {
            email = "No email found";
        }
        if (website == null) {
            website = "No website found";
        }
        if (phoneNumber == null) {
            phoneNumber = "No email found";
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

            // profilePresenter.callCloudVision(bitmap, feature, avLoadingIndicatorView, relativeProgress);

        } else {
            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_d);
/*
            getView().setEmailId(email);
            getView().setPhoneNumber(phoneNumber);
            getView().setWebsite(website);*/
            getView().setProfileImage(b);
            // imageView.setImageResource(R.drawable.business_card);
        }
    }

    private void showProfileData(String profile) {


        mApiService.getUserProfile(profile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  getView().showLoading();
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
                            job = "No Jobs found";
                        }
                        if (userNameProfile == null) {
                            userNameProfile = "No Name found";
                        }
                        if (companyNameProfile == null) {
                            companyNameProfile = "No Company Name found";
                        }
                        if (phoneNumberProfile == null) {
                            phoneNumberProfile = "No Number found";
                        }
                        if (emailIdProfile == null) {
                            emailIdProfile = "No Email found";
                        }
                        if (websiteProfile == null) {
                            websiteProfile = "No website found";
                        }
                        if (addressProfile == null) {
                            addressProfile = "No website found";
                        }

                        getView().setUserName(userNameProfile);
                        getView().setJobTitle(job);
                        getView().setCompanyName(companyNameProfile);
                        getView().setPhoneNumber(phoneNumberProfile);
                        getView().setEmailId(emailIdProfile);
                        getView().setWebsite(websiteProfile);
                        getView().setAddress(addressProfile);
                        getView().setSaveFalse(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // getView().hideLoading();
                        Log.e("err", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //  getView().hideLoading();
                    }
                });

    }

    @SuppressLint("StaticFieldLeak")
    private void naturalProcess(String result) {

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


                Log.e("result", "" + result);

                final List<Entity> entityList = response.getEntities();
                // final float sentiment = response.getDocumentSentiment().getScore();

                String entities = "";

                String person = "";
                String company = "";
                String address = "";

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
                if (person.equals("")) {
                    person = "No name found";
                }
                if (company.equals("")) {
                    company = "No Company name found";
                }
                if (company.equals("")) {
                    address = "No Address found";
                }
                getView().setUserName("" + person);
                getView().setCompanyName("" + company);
                getView().setAddress("" + address);
            }
        }.execute();


    }

    @Override
    public void saveBusinessCard(TextInputEditText nameTextInputEditText, TextInputEditText companyTextInputEditText, TextInputEditText jobTitleTextInputEditText, TextInputEditText mobileTextInputEditText, TextInputEditText emailTextInputEditText, TextInputEditText websiteTextInputEditText, TextInputEditText addressTextInputEditText) {


        String name = nameTextInputEditText.getText().toString();
        String company = companyTextInputEditText.getText().toString();
        String jobTitle = jobTitleTextInputEditText.getText().toString();
        String mobileNumber = mobileTextInputEditText.getText().toString();
        String emailId = emailTextInputEditText.getText().toString();
        String website = websiteTextInputEditText.getText().toString();
        String address = addressTextInputEditText.getText().toString();

        Log.e("name", "" + name);
        Log.e("name", "" + company);
        Log.e("name", "" + jobTitle);
        Log.e("name", "" + mobileNumber);
        Log.e("name", "" + emailId);
        Log.e("name", "" + website);
        Log.e("name", "" + address);

        BusinessCard businessCard = new BusinessCard();

        businessCard.setId("1");
        businessCard.setName(name);
        businessCard.setCompany(company);
        businessCard.setJobTitle(jobTitle);
        businessCard.setPhoneNumber(mobileNumber);
        businessCard.setEmailId(emailId);
        businessCard.setWebsite(website);
        businessCard.setAddress(address);
        businessCard.setImage("");

        mApiService.saveBusinessCard(businessCard)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // progressBar.smoothToShow();
                    }

                    @Override
                    public void onNext(BusinessCard userChangePassword) {
                        getView().savedSuccessfully();
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

    @SuppressLint("StaticFieldLeak")
    private void makeJsonRequest(String result, AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeProgress) {


        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {

                HttpHandler sh = new HttpHandler();
                String jsonStr = "";

                if (result.equals("No Data")) {
                    jsonStr = "null";
                } else {

                    String url = BaseUri + result.replaceAll("\\s", "%20");

                    Log.e("url ", "" + url);
                    // Making a request to url and getting response
                    jsonStr = sh.makeServiceCall(url);
                }

                return jsonStr;
            }

            protected void onPostExecute(String result) {

                Log.e("result", "" + result);

                String userName = "null";
                String companyName = "null";
                String jobTitle = "null";
                String address = "null";

                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);

                        userName = jsonObj.getString("name");
                        companyName = jsonObj.getString("organisation");
                        jobTitle = jsonObj.getString("title");
                        address = jsonObj.getString("address");

                        Log.e("web", "" + jsonObj.getString("name"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                if (userName.equals("null")) {
                    userName = "No name found";
                }
                if (companyName.equals("null")) {
                    companyName = "No name found";
                }
                if (jobTitle.equals("null")) {
                    jobTitle = "No Jobs Found";
                }
                if (address.equals("null")) {
                    address = "Address not found";
                }
                getView().setUserName(userName);
                getView().setJobTitle(jobTitle);
                getView().setCompanyName(companyName);
                getView().setAddress(address);


                avLoadingIndicatorView.hide();
                avLoadingIndicatorView.setVisibility(View.GONE);
                relativeProgress.setVisibility(View.GONE);

            }
        }.execute();


    }


    private String convertResponseToString(BatchAnnotateImagesResponse response) throws IOException {

        String textResult = "No Data";

        final TextAnnotation text = response.getResponses()
                .get(0).getFullTextAnnotation();


        if (text == null) {
            Log.e("msg", "nodate");
        } else {

            Log.e("msg", "" + text.getText());

            textResult = text.getText();


        }
        return textResult;

    }

    private String parseEmail(String results) {
        String EMAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
        Matcher m = Pattern.compile(EMAIL_REGEX).matcher(results);
        String parsedEmail = "No Email Id found";
        while (m.find()) {
            parsedEmail = m.group();
        }
        return parsedEmail;
    }

    private String parseWebsite(String results) {
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
        String parsedUrl = "No website found";
        String[] words = results.split(" ");
        for (String word : words) {
            Matcher m = Pattern.compile(URL_REGEX).matcher(word);
            if (m.find()) {
                parsedUrl = m.group();
            }
        }
        return parsedUrl;
    }

    private ArrayList<String> parseMobile(String bCardText) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        Iterable<PhoneNumberMatch> numberMatches = phoneNumberUtil.findNumbers(bCardText, Locale.US.getCountry());
        ArrayList<String> data = new ArrayList<>();
        String s = "Error";
        for (PhoneNumberMatch number : numberMatches) {

            s = number.rawString();

            data.add(s);

        }
        return data;
    }

}
