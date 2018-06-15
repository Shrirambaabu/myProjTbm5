package com.forzo.holdMyCard.ui.activities.newcard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.utils.ImagePath_MarshMallow;
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
import com.ibm.watson.developer_cloud.language_translator.v2.util.Language;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesResult;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.echodev.resizer.Resizer;

import static android.app.Activity.RESULT_OK;
import static com.forzo.holdMyCard.utils.NullUtils.notEmpty;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_NATURAL_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_VISION_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.getImageEncodeImage;
import static com.forzo.holdMyCard.utils.Utils.parseCompanyNameFromEmail;
import static com.forzo.holdMyCard.utils.Utils.parseEmail;
import static com.forzo.holdMyCard.utils.Utils.parseMobile;
import static com.forzo.holdMyCard.utils.Utils.parseNameFromEmail;
import static com.forzo.holdMyCard.utils.Utils.parseWebsite;
import static com.forzo.holdMyCard.utils.Utils.resize;

public class NewCardPresenter extends BasePresenter<NewCardContract.View> implements NewCardContract.Presenter {

    private ApiService mApiService;
    private Context context;
    private String TAG = "NewCardPresenter";
    private String getImageUrl;
    private Uri intentUri;
    private String email, cName, pName;

    private NaturalLanguageUnderstanding service;
    private AnalyzeOptions parameters;

    NewCardPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());

    }


    @Override
    public void getIntentValues(Intent intent) {
        String activityType = intent.getStringExtra("ActivityAction");
        String libraryUserId = intent.getStringExtra("libraryProfile");
        String profileLibraryImage = intent.getStringExtra("libraryProfileImage");
        Log.e("NewPresenter",""+activityType);
        if (activityType != null) {
            if (activityType.equals("NewCard")) {
                getView().newCardActivityType();
            }
            if (activityType.equals("MyLibrary")) {
                getView().libraryActivityType();
                showProfileData(libraryUserId);
                getView().libraryUserId(libraryUserId);
                if (profileLibraryImage != null)
                    getView().setImage(profileLibraryImage);
            }
        }
    }

    @Override
    public void saveBusinessCard(String nameTextInputEditText, String companyTextInputEditText, String jobTitleTextInputEditText, String mobileTextInputEditText, String mobileTextInputEditText2, String mobileTextInputEditText3, String emailTextInputEditText, String websiteTextInputEditText, String addressTextInputEditText) {

        BusinessCard businessCard = new BusinessCard();

        businessCard.setId(PreferencesAppHelper.getUserId());
        businessCard.setName(nameTextInputEditText);
        businessCard.setCompany(companyTextInputEditText);
        businessCard.setJobTitle(jobTitleTextInputEditText);
        businessCard.setPhoneNumber(mobileTextInputEditText);
        businessCard.setPhoneNumber2(mobileTextInputEditText2);
        businessCard.setPhoneNumber3(mobileTextInputEditText3);
        businessCard.setEmailId(emailTextInputEditText);
        businessCard.setWebsite(websiteTextInputEditText);
        businessCard.setAddress(addressTextInputEditText);

        getView().activityLoader();

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
                        // getView().savedSuccessfully(userChangePassword.getUserId());
                        getView().hideLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  progressBar.smoothToHide();
                        Log.e("error", "" + e.getMessage());
                        getView().hideLoader();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public boolean checkPermission(String[] permissions, int requestCode) {
        ArrayList<String> permissionsList = new ArrayList<>();
        for (String permission : permissions) {
            int result = checkPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        //If all permissions are granted
        if (permissionsList.size() == 0)
            return true;
        else
            //if any one of them are not granted then request permission
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), requestCode);
        return true;
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data, Uri capturedImageUri) {
        Log.e(TAG, "handleResult: " + requestCode + " " + resultCode + " " + data);
        if (requestCode == 20 && resultCode == RESULT_OK) {
            try {
                if (Build.VERSION.SDK_INT > 22)
                    getImageUrl = ImagePath_MarshMallow.getPath(context, capturedImageUri);
                else
                    getImageUrl = capturedImageUri.getPath();
                getView().showDialog();
                Log.e(TAG, "handleResult: startd");
                final File[] resizedImage = new File[1];
                new Resizer(context)
                        .setTargetLength(1080)
                        .setOutputFormat("PNG")
                        .setSourceImage(new File(getImageUrl))
                        .getResizedFileAsFlowable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(file -> {
                            resizedImage[0] = file;
                            getView().onPhotosReturned(Uri.fromFile(resizedImage[0]));
                        }, Throwable::printStackTrace);
                Log.e(TAG, "handleResult: ends");
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                intentUri = result.getUri();
                getView().sendCroppedImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e(TAG, "onActivityResult: ", result.getError());
            }
        }
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
    public boolean checkPermission(String[] permissions) {
        boolean permissionResult = false;
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            permissionResult = result != PackageManager.PERMISSION_GRANTED;
        }
        return permissionResult;
    }

    @Override
    public void requestPermissions(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }

    @Override
    public int checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void callGoogleCloudVision(Uri uri, Feature feature, Uri intentUri) {
        this.intentUri = intentUri;

        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
//        annotateImageReq.setImage(getImageEncodeImage(resizeBitmap(bitmap)));
        annotateImageReq.setImage(getImageEncodeImage(resize(uri, context)));
        annotateImageRequests.add(annotateImageReq);

        getView().showDialog();
        new AsyncTask<Object, Void, BatchAnnotateImagesResponse>() {
            @Override
            protected BatchAnnotateImagesResponse doInBackground(Object... params) {

                try {

                    Log.d(TAG, "doInBackground: sending Request ");

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
                    return response;
                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to connect because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to connect because of other IOException " + e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(BatchAnnotateImagesResponse result) {
                convertGoogleResponseToString(result);
                Log.e(TAG, "onPostExecute: " + result);
                Log.e(TAG, "onPostExecuteIntentURI: " + intentUri.toString());
                getView().setProfileImageUri(intentUri);
                getView().hideLoader();
              /*  if (result.equals("Nothing Found")) {
                    Intent intent = new Intent(context, NewCardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("intentUri", intentUri.toString());
                    context.startActivity(intent);
                }*/
            }
        }.execute();
    }

    @Override
    public String convertGoogleResponseToString(BatchAnnotateImagesResponse response) {

        try {
            Log.e(TAG, "convertGoogleResponseToString: " + response.toPrettyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnnotateImageResponse imageResponses = response.getResponses().get(0);
        List<EntityAnnotation> entityAnnotations;
        entityAnnotations = imageResponses.getTextAnnotations();
        return formatAnnotation(entityAnnotations);
    }

    @Override
    public String formatAnnotation(List<EntityAnnotation> entityAnnotation) {

        StringBuilder message;

        if (entityAnnotation != null) {

            EntityAnnotation entity = entityAnnotation.get(0);

//            List<Vertex> vertexList = entity.getBoundingPoly().getVertices();
//            for (Vertex vertex : vertexList) {
//                Log.e(TAG, "formatAnnotation: "+vertex.toString() );
//            }

            message = new StringBuilder(entity.getDescription());
            Log.d(TAG, "formatAnnotation: " + message);
            if (message.toString().contains("\n")) {
                BufferedReader bufReader = new BufferedReader(new StringReader(message.toString()));
                String line = "";
                String email = "";
                String website = "";
                LinkedHashSet<String> phoneList = new LinkedHashSet<>();
                try {
                    while ((line = bufReader.readLine()) != null) {
                        if (!parseEmail(line).equals("Error")) {
                            email = parseEmail(line);
                            message.append("email : ")
                                    .append(email)
                                    .append("\n");
                            Log.e(TAG, "email: " + email);
                        }
                        if (!parseWebsite(line).equals("Error")) {
                            website = parseWebsite(line);
                            message.append("website : ")
                                    .append(website)
                                    .append("\n");
                            Log.e(TAG, "website: " + website);
                        }
                        phoneList.addAll(parseMobile(line));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!phoneList.isEmpty()) {
                    for (String phone : phoneList) {
                        message.append("Phone Number :")
                                .append(phone)
                                .append("\n");
                    }
                }
                if (!phoneList.isEmpty()) {
                    String phone[] = phoneList.toArray(new String[phoneList.size()]);
                    Log.e("phone_size", "" + phone.length);

                    for (int i = 0; i < phone.length; i++) {

                        Log.e("phone" + i, phone[i]);
                        getView().setPhoneFromAPI(phone.length, phone[i]);
                    }
                }
                String intentMessage = entity.getDescription();
//                if (intentMessage.contains(email)) {
//                    intentMessage = intentMessage.replace(email, "");
//                }
                if (intentMessage.contains(website)) {
                    intentMessage = intentMessage.replace(website, "");
                }
                if (!phoneList.isEmpty()) {
                    for (String phone : phoneList) {
                        if (intentMessage.contains(email)) {
                            intentMessage = intentMessage.replace(phone, "");
                        }
                    }
                }

                Log.e("GoogleDataEmail", email);
                getView().setEmailFromAPI(email);
                Log.e("GoogleDataWebsite", website);
                getView().setWebsiteFromAPI(website);
                Log.e("GoogleDataIntent", intentMessage);

                getView().dataFromAPI(intentMessage);
                Log.e("GoogleDataURI", intentUri.toString());
                Log.e("GoogleDataURI", "" + message);

            }
        } else {
            message = new StringBuilder("Nothing Found");
        }
        Log.e(TAG, "String: " + message);
        return message.toString();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void callWatsonAPI(String intentMessage, String intentEmail) {

        service = new NaturalLanguageUnderstanding("2018-03-16");
        service.setUsernameAndPassword("e7c2b904-2645-4b07-91bb-583f4997f066", "wxrTNDqJjGBn");


        EntitiesOptions entities = new EntitiesOptions.Builder()
                .sentiment(true)
                .build();

        com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features features
                = new com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features.Builder()
                .entities(entities)
                .build();
        if (notEmpty(intentMessage)) {

            parameters = new AnalyzeOptions.Builder()
                    .text(intentMessage)
                    .language(Language.ENGLISH)
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
                            getView().setEmailFromAPI(result.getText());
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
                        getView().setEmailFromAPI(intentEmail);
                    }

                    if (notEmpty(email)) {
                        if (!notEmpty(cName)) {
                            if (!parseCompanyNameFromEmail(email).equalsIgnoreCase("gmail") &&
                                    !parseCompanyNameFromEmail(email).equalsIgnoreCase("yahoo") &&
                                    !parseCompanyNameFromEmail(email).equalsIgnoreCase("outlook")) {
                                getView().setCompanyName(parseCompanyNameFromEmail(email));
                            }
                        }
                        if (!notEmpty(pName)) {
                            if (!parseNameFromEmail(email).equalsIgnoreCase("info")) {
                                getView().setUserName(parseNameFromEmail(email));
                            }
                        }
                    }
                    naturalProcess(intentMessage);
                }
            }.execute();
        }
    }

    @Override
    public void showProfileData(String libraryUserId) {

        mApiService.getUserProfile(libraryUserId)
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
                        String phoneNumberProfile2 = businessCardList.getPhoneNumber2();
                        String phoneNumberProfile3 = businessCardList.getPhoneNumber3();
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
                       /* if (phoneNumberProfile2 == null || phoneNumberProfile2.length() <= 0) {
                            phoneNumberProfile2 = "";
                            getView().hideVisibilityPhoneNumber2();
                        } else {
                            getView().showVisibilityPhoneNumber2();
                        }
                        if (phoneNumberProfile3 == null || phoneNumberProfile3.length() <= 0) {
                            phoneNumberProfile3 = "";
                            getView().hideVisibilityPhoneNumber3();
                        } else {
                            Log.e(TAG, "onNext: " + phoneNumberProfile3);
                            getView().showVisibilityPhoneNumber3();
                        }*/
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
                        getView().setMobileNumber(phoneNumberProfile);/*
                        getView().setPhoneNumber2(phoneNumberProfile2);
                        getView().setPhoneNumber3(phoneNumberProfile3);*/
                        getView().setEmailFromAPI(emailIdProfile);
                        getView().setWebsiteFromAPI(websiteProfile);
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
}
