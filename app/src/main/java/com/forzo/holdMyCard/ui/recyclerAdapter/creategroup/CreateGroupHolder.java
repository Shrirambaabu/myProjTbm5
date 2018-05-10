package com.forzo.holdMyCard.ui.recyclerAdapter.creategroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupHolder extends RecyclerView.ViewHolder implements CreateGroupContract.CreateGroupRowView {

    CreateGroupListPresenter createGroupListPresenter;

    @BindView(R.id.card_name)
    TextView setCardName;
    @BindView(R.id.card_description)
    TextView setCardDescription;
    @BindView(R.id.card_details)
    TextView setCardDetails;


    CreateGroupHolder(View itemView, CreateGroupListPresenter createGroupListPresenter) {
        super(itemView);
        this.createGroupListPresenter = createGroupListPresenter;
        ButterKnife.bind(this, itemView);
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
}
