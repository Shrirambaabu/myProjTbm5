package com.forzo.holdMyCard.ui.fragments.mygroups;

import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

public interface MyGroupsFragmentContract {


    interface Presenter extends BaseMvpPresenter<View> {

        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);


        void populateRecyclerView(List<MyGroups> groups);

    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void showRecyclerView();

        void updateAdapter();

    }
}
