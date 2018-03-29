package com.forzo.holdMyCard.ui.activities.library;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;

public class MyLibraryActivity extends AppCompatActivity implements MyLibraryContract.View {


    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationView;

    @Inject
    MyLibraryPresenter myLibraryPresenter;

    private Context mContext = MyLibraryActivity.this;

    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);


        ButterKnife.bind(this);


        DaggerMyLibraryComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        myLibraryPresenter.attach(this);

        myLibraryPresenter.bottomNavigationViewSetup(bottomNavigationView);

    }

    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
