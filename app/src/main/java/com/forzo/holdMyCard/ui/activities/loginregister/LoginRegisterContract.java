package com.forzo.holdMyCard.ui.activities.loginregister;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.fragments.mylogin.MyLogin;
import com.forzo.holdMyCard.ui.fragments.myregister.MyRegister;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;

public interface LoginRegisterContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void setupViewPager(ViewPager viewPager, SectionsStatePagerAdapter adapter,
                            MyLogin myLogin, MyRegister myRegister);

        void getIntentValues(Intent intent);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void showTabLayout();

        void statusValue(String statusValue);

    }

}
