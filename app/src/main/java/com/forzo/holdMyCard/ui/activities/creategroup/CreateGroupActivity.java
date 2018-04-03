package com.forzo.holdMyCard.ui.activities.creategroup;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.creategroup.CreateGroupRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;

public class CreateGroupActivity extends AppCompatActivity implements CreateGroupContract.View {


    @Inject
    CreateGroupPresenter createGroupPresenter;

    @Inject
    CreateGroupRecyclerAdapter createGroupRecyclerAdapter;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationViewEx bottomNavigationViewEx;

    @Inject
    ArrayList<MyLibrary> myLibraryArrayList;


    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    @BindView(R.id.search_groups)
    android.support.v7.widget.SearchView searchView;


    private Context mContext = CreateGroupActivity.this;

    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);



        DaggerCreateGroupComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        searchView.setQueryHint("Search for people to add");
        createGroupPresenter.attach(this);
        createGroupPresenter.bottomNavigationViewSetup(bottomNavigationViewEx);
        createGroupPresenter.setupShowsRecyclerView(recyclerView, emptyView);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(createGroupRecyclerAdapter);
        createGroupPresenter.populateRecyclerView(myLibraryArrayList);
    }

    @Override
    public void updateAdapter() {
        createGroupRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
