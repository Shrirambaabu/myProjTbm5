package com.forzo.holdMyCard.ui.recyclerAdapter.remainder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shriram on 4/5/2018.
 */

public class MyRemainderHolder extends RecyclerView.ViewHolder implements MyRemainderContract.MyRemainderRowView, View.OnClickListener {

    MyRemainderListPresenter myRemainderListPresenter;

    @BindView(R.id.remainder_name)
    TextView remainderName;

    @BindView(R.id.remainder_date)
    TextView remainderDate;


    MyRemainderHolder(View itemView, MyRemainderListPresenter myRemainderListPresenter) {
        super(itemView);
        this.myRemainderListPresenter = myRemainderListPresenter;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        myRemainderListPresenter.onItemClick(getAdapterPosition());
    }

    @Override
    public void setCardName(String cardName) {


        remainderName.setText(cardName);

    }

    @Override
    public void setDateTime(String dateTime) {

        remainderDate.setText(dateTime);
    }


}

