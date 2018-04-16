package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
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
    public void onItemClick(int adapterPosition) {

        Activity activity = (Activity) context;
        Intent myLibraryIntent = new Intent(context, ProfileActivity.class);
        myLibraryIntent.putExtra("libraryProfile",""+myLibraries.get(adapterPosition).getUserId());
        context.startActivity(myLibraryIntent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void bindEventRow(int position, MyLibraryContract.MyLibraryRowView rowView) {

        MyLibrary myLibrary=myLibraries.get(position);


        rowView.setCardName(myLibrary.getCardName());
        rowView.setCardDescription(myLibrary.getCardDescription());
        rowView.setCardDetails(myLibrary.getCardDetails());

    }
}
