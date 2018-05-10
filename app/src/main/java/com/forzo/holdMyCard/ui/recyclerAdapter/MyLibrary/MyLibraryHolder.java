package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibraryHolder extends RecyclerView.ViewHolder implements MyLibraryContract.MyLibraryRowView, View.OnClickListener {

    MyLibraryListPresenter fragmentListPresenter;

    @BindView(R.id.card_name)
    TextView setCardName;
    @BindView(R.id.card_description)
    TextView setCardDescription;
    @BindView(R.id.card_details)
    TextView setCardDetails;
    @BindView(R.id.card_image)
    ImageView setCardImageValue;


    MyLibraryHolder(View itemView, MyLibraryListPresenter fragmentListPresenter) {
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
    public void setCardImage(String image) {

        fragmentListPresenter.setImage(image,setCardImageValue);


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
