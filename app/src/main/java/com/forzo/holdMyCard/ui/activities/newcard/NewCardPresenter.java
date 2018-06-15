package com.forzo.holdMyCard.ui.activities.newcard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.utils.ImagePath_MarshMallow;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.echodev.resizer.Resizer;

import static android.app.Activity.RESULT_OK;

public class NewCardPresenter extends BasePresenter<NewCardContract.View> implements NewCardContract.Presenter {

    private ApiService mApiService;
    private Context context;
    private String TAG = "NewCardPresenter";
    private String getImageUrl;
    private Uri intentUri;

    NewCardPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());

    }


    @Override
    public void getIntentValues(Intent intent) {
        String activityType = intent.getStringExtra("ActivityAction");
        if (activityType != null) {
            if (activityType.equals("NewCard")) {
                getView().newCardActivityType();
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
}
