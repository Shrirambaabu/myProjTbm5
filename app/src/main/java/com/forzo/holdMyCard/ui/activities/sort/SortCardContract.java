package com.forzo.holdMyCard.ui.activities.sort;

import android.content.Intent;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface SortCardContract {

    interface Presenter extends BaseMvpPresenter<SortCardContract.View> {

        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

        void getIntentValues(Intent intent);
    }

    interface View {
        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);

        void showDialog();
    }

}
