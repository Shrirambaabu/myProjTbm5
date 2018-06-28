package com.forzo.holdMyCard.ui.activities.groupdetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

public class GroupDetailsPresenter extends BasePresenter<GroupDetailsContract.View> implements GroupDetailsContract.Presenter {

    private static final String TAG = "GroupDetailsPresenter";
    private Context context;
    private ApiService mApiService;

    GroupDetailsPresenter(Context context) {
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

        String val = intent.getStringExtra("adapterPosition");

        if (val != null) {
            Log.e("GroupPosition", "" + val);
        }
    }
}
