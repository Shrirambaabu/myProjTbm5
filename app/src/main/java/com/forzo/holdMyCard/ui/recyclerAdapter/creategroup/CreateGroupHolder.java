package com.forzo.holdMyCard.ui.recyclerAdapter.creategroup;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forzo.holdMyCard.R;
import com.hanks.library.AnimateCheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupHolder extends RecyclerView.ViewHolder implements CreateGroupContract.CreateGroupRowView,
        AnimateCheckBox.OnCheckedChangeListener {

    CreateGroupListPresenter createGroupListPresenter;
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

    CreateGroupHolder(View itemView, CreateGroupListPresenter createGroupListPresenter) {
        super(itemView);
        this.createGroupListPresenter = createGroupListPresenter;
        ButterKnife.bind(this, itemView);
        animeBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void setCardName(String cardName) {
        Log.e("CardName:", ":" + cardName);
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
        createGroupListPresenter.setImage(Image, setCardImageValue);
    }

    @Override
    public void setCheckBoxState(boolean state) {
        animeBox.setChecked(state);
    }

    @Override
    public void onCheckedChanged(View buttonView, boolean isChecked) {
        if (isChecked) {
            createGroupListPresenter.performClick(getAdapterPosition(), true, animeBox);
        } else {
            createGroupListPresenter.performClick(getAdapterPosition(), false, animeBox);
        }
    }
}
