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

    private Context context;
    private ArrayList<MyLibrary> myLibraries;
    private ArrayList<String> selectedContact = new ArrayList<>();

    private CreateGroupPresenter createGroupPresenter = new CreateGroupPresenter();

    public CreateGroupListPresenter(Context context, ArrayList<MyLibrary> myLibraries) {
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
    public void bindEventRow(int position, CreateGroupContract.CreateGroupRowView rowView) {

        MyLibrary myLibrary = myLibraries.get(position);

        rowView.setCardName(myLibrary.getCardName());
        rowView.setCardDescription(myLibrary.getCardDescription());
        rowView.setCardDetails(myLibrary.getCardDetails());
        rowView.setImageCard(myLibrary.getImage());

    }


    public void setfilter(ArrayList<MyLibrary> listitem, CreateGroupRecyclerAdapter createGroupRecyclerAdapter) {
        myLibraries = new ArrayList<>();
        myLibraries.addAll(listitem);
        createGroupRecyclerAdapter.notifyDataSetChanged();
    }


    @Override
    public void performClick(int adapterPosition, String value, AnimateCheckBox animeBox) {

        Log.e("ClickPosition", "" + adapterPosition);
        Log.e("ClickPositionUserId", "" + myLibraries.get(adapterPosition).getUserId());

        if (value.equals("true")) {
            selectedContact.add(myLibraries.get(adapterPosition).getUserId());
        } else if (value.equals("false")) {
            selectedContact.remove(myLibraries.get(adapterPosition).getUserId());
        }

        createGroupPresenter.getGroupId(selectedContact);
        // createGroupPresenter.getGroupId(selectedContact);
      /*  if (!selectedContact.isEmpty()) {
            int num = selectedContact.size();
            for (int i = 0; i <= num - 1; i++) {
                createGroupPresenter.getGroupId(selectedContact);
                Log.e("SelContactLast", "" + selectedContact.get(i));
            }
        }*/
    }
}
