package com.forzo.holdMyCard.ui.recyclerAdapter.mynotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotesRecyclerAdapter extends RecyclerView.Adapter<MyNotesHolder> {

    private static final String TAG = "NotificationRecyclerAda";
    MyNotesListPresenter myNotesListPresenter;

    public MyNotesRecyclerAdapter(MyNotesListPresenter myNotesListPresenter) {
        this.myNotesListPresenter = myNotesListPresenter;
    }

    @Override
    public MyNotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyNotesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_notes, parent, false), myNotesListPresenter);
    }

    @Override
    public void onBindViewHolder(MyNotesHolder holder, int position) {
        myNotesListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return myNotesListPresenter.getItemCount();
    }

}