package com.forzo.holdMyCard.ui.activities.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.fragments.mycurrentlibrary.MyCurrentLibraryFragment;
import com.forzo.holdMyCard.ui.fragments.mygroups.MyGroupsFragment;
import com.forzo.holdMyCard.ui.models.User;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;

/**
 * Created by Shriram on 3/29/2018.
 */

public class MyLibraryPresenter extends BasePresenter<MyLibraryContract.View> implements MyLibraryContract.Presenter {

    private Context context;
    private ApiService mApiService;

    public MyLibraryPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx) {
        setupBottomNavigationSetUp(bottomNavigationViewEx);
        getView().viewBottomNavigation(bottomNavigationViewEx);
    }


    @Override
    public void setupViewPager(ViewPager viewPager, SectionsStatePagerAdapter adapter, MyCurrentLibraryFragment myCurrentLibraryFragment, MyGroupsFragment myGroupsFragment) {
        adapter.addFragment(myCurrentLibraryFragment, "My Library");
        adapter.addFragment(myGroupsFragment,"My Groups");
        viewPager.setAdapter(adapter);
        getView().showTabLayout();
    }

    @Override
    public void setUuid() {
        uuidGenerate();
       /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here


            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }*/

    }

    private void uuidGenerate() {
        String uuid = UUID.randomUUID().toString();

        User user=new User();
        user.setUuid(uuid);

        mApiService.checkUuid(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User userUuid) {


                        Log.e("s","s");
                        Log.e("nextLib",""+userUuid.toString());
                      //  getView().savedSuccessfully();
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
}
