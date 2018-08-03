package com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

public class AddParticipantRecyclerAdapter extends RecyclerView.Adapter<AddParticipantAdapterHolder> {

    AddParticipantAdapterListPresenter addParticipantAdapterListPresenter;


    public AddParticipantRecyclerAdapter(AddParticipantAdapterListPresenter currentFragmentListPresenter) {
        this.addParticipantAdapterListPresenter = currentFragmentListPresenter;
    }


    @NonNull
    @Override
    public AddParticipantAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddParticipantAdapterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_add_group_members, parent, false), addParticipantAdapterListPresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull AddParticipantAdapterHolder holder, int position) {
        addParticipantAdapterListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return addParticipantAdapterListPresenter.getItemCount();
    }
}
