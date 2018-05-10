package com.forzo.holdMyCard.ui.recyclerAdapter.remainder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.forzo.holdMyCard.ui.activities.remainderdetails.RemainderDetailsActivity;
import com.forzo.holdMyCard.ui.models.MyRemainder;

import java.util.ArrayList;
import java.util.Date;

import static com.forzo.holdMyCard.utils.Utils.dateReminderToUI;
import static com.forzo.holdMyCard.utils.Utils.dateToUI;
import static com.forzo.holdMyCard.utils.Utils.gmtToLocal;
import static com.forzo.holdMyCard.utils.Utils.newUtcTimeFormat;
import static com.forzo.holdMyCard.utils.Utils.timeToUi;

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
        Intent remainderDetailsIntent = new Intent(context, RemainderDetailsActivity.class);
       /* remainderDetailsIntent.putExtra("remainDesc",""+myRemainders.get(adapterPosition).getName());
        remainderDetailsIntent.putExtra("remainTime",""+myRemainders.get(adapterPosition).getTime());
        remainderDetailsIntent.putExtra("remainDate",""+myRemainders.get(adapterPosition).getDate());*/
        remainderDetailsIntent.putExtra("remainId",""+myRemainders.get(adapterPosition).getId());
        remainderDetailsIntent.putExtra("libraryProfile", "" + myRemainders.get(adapterPosition).getLibraryProfileId());
        remainderDetailsIntent.putExtra("libraryProfileImage", "" + myRemainders.get(adapterPosition).getLibraryProfileImage());
        context.startActivity(remainderDetailsIntent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void bindEventRow(int position, MyRemainderContract.MyRemainderRowView rowView) {

        MyRemainder notes=myRemainders.get(position);

        String time=notes.getTime();

        time=timeToUi(time);


        String date=dateReminderToUI(notes.getDate());
        String reminderDate= dateToUI(notes.getDate());

        Date convertUtc= gmtToLocal(reminderDate+" "+time);

        String timeFinal=newUtcTimeFormat(convertUtc);

        rowView.setCardName(notes.getName());
        rowView.setDateTime(date+" "+timeFinal);



    }
}
