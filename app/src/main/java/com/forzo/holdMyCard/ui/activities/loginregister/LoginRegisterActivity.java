package com.forzo.holdMyCard.ui.activities.loginregister;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.newcard.NewCardActivity;
import com.forzo.holdMyCard.ui.fragments.mylogin.MyLogin;
import com.forzo.holdMyCard.ui.fragments.myregister.MyRegister;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginRegisterActivity extends AppCompatActivity implements LoginRegisterContract.View {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager pager;

    @Inject
    LoginRegisterPresenter loginRegisterPresenter;

    @Inject
    SectionsStatePagerAdapter adapter;
    @Inject
    MyLogin myLogin;
    @Inject
    MyRegister myRegister;

    private Context mContext = LoginRegisterActivity.this;
    private static String statusDataValue = "other";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);

        DaggerLoginRegisterComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .loginRegisterModule(new LoginRegisterModule(this))
                .build()
                .inject(this);
        loginRegisterPresenter.attach(this);
        loginRegisterPresenter.getIntentValues(getIntent());
        loginRegisterPresenter.setupViewPager(pager, adapter, myLogin, myRegister);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
                Log.d("Activity", "ON RESULT CALLED");
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }

    @Override
    public void showTabLayout() {
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void statusValue(String statusValue) {
        statusDataValue = statusValue;
    }

    @Override
    public void onBackPressed() {

        if (statusDataValue.equals("logout")) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(LoginRegisterActivity.this, MyLibraryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

}
