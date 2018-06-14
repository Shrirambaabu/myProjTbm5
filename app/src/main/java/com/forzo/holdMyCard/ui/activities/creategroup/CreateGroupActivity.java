package com.forzo.holdMyCard.ui.activities.creategroup;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.creategroup.CreateGroupRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;
import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

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

    TextView mNext;
    private Context mContext = CreateGroupActivity.this;

    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);

        backButtonOnToolbar(CreateGroupActivity.this);

        DaggerCreateGroupComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        searchView.setQueryHint("Search for people to add");
        createGroupPresenter.attach(this);
        createGroupPresenter.getIntentValues(getIntent());
        createGroupPresenter.bottomNavigationViewSetup(bottomNavigationViewEx);
        createGroupPresenter.setupShowsRecyclerView(recyclerView, emptyView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_group, menu);//Menu Resource, Menu

        MenuItem searchItem = menu.findItem(R.id.action_next);
        mNext = (TextView) MenuItemCompat.getActionView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_next:

                Toast.makeText(getApplicationContext(),"Next",Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(CreateGroupActivity.this).create();
        alertDialog.setTitle("Alert !!!");
        alertDialog.setMessage("Module disabled for Milestone 1 release");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent intentMain = new Intent(CreateGroupActivity.this, MyLibraryActivity.class);
                    intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentMain);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                });
        alertDialog.show();
    }

    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
