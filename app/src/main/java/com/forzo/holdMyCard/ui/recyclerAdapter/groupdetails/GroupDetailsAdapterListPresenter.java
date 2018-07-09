package com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.models.MyLibrary;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

public class GroupDetailsAdapterListPresenter implements GroupDetailsAdapterContract.Presenter {

    private Context context;
    private ArrayList<MyLibrary> myLibraries;
    
    public GroupDetailsAdapterListPresenter(Context context, ArrayList<MyLibrary> myLibraries) {
        this.context = context;
        this.myLibraries = myLibraries;
    }

    @Override
    public int getItemCount() {
        return (null != myLibraries ? myLibraries.size() : 0);
    }

    @Override
    public void setImage(String image, ImageView imageView) {

        Glide.with(context)
                .load(IMAGE_URL + image)
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public void bindEventRow(int position, GroupDetailsAdapterContract.GroupDetailsAdapterRowView holder) {

        MyLibrary myLibrary = myLibraries.get(position);


        //  holder.setCardImage(myLibrary.getImage());
        holder.setCardName(myLibrary.getCardName());
        holder.setCardDescription(myLibrary.getCardDescription());
        holder.setCardDetails(myLibrary.getCardDetails());
    }

    @Override
    public void longPress(int adapterPosition) {

        Log.e("LongClickPos:", "" + adapterPosition);

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Confirm !!!");
        alertDialog.setMessage("Are you sure you remove this user ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    dialog.dismiss();
                    myLibraries.remove(adapterPosition);

                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }
}
