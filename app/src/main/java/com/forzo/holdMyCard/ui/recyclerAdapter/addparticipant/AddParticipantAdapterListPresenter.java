package com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.activities.addparticipant.AddParticipantPresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.hanks.library.AnimateCheckBox;

import java.util.ArrayList;
import java.util.List;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

public class AddParticipantAdapterListPresenter implements AddParticipantAdapterContract.Presenter {


    private Context context;
    private ArrayList<MyLibrary> myLibraries;
    private ArrayList<String> selectedContact = new ArrayList<>();
    private ArrayList<String> newSelectedContact = new ArrayList<>();
    private ArrayList<MyLibrary> selectedGroupContact = new ArrayList<>();

    private AddParticipantPresenter addParticipantPresenter = new AddParticipantPresenter();

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
        holder.setImageCard(myLibrary.getImage());
        if (myLibrary.isSetChecked()) {
            holder.setCheckBoxState(true);
        } else {
            holder.setCheckBoxState(false);
        }
        if (myLibrary.isGroupAdded()) {
            holder.handleGroupVisiblity();
        }
    }

    @Override
    public void performClick(int adapterPosition, boolean value, AnimateCheckBox animeBox, AddParticipantAdapterContract.AddParticipantRowView holder) {

        if (value) {
            if (!selectedGroupContact.isEmpty()) {
                for (int i = 0; i <= selectedGroupContact.size() - 1; i++) {
                    selectedContact.add(selectedGroupContact.get(i).getUserId());

                    if (myLibraries.get(adapterPosition).getUserId().equals(selectedGroupContact.get(i).getUserId())) {
                        myLibraries.get(adapterPosition).setGroupAdded(true);
                        holder.handleGroupVisiblity();
                    }
                }
            }

            if (!selectedContact.contains(myLibraries.get(adapterPosition).getUserId())) {
                selectedContact.add(myLibraries.get(adapterPosition).getUserId());
                newSelectedContact.add(myLibraries.get(adapterPosition).getUserId());
                myLibraries.get(adapterPosition).setSetChecked(true);
            }
        } else {
            newSelectedContact.remove(myLibraries.get(adapterPosition).getUserId());
            selectedContact.remove(myLibraries.get(adapterPosition).getUserId());
            myLibraries.get(adapterPosition).setSetChecked(false);
        }
        if (!newSelectedContact.isEmpty()) {
            for (int j = 0; j <= newSelectedContact.size() - 1; j++) {
                Log.e("CRContactLast:" + newSelectedContact.size(), "" + newSelectedContact.get(j));
            }
        }
        addParticipantPresenter.addNewMembers(newSelectedContact);
    }

    @Override
    public void setPrevGroupList(ArrayList<MyLibrary> myGroupsArrayList) {
        this.selectedGroupContact = myGroupsArrayList;
        Log.e("Adapter", "" + selectedGroupContact.size());

        if (!selectedGroupContact.isEmpty()) {
            for (int i = 0; i <= selectedGroupContact.size() - 1; i++) {
                Log.e("UserIds", "" + selectedGroupContact.get(i).getUserId());
            }
        }
    }


    public void setFilter(ArrayList<MyLibrary> listitem, AddParticipantRecyclerAdapter addParticipantRecyclerAdapter) {
        myLibraries = new ArrayList<>();
        myLibraries.addAll(listitem);
        addParticipantRecyclerAdapter.notifyDataSetChanged();
    }

}
