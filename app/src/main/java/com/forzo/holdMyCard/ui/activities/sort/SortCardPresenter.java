package com.forzo.holdMyCard.ui.activities.sort;

import android.content.Context;
import android.content.Intent;

import com.forzo.holdMyCard.base.BasePresenter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;

/**
 * Created by Shriram on 3/29/2018.
 */

public class SortCardPresenter  extends BasePresenter<SortCardContract.View> implements SortCardContract.Presenter{


    private Context context;

    SortCardPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx) {
        setupBottomNavigationSetUp(bottomNavigationViewEx);
        getView().viewBottomNavigation(bottomNavigationViewEx);
    }

    @Override
    public void getIntentValues(Intent intent) {
        String sortDisable=intent.getStringExtra("sortMain");

        if (sortDisable!=null){

            getView().showDialog();
        }
    }
}
