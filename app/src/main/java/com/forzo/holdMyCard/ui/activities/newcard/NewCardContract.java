package com.forzo.holdMyCard.ui.activities.newcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public interface NewCardContract {
    interface Presenter extends BaseMvpPresenter<View> {


        void getIntentValues(Intent intent);

        void saveBusinessCard(String nameTextInputEditText, String companyTextInputEditText, String jobTitleTextInputEditText, String mobileTextInputEditText, String mobileTextInputEditText2, String mobileTextInputEditText3, String emailTextInputEditText, String websiteTextInputEditText, String addressTextInputEditText);

        void updateCard(String userId, String nameTextInputEditText, String companyTextInputEditText, String jobTitleTextInputEditText, String mobileTextInputEditText, String mobileTextInputEditText2, String mobileTextInputEditText3, String emailTextInputEditText, String websiteTextInputEditText, String addressTextInputEditText);

        boolean checkPermission(String[] permissions, int requestCode);

        void handleResult(int requestCode, int resultCode, Intent data, Uri capturedImageUri);

        void naturalProcess(String result);

        boolean checkPermission(String[] permissions);

        void requestPermissions(String[] permissions, int requestCode);

        int checkPermission(Context context, String permission);

        void callGoogleCloudVision(Uri uri, Feature feature, Uri intentUri);

        String convertGoogleResponseToString(BatchAnnotateImagesResponse response);

        String formatAnnotation(List<EntityAnnotation> entityAnnotation);

        void callWatsonAPI(String intentMessage, String intentEmail);

        void showProfileData(String libraryUserId);

        void saveQRImageName(String userId, String imageType, String imageName);

        void saveBusinessImage(Uri uri, String userId, String imageType);

        void deleteCard(String userId);

        void saveContactToPhone(String name, String mobile, String email, String companyName, String jobTitle, String address);

        void searchUserOnTwitter(String userName);

        void searchUserOnFacebook(String userName);

        void updateBusinessImage(Uri uri, String userId, String imageType);

        void setImage(String imageName);

        void setBackImage(String backImage);

    }

    interface View {
        void setImage(String image);

        void activityLoader();

        void setProfileImageUri(Uri profileImageUri);

        void libraryActivityType();

        void onPhotosReturned(Uri photoUri);

        void newCardActivityType();

        void setEmailFromAPI(String email);

        void setWebsiteFromAPI(String website);

        void dataFromAPI(String intentMessage);

        void setPhoneFromAPI(int phone, String phoneList);

        void setMobileNumber(String mobileNumber);

        void setPhoneNumber2(String mobileNumber2);

        void hideLoader();

        void sendCroppedImage(Uri resultUri);

        void showDialog();

        void setUserName(String userName);

        void setCompanyName(String companyName);

        void setJobTitle(String jobTitle);

        void setAddress(String address);

        void libraryUserId(String libraryUserId);

        void setQRImage(String qrImage);

        void savedQRProfileImage(String userId);

        void qrProfileSavedSuccessfully();

        void updateSuccess();

        void deleteProfile();

        void setModifiedTs(String modifiedTs);

        void setBackImage(String backImage);

        void setBusinessCarosuilImage(Bitmap businessCarosuilImage, String imageName);
        void setBusinessBackCarosuilImage(Bitmap businessBackCarosuilImage, String imageName);
    }
}
