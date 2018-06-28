package com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forzo.holdMyCard.R;
import com.hanks.library.AnimateCheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddParticipantAdapterHolder extends RecyclerView.ViewHolder implements AddParticipantAdapterContract.AddParticipantRowView{


    AddParticipantAdapterListPresenter createGroupListPresenter;

    @BindView(R.id.card_name)
    TextView setCardName;
    @BindView(R.id.card_description)
    TextView setCardDescription;
    @BindView(R.id.card_details)
    TextView setCardDetails;

    @BindView(R.id.anime_box)
    AnimateCheckBox animeBox;

    @BindView(R.id.card_image)
    ImageView setCardImageValue;

    AddParticipantAdapterHolder(View itemView, AddParticipantAdapterListPresenter addParticipantAdapterListPresenter) {
        super(itemView);
        this.createGroupListPresenter = addParticipantAdapterListPresenter;
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

    @Override
    public void setImageCard(String Image) {
      //  createGroupListPresenter.setImage(Image,setCardImageValue);
    }
}
