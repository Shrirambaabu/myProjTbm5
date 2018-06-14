package com.forzo.holdMyCard.ui.activities.userprofile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.forzo.holdMyCard.utils.Utils.getBitmapLowFile;
import static com.forzo.holdMyCard.utils.Utils.getResizedBitmapFile;

public class UserProfilePresenter extends BasePresenter<UserProfileContract.View> implements UserProfileContract.Presenter {

    private Context context;
    private ApiService mApiService;
    private String TAG = "Profile";

    UserProfilePresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());

    }

    @Override
    public void getIntentValues(Intent intent) {
        String userProfile = intent.getStringExtra("userProfile");

        mApiService.getUserProfile(userProfile)
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
                        if (userNameProfile != null) {
                            Log.e(TAG, userNameProfile);
                            getView().setUserProfileName(userNameProfile);
                        }
                        if (job != null) {
                            Log.e(TAG, job);
                            getView().setUserProfileJob(job);
                        }
                        if (companyNameProfile != null) {
                            Log.e(TAG, companyNameProfile);
                            getView().setUserProfileCompanyName(companyNameProfile);
                        }
                        if (phoneNumberProfile != null) {
                            Log.e(TAG, phoneNumberProfile);
                            getView().setUserProfilePhonePrimary(phoneNumberProfile);
                        }
                        if (phoneNumberProfile2 != null) {
                            Log.e(TAG, phoneNumberProfile2);
                            getView().setUserProfilePhoneSecondary(phoneNumberProfile2);
                        }
                        if (phoneNumberProfile3 != null) {
                            Log.e(TAG, phoneNumberProfile3);
                            getView().setUserProfilePhone3(phoneNumberProfile3);
                        }
                        if (emailIdProfile != null) {
                            Log.e(TAG, emailIdProfile);
                            getView().setUserProfileEmail(emailIdProfile);
                        }
                        if (websiteProfile != null) {
                            Log.e(TAG, websiteProfile);
                            getView().setUserProfileWebsite(websiteProfile);
                        }
                        if (addressProfile != null) {
                            Log.e(TAG, addressProfile);
                            getView().setUserProfileAddress(addressProfile);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        Log.e("GetProfImage", "" + PreferencesAppHelper.getUserId());
        mApiService.getUserProfileImages(PreferencesAppHelper.getUserId(), "BCF")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BusinessCard>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<BusinessCard> businessCardList) {

                        for (int i = 0; i < businessCardList.size(); i++) {

                            BusinessCard card = new BusinessCard();


                            Log.e("userImage", "" + businessCardList.get(i).getImageType());
                            Log.e("userImage", "" + businessCardList.get(i).getPostImage());
                            getView().setBackGroundImage(businessCardList.get(i).getPostImage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                        Log.e("err", "erorr image get");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        mApiService.getUserProfileImages(PreferencesAppHelper.getUserId(), "DP")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BusinessCard>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<BusinessCard> businessCardList) {

                        for (int i = 0; i < businessCardList.size(); i++) {

                            BusinessCard card = new BusinessCard();


                            Log.e("userImage", "" + businessCardList.get(i).getImageType());
                            Log.e("userImage", "" + businessCardList.get(i).getPostImage());
                            getView().setDpImage(businessCardList.get(i).getPostImage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                        Log.e("err", "erorr image get");
                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }

    @Override
    public void updateUserBusinessImage(File businessImage, String imageType) {
      /*  Bitmap bitmapS = null;

        try {
            bitmapS = MediaStore.Images.Media.getBitmap(context.getContentResolver(), businessImage.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Bitmap bitmap = BitmapFactory.decodeFile(businessImage.getAbsolutePath());

        Bitmap newBitmap = getResizedBitmapFile(bitmap, 480, 640);

        File newFile = getBitmapLowFile(newBitmap);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image"), newFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", newFile.getName(), reqFile);


        mApiService.postUserImage(body, Integer.parseInt(PreferencesAppHelper.getUserId()), imageType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BusinessCard userChangePassword) {

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
    public void updateUserProfile(String name, String jobTitle, String companyName, String phoneOne, String phone2, String phone3, String email, String address, String website) {
        BusinessCard businessCard = new BusinessCard();
        businessCard.setUserId(PreferencesAppHelper.getUserId());
        businessCard.setName(name);
        businessCard.setCompany(companyName);
        businessCard.setJobTitle(jobTitle);
        businessCard.setPhoneNumber(phoneOne);
        businessCard.setPhoneNumber2(phone2);
        businessCard.setPhoneNumber3(phone3);
        businessCard.setEmailId(email);
        businessCard.setWebsite(website);
        businessCard.setAddress(address);

        mApiService.updateBusinessCard(businessCard)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        getView().activityLoader();
                    }

                    @Override
                    public void onNext(BusinessCard userChangePassword) {
                        Log.e("uss", "done");
                        getView().updateSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                        getView().hideLoader();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
