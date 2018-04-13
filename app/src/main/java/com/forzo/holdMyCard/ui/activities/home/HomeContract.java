package com.forzo.holdMyCard.ui.activities.home;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface HomeContract {

    interface Presenter extends BaseMvpPresenter<View> {


    void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

    void callVisionApi(HomeActivity homeActivity, Bitmap bitmap, Feature feature, Uri uri, AVLoadingIndicatorView avLoadingIndicatorView, RelativeLayout relativeLayout,RelativeLayout relativeLayoutTwo);
    void onBackPress();

}

// Action callbacks. Activity/Fragment will implement
interface View {


    void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);
}
}
