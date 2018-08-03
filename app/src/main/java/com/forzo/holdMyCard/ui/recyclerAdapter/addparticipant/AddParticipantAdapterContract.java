package com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant;

import android.widget.ImageView;

import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.hanks.library.AnimateCheckBox;

import java.util.ArrayList;
import java.util.List;

public class AddParticipantAdapterContract {


    interface Presenter {

        int getItemCount();

        void setImage(String image, ImageView imageView);

        void bindEventRow(int position, AddParticipantRowView holder);

        void performClick(int adapterPosition, boolean value, AnimateCheckBox animeBox,AddParticipantRowView holder);

        void setPrevGroupList(ArrayList<MyLibrary> myGroupsArrayList);

    }

    interface AddParticipantRowView {

        void setCardName(String cardName);

        void setCardDescription(String cardDescription);

        void setCardDetails(String cardDetails);

        void setImageCard(String Image);

        void setCheckBoxState(boolean state);

        void handleGroupVisiblity();


    }
}
