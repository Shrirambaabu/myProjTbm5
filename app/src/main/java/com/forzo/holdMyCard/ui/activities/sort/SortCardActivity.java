package com.forzo.holdMyCard.ui.activities.sort;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.login.LoginActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        sortCardPresenter.getIntentValues(getIntent());
        sortCardPresenter.bottomNavigationViewSetup(bottomNavigationView);
    }

    @Override
    public void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx) {
        enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void showDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(SortCardActivity.this).create();
        alertDialog.setTitle("Alert !!!");
        alertDialog.setMessage("Module disabled for Milestone 1 release");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> {
                    dialog.dismiss();
                    Intent intentMain = new Intent(SortCardActivity.this, MyLibraryActivity.class);
                    intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentMain);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                });
        alertDialog.show();
    }

    @OnClick(R.id.login_reg)
    public void loginPage() {

        Intent libraryIntent = new Intent(SortCardActivity.this, LoginActivity.class);
        startActivity(libraryIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
