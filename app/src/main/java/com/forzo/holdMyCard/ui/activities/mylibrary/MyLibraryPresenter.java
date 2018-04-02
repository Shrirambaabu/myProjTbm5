package com.forzo.holdMyCard.ui.activities.mylibrary;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.fragments.mycurrentlibrary.MyCurrentLibraryFragment;
import com.forzo.holdMyCard.ui.fragments.mygroups.MyGroupsFragment;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;

/**
 * Created by Shriram on 3/29/2018.
 */

public class MyLibraryPresenter extends BasePresenter<MyLibraryContract.View> implements MyLibraryContract.Presenter {

    private Context context;

    public MyLibraryPresenter(Context context) {
        this.context = context;
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
}
