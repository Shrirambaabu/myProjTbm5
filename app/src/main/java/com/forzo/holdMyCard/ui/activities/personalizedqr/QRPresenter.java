package com.forzo.holdMyCard.ui.activities.personalizedqr;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QRPresenter extends BasePresenter<QRContract.View> implements QRContract.Presenter {

    private Context context;
    private ApiService mApiService;
    private String TAG = "QR";

    QRPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void getPersonalDetails() {

        final String[] userNameProfile = new String[1];
        final String[] job = new String[1];
        final String[] companyNameProfile = new String[1];
        final String[] phoneNumberProfile = new String[1];
        final String[] phoneNumberProfile2 = new String[1];
        final String[] phoneNumberProfile3 = new String[1];
        final String[] emailIdProfile = new String[1];
        final String[] websiteProfile = new String[1];
        final String[] addressProfile = new String[1];

        mApiService.getUserProfile(PreferencesAppHelper.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BusinessCard>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BusinessCard businessCardList) {

                        userNameProfile[0] = businessCardList.getName();
                        job[0] = businessCardList.getJobTitle();
                        companyNameProfile[0] = businessCardList.getCompany();
                        phoneNumberProfile[0] = businessCardList.getPhoneNumber();
                        phoneNumberProfile2[0] = businessCardList.getPhoneNumber2();
                        phoneNumberProfile3[0] = businessCardList.getPhoneNumber3();
                        emailIdProfile[0] = businessCardList.getEmailId();
                        websiteProfile[0] = businessCardList.getWebsite();
                        addressProfile[0] = businessCardList.getAddress();
                        if (userNameProfile[0] != null) {
                            Log.e(TAG, userNameProfile[0]);
                            //   getView().setUserProfileName(userNameProfile);
                        } else {
                            userNameProfile[0] = "";
                        }
                        if (job[0] != null) {
                            Log.e(TAG, job[0]);
                            //  getView().setUserProfileJob(job);
                        } else {
                            job[0] = "";
                        }
                        if (companyNameProfile[0] != null) {
                            Log.e(TAG, companyNameProfile[0]);
                            //  getView().setUserProfileCompanyName(companyNameProfile);
                        } else {
                            companyNameProfile[0] = "";
                        }
                        if (phoneNumberProfile[0] != null) {
                            Log.e(TAG, phoneNumberProfile[0]);
                            //  getView().setUserProfilePhonePrimary(phoneNumberProfile);
                        } else {
                            phoneNumberProfile[0] = "";
                        }
                        if (phoneNumberProfile2[0] != null) {
                            Log.e(TAG, phoneNumberProfile2[0]);
                            // getView().setUserProfilePhoneSecondary(phoneNumberProfile2);
                        } else {
                            phoneNumberProfile2[0] = "";
                        }
                        if (phoneNumberProfile3[0] != null) {
                            Log.e(TAG, phoneNumberProfile3[0]);
                            //  getView().setUserProfilePhone3(phoneNumberProfile3);
                        } else {
                            phoneNumberProfile3[0] = "";
                        }
                        if (emailIdProfile[0] != null) {
                            Log.e(TAG, emailIdProfile[0]);
                            //  getView().setUserProfileEmail(emailIdProfile);
                        } else {
                            emailIdProfile[0] = "";
                        }
                        if (websiteProfile[0] != null) {
                            Log.e(TAG, websiteProfile[0]);
                            //   getView().setUserProfileWebsite(websiteProfile);
                        } else {
                            websiteProfile[0] = "";
                        }
                        if (addressProfile[0] != null) {
                            Log.e(TAG, addressProfile[0]);
                            // getView().setUserProfileAddress(addressProfile);
                        } else {
                            addressProfile[0] = "";
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        getView().userDetails(userNameProfile[0], job[0], companyNameProfile[0], phoneNumberProfile[0], phoneNumberProfile2[0], phoneNumberProfile3[0], emailIdProfile[0], websiteProfile[0], addressProfile[0]);

                    }
                });
    }

    @Override
    public void getBackgroundImage() {

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
    }

    @Override
    public void getProfileImage() {

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
}
