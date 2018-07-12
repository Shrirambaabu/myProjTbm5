package com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupDetailsAdapterHolder extends RecyclerView.ViewHolder implements GroupDetailsAdapterContract.GroupDetailsAdapterRowView, View.OnLongClickListener {


    GroupDetailsAdapterListPresenter fragmentListPresenter;

    @BindView(R.id.card_name)
    TextView setCardName;
    @BindView(R.id.card_description)
    TextView setCardDescription;
    @BindView(R.id.card_details)
    TextView setCardDetails;
    @BindView(R.id.card_image)
    ImageView setCardImageValue;

    public GroupDetailsAdapterHolder(View itemView, GroupDetailsAdapterListPresenter groupDetailsAdapterListPresenter,GroupDetailsRecyclerAdapter groupDetailsRecyclerAdapter) {
        super(itemView);
        this.fragmentListPresenter = groupDetailsAdapterListPresenter;
        ButterKnife.bind(this, itemView);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void setCardImage(String image) {

      //  fragmentListPresenter.setImage(image,setCardImageValue);


    }

    @Override
    public void setCardName(String cardName) {


        setCardName.setText(cardName);

    }

    @Override
    public void setCardDescription(String cardDescription) {


        setCardDescription.setText(cardDescription);
    }

    @Override
    public void setCardDetails(String cardDetails) {


        setCardDetails.setText(cardDetails);
    }

    @Override
    public boolean onLongClick(View v) {

        fragmentListPresenter.longPress(getAdapterPosition());
        return true;
    }
}
