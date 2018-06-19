package com.forzo.holdMyCard.ui.recyclerAdapter.groupname;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

public class GroupNameRecyclerAdapter extends RecyclerView.Adapter<GroupNameHolder> {

    private static final String TAG = "GroupNameAdapter";

    GroupNameListPresenter groupNameListPresenter;

    public GroupNameRecyclerAdapter() {
    }

    public GroupNameRecyclerAdapter(GroupNameListPresenter groupNameListPresenter) {
        this.groupNameListPresenter = groupNameListPresenter;
    }


    @NonNull
    @Override
    public GroupNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_group_name, parent, false), groupNameListPresenter);

    }

    @Override
    public void onBindViewHolder(@NonNull GroupNameHolder holder, int position) {
        groupNameListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return groupNameListPresenter.getItemCount();
    }
}
