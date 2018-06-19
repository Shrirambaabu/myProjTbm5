package com.forzo.holdMyCard.ui.activities.creategroupname;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.Groups;

import java.util.ArrayList;
import java.util.List;

public interface CreateGroupNameContract {


    interface Presenter extends BaseMvpPresenter<View> {
        void getIntentValues(Intent intent);

        void setupShowsRecyclerView(RecyclerView recyclerView);

        void updateDataValues(ArrayList<Groups> groupsArrayList);

        void populateRecyclerView(ArrayList<Groups> groups);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {
        void showRecyclerView();

        void updateAdapter();

        void cardNumber(int cardTotal);
    }
}
