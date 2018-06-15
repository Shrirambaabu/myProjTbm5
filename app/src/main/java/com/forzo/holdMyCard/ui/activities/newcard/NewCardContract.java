package com.forzo.holdMyCard.ui.activities.newcard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;

import java.util.List;

public interface NewCardContract {
    interface Presenter extends BaseMvpPresenter<View> {


        void getIntentValues(Intent intent);

        void saveBusinessCard(String nameTextInputEditText, String companyTextInputEditText, String jobTitleTextInputEditText, String mobileTextInputEditText, String mobileTextInputEditText2, String mobileTextInputEditText3, String emailTextInputEditText, String websiteTextInputEditText, String addressTextInputEditText);

        boolean checkPermission(String[] permissions, int requestCode);

        void handleResult(int requestCode, int resultCode, Intent data, Uri capturedImageUri);

        void naturalProcess(String result);

        boolean checkPermission(String[] permissions);

        void requestPermissions(String[] permissions, int requestCode);

        int checkPermission(Context context, String permission);

        void callGoogleCloudVision(Uri uri, Feature feature, Uri intentUri);

        String convertGoogleResponseToString(BatchAnnotateImagesResponse response);

        String formatAnnotation(List<EntityAnnotation> entityAnnotation);

        void callWatsonAPI(String intentMessage,String intentEmail);
    }

    interface View {
        void setImage(String image);

        void activityLoader();

        void setProfileImageUri(Uri profileImageUri);
        void onPhotosReturned(Uri photoUri);

        void newCardActivityType();

        void setEmailFromAPI(String email);

        void setWebsiteFromAPI(String website);

        void dataFromAPI(String intentMessage);

        void setPhoneFromAPI(int phone, String phoneList);

        void hideLoader();

        void sendCroppedImage(Uri resultUri);

        void showDialog();

        void setUserName(String userName);

        void setCompanyName(String companyName);

        void setJobTitle(String jobTitle);

        void setAddress(String address);

    }
}
