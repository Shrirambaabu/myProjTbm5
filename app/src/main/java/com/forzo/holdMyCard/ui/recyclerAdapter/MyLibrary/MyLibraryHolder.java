package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibraryHolder extends RecyclerView.ViewHolder implements MyLibraryContract.MyLibraryRowView {

    MyLibraryListPresenter fragmentListPresenter;

    @BindView(R.id.card_name)
    TextView setCardName;
    @BindView(R.id.card_description)
    TextView setCardDescription;
    @BindView(R.id.card_details)
    TextView setCardDetails;


    MyLibraryHolder(View itemView, MyLibraryListPresenter fragmentListPresenter) {
        super(itemView);
        this.fragmentListPresenter = fragmentListPresenter;
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
