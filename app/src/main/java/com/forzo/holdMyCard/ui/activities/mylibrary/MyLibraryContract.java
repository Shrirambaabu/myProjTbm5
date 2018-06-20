package com.forzo.holdMyCard.ui.activities.mylibrary;

import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.fragments.mycurrentlibrary.MyCurrentLibraryFragment;
import com.forzo.holdMyCard.ui.fragments.mygroups.MyGroupsFragment;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface MyLibraryContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

        void setupViewPager(ViewPager viewPager, SectionsStatePagerAdapter adapter,
                            MyCurrentLibraryFragment myCurrentLibraryFragment, MyGroupsFragment myGroupsFragment);

        void registerFirstTime();

        void showExitDialog();

        void getIntentValues(Intent intent);

        void setNavigationHeader();

        void checkVersion();
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void showTabLayout();

        void setUserProfileName(String userProfileName);

        void setIsEnabled(String isEnabled);

        void setDpImage(String imageName);

        void setVersionNumber(String versionNumber);

        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);

        void alphaValue(String alphaValue);

    }
}
