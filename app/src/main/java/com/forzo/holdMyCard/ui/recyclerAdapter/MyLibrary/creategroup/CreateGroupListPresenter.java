package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.creategroup;

import android.content.Context;

import com.forzo.holdMyCard.ui.models.MyLibrary;

import java.util.ArrayList;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupListPresenter implements CreateGroupContract.Presenter {

    private Context context;
    private ArrayList<MyLibrary> myLibraries;

    public CreateGroupListPresenter(Context context, ArrayList<MyLibrary> myLibraries) {
        this.context = context;
        this.myLibraries = myLibraries;
    }

    @Override
    public int getItemCount() {
        return (null != myLibraries ? myLibraries.size() : 0);
    }

    @Override
    public void bindEventRow(int position, CreateGroupContract.CreateGroupRowView rowView) {

        MyLibrary myLibrary=myLibraries.get(position);


        rowView.setCardName(myLibrary.getCardName());
        rowView.setCardDescription(myLibrary.getCardDescription());
        rowView.setCardDetails(myLibrary.getCardDetails());

    }
}
