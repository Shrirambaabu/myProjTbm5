package com.forzo.holdMyCard.ui.activities.creategroup;

import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

/**
 * Created by Shriram on 4/3/2018.
 */

public interface CreateGroupContract {
    interface Presenter extends BaseMvpPresenter<View> {


        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);
        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

        void populateRecyclerView(List<MyLibrary> events);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void updateAdapter();

        void showRecyclerView();
        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);
    }
}
