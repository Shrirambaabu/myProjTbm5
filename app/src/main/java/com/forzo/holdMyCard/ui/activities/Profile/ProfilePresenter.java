package com.forzo.holdMyCard.ui.activities.Profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
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
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;

/**
 * Created by Shriram on 3/29/2018.
 */

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {


    private static final String TAG = "ProfileActivity";
    private ApiService mApiService;

    private static final String CLOUD_VISION_API_KEY = "AIzaSyCFVBIjD8Vk13VzO980yu_OsVL2-F5itpA";

    private String[] visionAPI = new String[]{"TEXT_DETECTION"};

    private String api = visionAPI[0];

    private Context context;

    ProfilePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx) {
        setupBottomNavigationSetUp(bottomNavigationViewEx);
        getView().viewBottomNavigation(bottomNavigationViewEx);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void callCloudVision(final Bitmap bitmap, final Feature feature,AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeProgress) {


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

                String phoneNumber="";

                email = parseEmail(result);
                website = parseWebsite(result);
                phone = parseMobile(result);

                phoneNumber=phone.toString().replaceAll("\\[", "").replaceAll("\\]","");

                if (phoneNumber.equals("")){
                    phoneNumber="No number found";
                }

                getView().setEmailId(email);
                getView().setPhoneNumber(phoneNumber);
                getView().setWebsite(website);


                avLoadingIndicatorView.hide();
                avLoadingIndicatorView.setVisibility(View.GONE);
                relativeProgress.setVisibility(View.GONE);
            }
        }.execute();


    }

    @Override
    public void setupNetworkCall(Bitmap bitmap, Feature feature) {

    }

    public Image getImageEncodeImage(Bitmap bitmap) {

        Image base64EncodedImage = new Image();
        // Convert the bitmap to a JPEG
        // Just in case it's a format that Android understands but Cloud Vision
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Base64 encode the JPEG
        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
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
