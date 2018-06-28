package com.forzo.holdMyCard.ui.activities.groupdetails;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

public interface GroupDetailsContract {


    interface Presenter extends BaseMvpPresenter<View> {
        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);

        void populateRecyclerView(List<MyLibrary> events);

        void getIntentValues(Intent intent);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void updateAdapter();

        void showRecyclerView();
    }
}
