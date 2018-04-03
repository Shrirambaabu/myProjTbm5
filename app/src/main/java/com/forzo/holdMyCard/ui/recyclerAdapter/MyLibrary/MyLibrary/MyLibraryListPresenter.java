package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibrary;

import android.content.Context;

import com.forzo.holdMyCard.ui.models.MyLibrary;

import java.util.ArrayList;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibraryListPresenter implements MyLibraryContract.Presenter {

    private Context context;
    private ArrayList<MyLibrary> myLibraries;

    public MyLibraryListPresenter(Context context, ArrayList<MyLibrary> myLibraries) {
        this.context = context;
        this.myLibraries = myLibraries;
    }

    @Override
    public int getItemCount() {
        return (null != myLibraries ? myLibraries.size() : 0);
    }

    @Override
    public void bindEventRow(int position, MyLibraryContract.MyLibraryRowView rowView) {

        MyLibrary myLibrary=myLibraries.get(position);


        rowView.setCardName(myLibrary.getCardName());
        rowView.setCardDescription(myLibrary.getCardDescription());
        rowView.setCardDetails(myLibrary.getCardDetails());

    }
}
