package com.forzo.holdMyCard.ui.activities.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_VISION_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.convertResponseToString;
import static com.forzo.holdMyCard.utils.Utils.getImageEncodeImage;
import static com.forzo.holdMyCard.utils.Utils.parseEmail;
import static com.forzo.holdMyCard.utils.Utils.parseMobile;
import static com.forzo.holdMyCard.utils.Utils.parseWebsite;
import static com.forzo.holdMyCard.utils.Utils.resize;

/**
 * Created by Shriram on 3/29/2018.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private static final String TAG = "HomeActivity";
    static Uri capturedImageUri = null;
    private final int requestCode = 20;
    Uri intentUri = null;
    private Context mContext;
    private Bitmap bitmap;

    HomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx) {
        setupBottomNavigationSetUp(bottomNavigationViewEx);
        getView().viewBottomNavigation(bottomNavigationViewEx);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void callGoogleCloudVision(Uri uri, Feature feature, AVLoadingIndicatorView avLoadingIndicatorView, Uri intentUri, RelativeLayout relativeLayout, RelativeLayout relativeLayoutMain) {
        this.intentUri = intentUri;

        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
//        annotateImageReq.setImage(getImageEncodeImage(resizeBitmap(bitmap)));
        annotateImageReq.setImage(getImageEncodeImage(resize(uri, mContext)));
        annotateImageRequests.add(annotateImageReq);

        avLoadingIndicatorView.show();
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayoutMain.setVisibility(View.GONE);
        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
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
                    return convertGoogleResponseToString(response);
                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to connect because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to connect because of other IOException " + e.getMessage());
                }
                return "Request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                Log.e(TAG, "onPostExecute: " + result);
                if (result.equals("Nothing Found")) {
/*
                    avLoadingIndicatorView.hide();
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    relativeLayoutMain.setVisibility(View.VISIBLE);*/

                    Intent intent = new Intent(mContext, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("intentUri", intentUri.toString());
                    mContext.startActivity(intent);
                }
            }
        }.execute();

    }

    public String convertGoogleResponseToString(BatchAnnotateImagesResponse response) throws IOException {


        AnnotateImageResponse imageResponses = response.getResponses().get(0);
        List<EntityAnnotation> entityAnnotations;
        entityAnnotations = imageResponses.getTextAnnotations();
        return formatAnnotation(entityAnnotations);

    }

    private String formatAnnotation(List<EntityAnnotation> entityAnnotation) {

        StringBuilder message;

        if (entityAnnotation != null) {
            EntityAnnotation entity = entityAnnotation.get(0);
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

                Intent intent = new Intent(mContext, ProfileActivity.class);
//                intent.putExtra("email", email);
                if (!email.equals("error"))
                    intent.putExtra("email", email);
                if (!website.equals("error"))
                    intent.putExtra("website", website);
                if (!phoneList.isEmpty()) {
                    String phone[] = phoneList.toArray(new String[phoneList.size()]);
                    intent.putExtra("phone_size", phone.length);
                    for (int i = 0; i < phone.length; i++) {
                        intent.putExtra("phone" + i, phone[i]);
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
                intent.putExtra("parse", intentMessage);
                intent.putExtra("intentUri", intentUri.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        } else {
            message = new StringBuilder("Nothing Found");
        }
        Log.e(TAG, "String: " + message);
        return message.toString();

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void callVisionApi(HomeActivity homeActivity, Bitmap bitmap, Feature feature, Uri uri, AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeLayout, RelativeLayout relativeLayoutMain, File image) {
        Log.e("HM", "Vision called");

        if (uri != null) {

            try {
                InputStream ims = homeActivity.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(ims);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);


        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {


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

                String email;
                String website;
                ArrayList<String> phone;

                String phoneNumber = "";

                email = parseEmail(result);
                website = parseWebsite(result);
                phone = parseMobile(result);

                // phoneNumber = phone.toString().replaceAll("\\[", "").replaceAll("\\]", "");

                if (!phone.isEmpty()) {
                    phoneNumber = phone.get(0);
                }

                Intent intent = new Intent(homeActivity, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("email", email);
                intent.putExtra("image", uri);
                intent.putExtra("website", website);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("result", result);
                intent.putExtra("imageFile", image);
                homeActivity.startActivity(intent);
                homeActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }.execute();


    }


    @Override
    public void onBackPress() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Confirm !!!");
        alertDialog.setMessage("Are you sure you want to close this application ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(startMain);
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> {
                    dialog.dismiss();
                });
        alertDialog.show();
    }


}
