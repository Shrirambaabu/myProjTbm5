package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.activities.newcard.NewCardActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;

import java.util.ArrayList;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

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
        Intent myLibraryIntent = new Intent(context, NewCardActivity.class);
        myLibraryIntent.putExtra("libraryProfile", "" + myLibraries.get(adapterPosition).getUserId());
        myLibraryIntent.putExtra("libraryProfileImage", "" + myLibraries.get(adapterPosition).getImage());
        myLibraryIntent.putExtra("ActivityAction","MyLibrary");
        context.startActivity(myLibraryIntent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void setImage(String image, ImageView imageView) {

        Glide.with(context)
                .load(IMAGE_URL + image)
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public void bindEventRow(int position, MyLibraryContract.MyLibraryRowView rowView) {

        MyLibrary myLibrary = myLibraries.get(position);


        rowView.setCardImage(myLibrary.getImage());
        rowView.setCardName(myLibrary.getCardName());
        rowView.setCardDescription(myLibrary.getCardDescription());
        rowView.setCardDetails(myLibrary.getCardDetails());

    }


    public void setfilter(ArrayList<MyLibrary> listitem,MyLibraryRecyclerAdapter myLibraryRecyclerAdapter) {
        myLibraries = new ArrayList<>();
        myLibraries.addAll(listitem);
        myLibraryRecyclerAdapter.notifyDataSetChanged();
    }

}
