package com.forzo.holdMyCard.ui.activities.userprofile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
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
    public void loadProfileVales() {

        mApiService.getUserProfileData(PreferencesAppHelper.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BusinessCard>() {
                    @Override
                    public void onSuccess(BusinessCard businessCard) {

                        if (businessCard.getName() != null) {
                            Log.e(TAG, businessCard.getName());
                            getView().setUserProfileName(businessCard.getName());
                        }
                        if (businessCard.getJobTitle() != null) {
                            Log.e(TAG, businessCard.getJobTitle());
                            getView().setUserProfileJob(businessCard.getJobTitle());
                        }
                        if (businessCard.getCompany() != null) {
                            Log.e(TAG, businessCard.getCompany());
                            getView().setUserProfileCompanyName(businessCard.getCompany());
                        }
                        if (businessCard.getPhoneNumber() != null) {
                            Log.e(TAG, businessCard.getPhoneNumber());
                            getView().setUserProfilePhonePrimary(businessCard.getPhoneNumber());
                        }
                        if (businessCard.getPhoneNumber2() != null) {
                            Log.e(TAG, businessCard.getPhoneNumber2());
                            getView().setUserProfilePhoneSecondary(businessCard.getPhoneNumber2());
                        }
                        if (businessCard.getPhoneNumber3() != null) {
                            Log.e(TAG, businessCard.getPhoneNumber3());
                            getView().setUserProfilePhone3(businessCard.getPhoneNumber3());
                        }
                        if (businessCard.getEmailId() != null) {
                            Log.e(TAG, businessCard.getEmailId());
                            getView().setUserProfileEmail(businessCard.getEmailId());
                        }
                        if (businessCard.getWebsite() != null) {
                            Log.e(TAG, businessCard.getWebsite());
                            getView().setUserProfileWebsite(businessCard.getWebsite());
                        }
                        if (businessCard.getAddress() != null) {
                            Log.e(TAG, businessCard.getAddress());
                            getView().setUserProfileAddress(businessCard.getAddress());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void loadDisplayPicture() {

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

                            Log.e("userImage", "" + businessCardList.get(i).getImageType());
                            Log.e("userImage", "" + businessCardList.get(i).getPostImage());
                            getView().setDpImage(businessCardList.get(i).getPostImage());
                            setDpImage(businessCardList.get(i).getPostImage());
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
    public void loadCoverImage() {
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

                            Log.e("userImage", "" + businessCardList.get(i).getImageType());
                            Log.e("userImage", "" + businessCardList.get(i).getPostImage());
                            getView().setBackGroundImage(businessCardList.get(i).getPostImage());
                            setImage(businessCardList.get(i).getPostImage());
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

    @SuppressLint("StaticFieldLeak")
    @Override
    public void setImage(String imageName) {
        new AsyncTask<Object, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Object... params) {
                Bitmap myBitmap = null;
                try {
                    URL url = new URL(IMAGE_URL + imageName);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("getBusiMsg", "" + e.getLocalizedMessage());
                    Log.e("getBusiMsg2", "" + e.getMessage());

                }
                return myBitmap;
            }

            protected void onPostExecute(Bitmap response) {
                getView().setBusinessCarosuilImage(response, imageName);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void setDpImage(String backImage) {

        new AsyncTask<Object, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Object... params) {
                Bitmap myBitmap = null;
                try {
                    URL url = new URL(IMAGE_URL + backImage);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);


                } catch (IOException e) {
                    e.printStackTrace();

                    Log.e("getDpMsg", "" + e.getLocalizedMessage());
                    Log.e("getDpMsg2", "" + e.getMessage());
                }
                return myBitmap;
            }

            protected void onPostExecute(Bitmap response) {
                getView().setBusinessDPCarosuilImage(response, backImage);
            }
        }.execute();
    }


    @Override
    public void postUserBusinessImage(File businessImage, String imageType) {

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
    public void updateUserBusinessImage(File businessImage, String imageType) {


        Bitmap bitmap = BitmapFactory.decodeFile(businessImage.getAbsolutePath());

        Bitmap newBitmap = getResizedBitmapFile(bitmap, 480, 640);

        File newFile = getBitmapLowFile(newBitmap);

        Log.e("FileSize", "" + newFile.length());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image"), newFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", newFile.getName(), reqFile);


        mApiService.updateUserImage(Integer.parseInt(PreferencesAppHelper.getUserId()), imageType, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getView().activityLoader();

                    }

                    @Override
                    public void onNext(BusinessCard userChangePassword) {
                        Log.e("SuccStatus", "image" + userChangePassword.getUpdateImage());
                        Log.e("Succ", "image");
                        getView().hideLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                        Log.e("error", "ErrorUpdatingImage");
                        getView().hideLoader();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoader();
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
