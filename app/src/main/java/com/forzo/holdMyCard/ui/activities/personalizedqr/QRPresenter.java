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

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QRPresenter extends BasePresenter<QRContract.View> implements QRContract.Presenter{

    private Context context;
    private ApiService mApiService;
private String TAG="QR";
    QRPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void getPersonalDetails() {

        mApiService.getUserProfile(PreferencesAppHelper.getUserId())
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
                         //   getView().setUserProfileName(userNameProfile);
                        }else {
                            userNameProfile="";
                        }
                        if (job != null) {
                            Log.e(TAG, job);
                          //  getView().setUserProfileJob(job);
                        }else {
                            job="";
                        }
                        if (companyNameProfile != null) {
                            Log.e(TAG, companyNameProfile);
                          //  getView().setUserProfileCompanyName(companyNameProfile);
                        }else {
                            companyNameProfile="";
                        }
                        if (phoneNumberProfile != null) {
                            Log.e(TAG, phoneNumberProfile);
                          //  getView().setUserProfilePhonePrimary(phoneNumberProfile);
                        }else {
                            phoneNumberProfile="";
                        }
                        if (phoneNumberProfile2 != null) {
                            Log.e(TAG, phoneNumberProfile2);
                           // getView().setUserProfilePhoneSecondary(phoneNumberProfile2);
                        }else {
                            phoneNumberProfile2="";
                        }
                        if (phoneNumberProfile3 != null) {
                            Log.e(TAG, phoneNumberProfile3);
                          //  getView().setUserProfilePhone3(phoneNumberProfile3);
                        }else {
                            phoneNumberProfile3="";
                        }
                        if (emailIdProfile != null) {
                            Log.e(TAG, emailIdProfile);
                          //  getView().setUserProfileEmail(emailIdProfile);
                        }else {
                            emailIdProfile="";
                        }
                        if (websiteProfile != null) {
                            Log.e(TAG, websiteProfile);
                         //   getView().setUserProfileWebsite(websiteProfile);
                        }else {
                            websiteProfile="";
                        }
                        if (addressProfile != null) {
                            Log.e(TAG, addressProfile);
                           // getView().setUserProfileAddress(addressProfile);
                        }else {
                            addressProfile="";
                        }

                        getView().userDetails(userNameProfile,job,companyNameProfile,phoneNumberProfile,phoneNumberProfile2,phoneNumberProfile3,emailIdProfile,websiteProfile,addressProfile);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
