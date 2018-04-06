package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.remainder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.forzo.holdMyCard.ui.activities.remainderdetails.RemainderDetailsActivity;
import com.forzo.holdMyCard.ui.models.MyRemainder;

import java.util.ArrayList;

/**
 * Created by Shriram on 4/5/2018.
 */

public class MyRemainderListPresenter implements MyRemainderContract.Presenter {

    private Context context;
    private ArrayList<MyRemainder> myRemainders;

    public MyRemainderListPresenter(Context context, ArrayList<MyRemainder> myRemainders) {
        this.context = context;
        this.myRemainders = myRemainders;
    }

    @Override
    public int getItemCount() {
        return (null != myRemainders ? myRemainders.size() : 0);
    }

    @Override
    public void onItemClick(int adapterPosition) {

        Activity activity = (Activity) context;
        Intent eventDetailsIntent = new Intent(context, RemainderDetailsActivity.class);
        context.startActivity(eventDetailsIntent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void bindEventRow(int position, MyRemainderContract.MyRemainderRowView rowView) {

        MyRemainder notes=myRemainders.get(position);

        rowView.setCardName(notes.getName());
        rowView.setDateTime(notes.getDateTime());


    }
}