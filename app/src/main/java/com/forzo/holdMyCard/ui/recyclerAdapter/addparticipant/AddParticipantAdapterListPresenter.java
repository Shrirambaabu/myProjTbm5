package com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.models.MyLibrary;

import java.util.ArrayList;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

public class AddParticipantAdapterListPresenter implements AddParticipantAdapterContract.Presenter {


    private Context context;
    private ArrayList<MyLibrary> myLibraries;

    public AddParticipantAdapterListPresenter(Context context, ArrayList<MyLibrary> myLibraries) {
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
    public void bindEventRow(int position, AddParticipantAdapterContract.AddParticipantRowView holder) {

        MyLibrary myLibrary = myLibraries.get(position);


        holder.setCardName(myLibrary.getCardName());
        holder.setCardDescription(myLibrary.getCardDescription());
        holder.setCardDetails(myLibrary.getCardDetails());
        //   holder.setImageCard(myLibrary.getImage());
    }

    @Override
    public void performClick(int adapterPosition, String value) {

    }


    public void setFilter(ArrayList<MyLibrary> listitem,AddParticipantRecyclerAdapter addParticipantRecyclerAdapter) {
        myLibraries = new ArrayList<>();
        myLibraries.addAll(listitem);
        addParticipantRecyclerAdapter.notifyDataSetChanged();
    }

}
