package com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant;

import android.widget.ImageView;

import com.hanks.library.AnimateCheckBox;

public class AddParticipantAdapterContract {


    interface Presenter {

        int getItemCount();

        void setImage(String image, ImageView imageView);

        void bindEventRow(int position, AddParticipantRowView holder);

        void performClick(int adapterPosition, boolean value, AnimateCheckBox animeBox);

    }

    interface AddParticipantRowView {

        void setCardName(String cardName);

        void setCardDescription(String cardDescription);

        void setCardDetails(String cardDetails);

        void setImageCard(String Image);

        void setCheckBoxState(boolean state);

    }
}
