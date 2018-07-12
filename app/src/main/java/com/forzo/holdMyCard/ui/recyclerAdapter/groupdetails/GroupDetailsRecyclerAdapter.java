package com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

public class GroupDetailsRecyclerAdapter extends RecyclerView.Adapter<GroupDetailsAdapterHolder> {

    GroupDetailsAdapterListPresenter currentFragmentListPresenter;


    public GroupDetailsRecyclerAdapter(GroupDetailsAdapterListPresenter currentFragmentListPresenter) {
        this.currentFragmentListPresenter = currentFragmentListPresenter;
    }

    public GroupDetailsRecyclerAdapter() {
    }

    @NonNull
    @Override
    public GroupDetailsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupDetailsAdapterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_my_library, parent, false), currentFragmentListPresenter,this);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupDetailsAdapterHolder holder, int position) {
        currentFragmentListPresenter.bindEventRow(position, holder,this);
    }

    @Override
    public int getItemCount() {
        return currentFragmentListPresenter.getItemCount();
    }
}
