package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibraryRecyclerAdapter extends RecyclerView.Adapter<MyLibraryHolder> {

    private static final String TAG = "NotificationRecyclerAda";
    MyLibraryListPresenter currentFragmentListPresenter;

    public MyLibraryRecyclerAdapter(MyLibraryListPresenter currentFragmentListPresenter) {
        this.currentFragmentListPresenter = currentFragmentListPresenter;
    }

    @Override
    public MyLibraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyLibraryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_my_library, parent, false), currentFragmentListPresenter);
    }

    @Override
    public void onBindViewHolder(MyLibraryHolder holder, int position) {
        currentFragmentListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return currentFragmentListPresenter.getItemCount();
    }

}