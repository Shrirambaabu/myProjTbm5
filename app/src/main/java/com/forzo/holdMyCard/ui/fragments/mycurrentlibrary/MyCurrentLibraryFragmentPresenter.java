package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyCurrentLibraryFragmentPresenter extends BasePresenter<MyCurrentLibraryFragmentContract.View> implements MyCurrentLibraryFragmentContract.Presenter {


    private Context context;

    MyCurrentLibraryFragmentPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setupShowsRecyclerView(EmptyRecyclerView paymentCurrentRecyclerView, RelativeLayout emptyView) {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        paymentCurrentRecyclerView.setLayoutManager(mLayoutManager);
        paymentCurrentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        paymentCurrentRecyclerView.setHasFixedSize(true);

        paymentCurrentRecyclerView.setEmptyView(emptyView);

        getView().showRecyclerView();

    }

    @Override
    public void populateRecyclerView(List<MyLibrary> myLibraryList) {


        myLibraryList.addAll(DataService.getCurrentCardList());
        getView().updateAdapter();

    }
}
