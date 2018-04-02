package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

/**
 * Created by Shriram on 3/31/2018.
 */

public interface MyCurrentLibraryFragmentContract {


    interface Presenter extends BaseMvpPresenter<View> {

        void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView);


        void populateRecyclerView(List<MyLibrary> events);

    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

        void showRecyclerView();

        void updateAdapter();

    }

}
