package com.forzo.holdMyCard.ui.recyclerAdapter.mygroups;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

public class MyGroupsRecyclerAdapter extends RecyclerView.Adapter<MyGroupsHolder> {

    private static final String TAG = "NotificationRecyclerAda";
    MyGroupsListPresenter groupsListPresenter;

    public MyGroupsRecyclerAdapter(MyGroupsListPresenter myGroupsListPresenter) {
        this.groupsListPresenter = myGroupsListPresenter;
    }

    @NonNull
    @Override
    public MyGroupsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyGroupsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_my_groups, parent, false), groupsListPresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGroupsHolder holder, int position) {
        groupsListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return groupsListPresenter.getItemCount();
    }
}
