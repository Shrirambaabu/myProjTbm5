package com.forzo.holdMyCard.ui.activities.addparticipant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddParticipantPresenter extends BasePresenter<AddParticipantContract.View> implements AddParticipantContract.Presenter {


    private Context context;
    private static final String TAG = "CreateGroupPresenter";
    private ApiService mApiService;


    public AddParticipantPresenter() {
    }

    AddParticipantPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void setupShowsRecyclerView(EmptyRecyclerView emptyRecyclerView, RelativeLayout emptyView) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        emptyRecyclerView.setLayoutManager(mLayoutManager);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setHasFixedSize(true);

        emptyRecyclerView.setEmptyView(emptyView);

        getView().showRecyclerView();
    }

    @Override
    public void populateRecyclerView(List<MyLibrary> myLibraryList) {
        myLibraryList.addAll(DataService.getCurrentCardList());
        getView().updateAdapter();
    }

    @Override
    public void getIntentValues(Intent intent) {

    }
}
