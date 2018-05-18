package com.forzo.holdMyCard.ui.activities.notes;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

/**
 * Created by Shriram on 4/4/2018.
 */

public interface NotesContract {

    interface Presenter extends BaseMvpPresenter<View> {


        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);


        void populateRecyclerView(List<MyNotes> myNotes, String primaryValue, String primaryImageId);

        void getIntentValues(Intent intent);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void updateAdapter();

        void showRecyclerView();

        void setNotesPrimaryValue(String profile, String profileImage);
    }

}
