package com.forzo.holdMyCard.ui.recyclerAdapter.mynotes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotesHolder extends RecyclerView.ViewHolder implements MyNotesContract.MyNotesRowView, View.OnClickListener {

    MyNotesListPresenter fragmentListPresenter;

    @BindView(R.id.note_name)
    TextView noteName;


    MyNotesHolder(View itemView, MyNotesListPresenter fragmentListPresenter) {
        super(itemView);
        this.fragmentListPresenter = fragmentListPresenter;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        fragmentListPresenter.onItemClick(getAdapterPosition());
    }

    @Override
    public void setCardName(String cardName) {


        noteName.setText(cardName);

    }


}
