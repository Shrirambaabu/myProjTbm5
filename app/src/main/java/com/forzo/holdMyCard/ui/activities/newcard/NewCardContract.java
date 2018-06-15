package com.forzo.holdMyCard.ui.activities.newcard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.forzo.holdMyCard.base.BaseMvpPresenter;

public interface NewCardContract {
    interface Presenter extends BaseMvpPresenter<View> {


        void getIntentValues(Intent intent);

        void saveBusinessCard(String nameTextInputEditText, String companyTextInputEditText, String jobTitleTextInputEditText, String mobileTextInputEditText, String mobileTextInputEditText2, String mobileTextInputEditText3, String emailTextInputEditText, String websiteTextInputEditText, String addressTextInputEditText);

        boolean checkPermission(String[] permissions, int requestCode);

        void handleResult(int requestCode, int resultCode, Intent data, Uri capturedImageUri);

        boolean checkPermission(String[] permissions);

        void requestPermissions(String[] permissions, int requestCode);

        int checkPermission(Context context, String permission);
    }

    interface View {
        void setImage(String image);

        void activityLoader();

        void onPhotosReturned(Uri photoUri);

        void newCardActivityType();

        void hideLoader();

        void sendCroppedImage(Uri resultUri);

        void showDialog();

    }
}
