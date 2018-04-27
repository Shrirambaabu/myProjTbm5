package com.forzo.holdMyCard.ui.activities.remainder;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

/**
 * Created by Shriram on 4/5/2018.
 */

public interface ReminderContract {

    interface Presenter extends BaseMvpPresenter<View> {


        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);


        void populateRecyclerView(List<MyRemainder> myRemainders,String s,String  libraryImageValue);
        void getIntentValues(Intent intent);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void updateAdapter();

        void showRecyclerView();

        void setReminderPrimaryValue(String reminderPrimaryValue,String  profileImage);
    }

}
