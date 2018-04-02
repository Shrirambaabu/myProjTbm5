package com.forzo.holdMyCard.ui.activities.sort;

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

public class SortCardActivity extends AppCompatActivity implements SortCardContract.View {



    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationView;

    @Inject
    SortCardPresenter sortCardPresenter;

    private Context mContext = SortCardActivity.this;

    private static final int ACTIVITY_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);


        DaggerSortCardComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        sortCardPresenter.attach(this);

        sortCardPresenter.bottomNavigationViewSetup(bottomNavigationView);
    }

    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
