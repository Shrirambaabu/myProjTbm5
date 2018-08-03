package com.forzo.holdMyCard.ui.activities.addparticipant;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public interface AddParticipantContract {

    interface Presenter extends BaseMvpPresenter<AddParticipantContract.View> {


        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);

        void populateRecyclerView(List<MyLibrary> myLibraries);

        void getIntentValues(Intent intent);

    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void updateAdapter();

        void showRecyclerView();

        void setGroupId(String groupId);
        void setGroupName(String groupName);
    }

}
