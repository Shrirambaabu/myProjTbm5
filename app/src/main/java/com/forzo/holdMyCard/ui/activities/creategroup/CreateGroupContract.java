package com.forzo.holdMyCard.ui.activities.creategroup;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shriram on 4/3/2018.
 */

public interface CreateGroupContract {
    interface Presenter extends BaseMvpPresenter<View> {


        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);

        void populateRecyclerView(List<MyLibrary> events);

        void getIntentValues(Intent intent);

        void getGroupId(ArrayList<String> stringArrayList);

        void gotoGroupName(String groupName);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void updateAdapter();

        void showDialog();

        void showRecyclerView();

        void createGroupDone();

        void createGroupId(ArrayList<String> stringArrayList);
    }
}
