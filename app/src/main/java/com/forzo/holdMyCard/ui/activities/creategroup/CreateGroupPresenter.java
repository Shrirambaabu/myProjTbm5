package com.forzo.holdMyCard.ui.activities.creategroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupPresenter extends BasePresenter<CreateGroupContract.View> implements CreateGroupContract.Presenter{


    private Context context;

    CreateGroupPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx) {
        setupBottomNavigationSetUp(bottomNavigationViewEx);
        getView().viewBottomNavigation(bottomNavigationViewEx);
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

        String disabled=intent.getStringExtra("module");

        if (disabled!=null){
            getView().showDialog();
        }

    }


}
