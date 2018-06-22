package com.forzo.holdMyCard.ui.activities.loginregister;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.fragments.mylogin.MyLogin;
import com.forzo.holdMyCard.ui.fragments.myregister.MyRegister;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;

public class LoginRegisterPresenter extends BasePresenter<LoginRegisterContract.View> implements LoginRegisterContract.Presenter{

    private static final String TAG = "LoginRegisterPresenter";
    private Context context;
    private ApiService mApiService;

    LoginRegisterPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void setupViewPager(ViewPager viewPager, SectionsStatePagerAdapter adapter, MyLogin myLogin, MyRegister myRegister) {

        adapter.addFragment(myLogin, "Login");
        adapter.addFragment(myRegister, "Register");
        viewPager.setAdapter(adapter);
        getView().showTabLayout();

    }

    @Override
    public void getIntentValues(Intent intent) {
        String status=intent.getStringExtra("status");
        if (status!=null){
            getView().statusValue(status);
        }
    }
}
