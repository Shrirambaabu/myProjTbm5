package com.forzo.holdMyCard.ui.activities.home;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface HomeContract {

    interface Presenter extends BaseMvpPresenter<View> {


    void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

    void onBackPress();
}

// Action callbacks. Activity/Fragment will implement
interface View {


    void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);
}
}
