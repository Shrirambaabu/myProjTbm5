package com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.hanks.library.AnimateCheckBox;

import java.util.ArrayList;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

public class AddParticipantAdapterListPresenter implements AddParticipantAdapterContract.Presenter {


    private Context context;
    private ArrayList<MyLibrary> myLibraries;
    private ArrayList<String> selectedContact = new ArrayList<>();

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
        if (myLibrary.isSetChecked())
            holder.setCheckBoxState(true);
        else
            holder.setCheckBoxState(false);
    }

    @Override
    public void performClick(int adapterPosition, boolean value, AnimateCheckBox animeBox) {

        Log.e("SeleCUser", "" + myLibraries.get(adapterPosition).getUserId());
        if (value) {
            if (!selectedContact.contains(myLibraries.get(adapterPosition).getUserId())) {
                selectedContact.add(myLibraries.get(adapterPosition).getUserId());
                myLibraries.get(adapterPosition).setSetChecked(true);
            }
        } else {
            selectedContact.remove(myLibraries.get(adapterPosition).getUserId());
            myLibraries.get(adapterPosition).setSetChecked(false);
        }
        if (!selectedContact.isEmpty()) {
            for (int i = 0; i <= selectedContact.size() - 1; i++) {
                Log.e("CRContactLast:" + selectedContact.size(), "" + selectedContact.get(i));
            }
        }
    }


    public void setFilter(ArrayList<MyLibrary> listitem, AddParticipantRecyclerAdapter addParticipantRecyclerAdapter) {
        myLibraries = new ArrayList<>();
        myLibraries.addAll(listitem);
        addParticipantRecyclerAdapter.notifyDataSetChanged();
    }

}
