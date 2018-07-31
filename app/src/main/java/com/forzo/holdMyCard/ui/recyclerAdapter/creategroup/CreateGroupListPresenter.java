package com.forzo.holdMyCard.ui.recyclerAdapter.creategroup;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupPresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.hanks.library.AnimateCheckBox;

import java.util.ArrayList;
import java.util.List;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupListPresenter implements CreateGroupContract.Presenter {
    private static final String TAG = "CreateGroupListPresente";
    private Context context;
    private ArrayList<MyLibrary> myLibraries;
    private ArrayList<String> selectedContact = new ArrayList<>();

    private CreateGroupPresenter createGroupPresenter = new CreateGroupPresenter();

    public CreateGroupListPresenter(Context context, ArrayList<MyLibrary> myLibraries) {
        this.context = context;
        this.myLibraries = myLibraries;
    }

    public ArrayList<String> getSelectedContact() {
        return selectedContact;
    }

    public ArrayList<MyLibrary> getMyLibrariesList() {
        return myLibraries;
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
    public void bindEventRow(int position, CreateGroupContract.CreateGroupRowView rowView) {

        MyLibrary myLibrary = myLibraries.get(position);

        rowView.setCardName(myLibrary.getCardName());
        rowView.setCardDescription(myLibrary.getCardDescription());
        rowView.setCardDetails(myLibrary.getCardDetails());
        rowView.setImageCard(myLibrary.getImage());
        if (myLibrary.isSetChecked())
            rowView.setCheckBoxState(true);
        else
            rowView.setCheckBoxState(false);
    }

    public void setFilter(ArrayList<MyLibrary> listItem, CreateGroupRecyclerAdapter createGroupRecyclerAdapter) {
        myLibraries = new ArrayList<>();
        myLibraries.addAll(listItem);
        createGroupRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void performClick(int adapterPosition, boolean value, AnimateCheckBox animeBox) {
        if (value) {
            if (!selectedContact.contains(myLibraries.get(adapterPosition).getUserId())) {
                selectedContact.add(myLibraries.get(adapterPosition).getUserId());
                myLibraries.get(adapterPosition).setSetChecked(true);
            }
        } else {
            selectedContact.remove(myLibraries.get(adapterPosition).getUserId());
            myLibraries.get(adapterPosition).setSetChecked(false);
        }
        createGroupPresenter.getGroupId(selectedContact);
    }
}
