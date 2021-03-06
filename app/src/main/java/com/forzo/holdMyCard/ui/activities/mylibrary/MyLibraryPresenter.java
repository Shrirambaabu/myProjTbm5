package com.forzo.holdMyCard.ui.activities.mylibrary;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.fragments.mycurrentlibrary.MyCurrentLibraryFragment;
import com.forzo.holdMyCard.ui.fragments.mygroups.MyGroupsFragment;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.ui.models.User;
import com.forzo.holdMyCard.utils.Constants;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;

/**
 * Created by Shriram on 3/29/2018.
 */

public class MyLibraryPresenter extends BasePresenter<MyLibraryContract.View> implements MyLibraryContract.Presenter {

    private static final String TAG = "MyLibraryPresenter";
    private Context context;
    private ApiService mApiService;

    MyLibraryPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx) {
        setupBottomNavigationSetUp(bottomNavigationViewEx);
        getView().viewBottomNavigation(bottomNavigationViewEx);
    }


    @Override
    public void setupViewPager(ViewPager viewPager, SectionsStatePagerAdapter adapter,
                               MyCurrentLibraryFragment myCurrentLibraryFragment, MyGroupsFragment myGroupsFragment) {
        adapter.addFragment(myCurrentLibraryFragment, context.getString(R.string.my_library));
        adapter.addFragment(myGroupsFragment, context.getString(R.string.my_groups));
        viewPager.setAdapter(adapter);
        getView().showTabLayout();
    }

    @Override
    public void registerFirstTime() {
        if (!PreferencesAppHelper.getFirstTime()) {
            Log.e(TAG, "registerFirstTime: ");
            // <---- run your one time code here
            mApiService.newUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(User user) {
                            Log.e("UserIDNew", "" + user.getNewUser());
                            PreferencesAppHelper.setUserId(user.getNewUser());
                            PreferencesAppHelper.setFirstTime(true);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                            Log.e(TAG, "Error On New User");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void showExitDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Confirm !!!");
        alertDialog.setMessage("Are you sure you want to close this application ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(startMain);
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    public void getIntentValues(Intent intent) {

        String alpha = intent.getStringExtra("alpha");

        if (alpha != null) {
            getView().alphaValue(alpha);
        }

    }

    @Override
    public void setNavigationHeader() {
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

                        if (userNameProfile != null) {
                            Log.e(TAG, userNameProfile);
                            getView().setUserProfileName(userNameProfile);
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

        mApiService.getUserStatus(PreferencesAppHelper.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User userList) {

                        String isEnabled = userList.getIsEnabled();

                        if (isEnabled != null) {
                            Log.e(TAG, isEnabled);
                            // getView().setIsEnabled(isEnabled);
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
                            PreferencesAppHelper.setCurrentUserProfileImage(businessCardList.get(i).getPostImage());
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
                            PreferencesAppHelper.setCurrentUserBusinessImage(businessCardList.get(i).getPostImage());
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
    public void checkVersion() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            getView().setVersionNumber(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userStatus() {
        String userStatus = PreferencesAppHelper.getUserStatus();

        getView().setUserStatusUI(userStatus);
    }

    @Override
    public void fcmToken(String refreshedToken) {

        SharedPreferences pref = context.getSharedPreferences(Constants.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", refreshedToken);
        editor.commit();


        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + PreferencesAppHelper.getUserId());
        if (PreferencesAppHelper.getUserId() != null) {
            mApiService.updateFcm(PreferencesAppHelper.getUserId(), refreshedToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<MyRemainder>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<MyRemainder> myRemainderResponse) {
                            Log.e(TAG, "onNext: " + myRemainderResponse.code());
                            if (myRemainderResponse.code() != 200)
                                Toast.makeText(context, "FCM token Error!!!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            Log.e(TAG, "sendRegistrationToServer: " + refreshedToken);
        }
    }
}
