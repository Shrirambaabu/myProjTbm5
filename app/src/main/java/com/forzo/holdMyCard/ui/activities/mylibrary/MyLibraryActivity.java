package com.forzo.holdMyCard.ui.activities.mylibrary;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.fragments.mycurrentlibrary.MyCurrentLibraryFragment;
import com.forzo.holdMyCard.ui.fragments.mygroups.MyGroupsFragment;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;

public class MyLibraryActivity extends AppCompatActivity implements MyLibraryContract.View {


    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager pager;

    @Inject
    MyLibraryPresenter myLibraryPresenter;

    @Inject
    SectionsStatePagerAdapter adapter;
    @Inject
    MyCurrentLibraryFragment myCurrentLibraryFragment;
    @Inject
    MyGroupsFragment myGroupsFragment;


    private Context mContext = MyLibraryActivity.this;

    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);


        ButterKnife.bind(this);


        DaggerMyLibraryComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .myLibraryModule(new MyLibraryModule(this))
                .build()
                .inject(this);

        myLibraryPresenter.attach(this);

        myLibraryPresenter.setupViewPager(pager, adapter, myCurrentLibraryFragment, myGroupsFragment);

        myLibraryPresenter.bottomNavigationViewSetup(bottomNavigationView);

    }

    @Override
    public void showTabLayout() {

        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
