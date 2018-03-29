package com.forzo.holdMyCard.ui.activities.library;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface MyLibraryContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

         }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);


    }
}
