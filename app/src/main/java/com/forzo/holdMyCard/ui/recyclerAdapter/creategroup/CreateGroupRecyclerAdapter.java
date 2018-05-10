package com.forzo.holdMyCard.ui.recyclerAdapter.creategroup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupRecyclerAdapter extends RecyclerView.Adapter<CreateGroupHolder> {

    private static final String TAG = "NotificationRecyclerAda";
    CreateGroupListPresenter createGroupListPresenter;

    public CreateGroupRecyclerAdapter(CreateGroupListPresenter createGroupListPresenter) {
        this.createGroupListPresenter = createGroupListPresenter;
    }

    @Override
    public CreateGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CreateGroupHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_create_group, parent, false), createGroupListPresenter);
    }

    @Override
    public void onBindViewHolder(CreateGroupHolder holder, int position) {
        createGroupListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return createGroupListPresenter.getItemCount();
    }

}