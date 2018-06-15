package com.forzo.holdMyCard.ui.activities.mylibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupActivity;
import com.forzo.holdMyCard.ui.activities.home.HomeActivity;
import com.forzo.holdMyCard.ui.activities.loginregister.LoginRegisterActivity;
import com.forzo.holdMyCard.ui.activities.newcard.NewCardActivity;
import com.forzo.holdMyCard.ui.activities.personalizedqr.QRActivity;
import com.forzo.holdMyCard.ui.activities.userprofile.UserProfileActivity;
import com.forzo.holdMyCard.ui.fragments.mycurrentlibrary.MyCurrentLibraryFragment;
import com.forzo.holdMyCard.ui.fragments.mygroups.MyGroupsFragment;
import com.forzo.holdMyCard.utils.Constants;
import com.forzo.holdMyCard.utils.NotificationUtils;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jackandphantom.circularimageview.CircleImage;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
import static com.forzo.holdMyCard.utils.BottomNavigationHelper.enableNavigation;

public class MyLibraryActivity extends AppCompatActivity implements MyLibraryContract.View {


    private static final int ACTIVITY_NUM = 1;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager pager;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;/*
    @BindView(R.id.nav_header_layout)
    RelativeLayout relativeLayoutHeader;*/

    @Inject
    MyLibraryPresenter myLibraryPresenter;

    @Inject
    SectionsStatePagerAdapter adapter;
    @Inject
    MyCurrentLibraryFragment myCurrentLibraryFragment;
    @Inject
    MyGroupsFragment myGroupsFragment;
    private SearchView mSearchView;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Context mContext = MyLibraryActivity.this;
    private TextView registerStatus;
    private CircleImage profImage;
    private TextView profName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        DaggerMyLibraryComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .myLibraryModule(new MyLibraryModule(this))
                .build()
                .inject(this);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Objects.equals(intent.getAction(), Constants.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("remainId");
                    if (message != null) {
                        Log.e("tag", "onReceive: " + message);
                        MediaPlayer mMediaPlayer;
                        mMediaPlayer = MediaPlayer.create(MyLibraryActivity.this, R.raw.notification);
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mMediaPlayer.setLooping(false);
                        mMediaPlayer.start();
                    }
                }
            }
        };

        myLibraryPresenter.attach(this);
        myLibraryPresenter.registerFirstTime();
        myLibraryPresenter.setupViewPager(pager, adapter, myCurrentLibraryFragment, myGroupsFragment);
        View headerview = navigationView.getHeaderView(0);
        RelativeLayout relativeLayoutHeader = (RelativeLayout) headerview.findViewById(R.id.nav_header_layout);
        registerStatus = (TextView) headerview.findViewById(R.id.reg_status);
        profImage = (CircleImage) headerview.findViewById(R.id.circleImage);
        profName = (TextView) headerview.findViewById(R.id.prof_name);

        myLibraryPresenter.setNavigationHeader();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(false);
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.library:

                        Log.e("Naveigation", "Library");

                        break;
                    case R.id.new_card:
                        Intent newCard = new Intent(MyLibraryActivity.this, NewCardActivity.class);
                        newCard.putExtra("ActivityAction","NewCard");
                        startActivity(newCard);
                        break;
                    case R.id.personalized_qr:

                        Intent qrIntent = new Intent(MyLibraryActivity.this, QRActivity.class);
                        startActivity(qrIntent);
                        break;
                    case R.id.register:
                        Intent loginIntent = new Intent(MyLibraryActivity.this, LoginRegisterActivity.class);
                        startActivity(loginIntent);
                        break;
                    case R.id.logout:

                        Intent logoutIntent = new Intent(MyLibraryActivity.this, LoginRegisterActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        PreferencesAppHelper.setCurrentUserBusinessImage("");
                        PreferencesAppHelper.setCurrentUserProfileImage("");
                        startActivity(logoutIntent);
                        break;

                }

                return true;
            }
        });

        relativeLayoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.closeDrawer(GravityCompat.START);
                if (PreferencesAppHelper.getUserId() != null) {
                    Intent showProfileIntent = new Intent(MyLibraryActivity.this, UserProfileActivity.class);
                    showProfileIntent.putExtra("userProfile", PreferencesAppHelper.getUserId());
                    startActivity(showProfileIntent);
                }
            }
        });

    }

    @Override
    public void showTabLayout() {

        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void setUserProfileName(String userProfileName) {
        profName.setText(userProfileName);
    }

    @Override
    public void setDpImage(String imageName) {
        Glide.with(mContext)
                .load(IMAGE_URL + imageName)
                .thumbnail(0.1f)
                .into(profImage);
    }


    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    @Override
    public boolean onSupportNavigateUp() {

        super.onSupportNavigateUp();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            myLibraryPresenter.showExitDialog();
        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu_item, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_view_menu_item, menu);//Menu Resource, Menu

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mSearchView.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                return true;

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_create:
                Intent intentSave = new Intent(MyLibraryActivity.this, CreateGroupActivity.class);
                // intentSave.putExtra("module", "disabled");
                startActivity(intentSave);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
