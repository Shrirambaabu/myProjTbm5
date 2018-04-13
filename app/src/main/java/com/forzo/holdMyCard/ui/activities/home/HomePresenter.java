package com.forzo.holdMyCard.ui.activities.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.utils.HttpHandler;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;
import static com.forzo.holdMyCard.utils.Utils.BaseUri;
import static com.forzo.holdMyCard.utils.Utils.CLOUD_VISION_API_KEY;
import static com.forzo.holdMyCard.utils.Utils.getImageEncodeImage;

/**
 * Created by Shriram on 3/29/2018.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private Context mContext;
    private static final String TAG = "HomeActivity";

    static Uri capturedImageUri = null;
    private final int requestCode = 20;
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
    public void callVisionApi(HomeActivity homeActivity, Bitmap bitmap, Feature feature, Uri uri, AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeLayout,RelativeLayout relativeLayoutMain) {


        if (uri != null) {
            bitmap = BitmapFactory.decodeFile(uri.getPath());
        }
        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);


        Log.e("HM", "Vision calling");

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

                //  makeJsonRequest(result);
                phoneNumber = phone.toString().replaceAll("\\[", "").replaceAll("\\]", "");

                if (phoneNumber.equals("")) {
                    phoneNumber = "No number found";
                }

                Intent intent = new Intent(homeActivity, ProfileActivity.class);

                avLoadingIndicatorView.setVisibility(View.GONE);
                avLoadingIndicatorView.hide();
                relativeLayout.setVisibility(View.GONE);
                relativeLayoutMain.setVisibility(View.VISIBLE);

                intent.putExtra("email", email);
                intent.putExtra("image", uri);
                intent.putExtra("website", website);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("result", result);
                homeActivity.startActivity(intent);
                homeActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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

    @SuppressLint("StaticFieldLeak")
    private void makeJsonRequest(String result) {

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


            }
        }.execute();


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
