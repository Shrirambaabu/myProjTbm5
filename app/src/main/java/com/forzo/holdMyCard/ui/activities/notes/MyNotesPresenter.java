package com.forzo.holdMyCard.ui.activities.notes;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotesPresenter extends BasePresenter<NotesContract.View> implements NotesContract.Presenter{


    private Context context;

    MyNotesPresenter(Context context) {
        this.context = context;
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
    public void populateRecyclerView(List<MyNotes> myNotes) {


        myNotes.addAll(DataService.getCardNameList());
        getView().updateAdapter();

    }


}
