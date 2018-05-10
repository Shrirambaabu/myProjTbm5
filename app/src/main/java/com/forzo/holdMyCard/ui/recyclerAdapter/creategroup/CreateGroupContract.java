package com.forzo.holdMyCard.ui.recyclerAdapter.creategroup;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupContract {

    interface Presenter {

        int getItemCount();

        void bindEventRow(int position, CreateGroupRowView holder);

    }

    interface CreateGroupRowView {

        void setCardName(String cardName);
        void setCardDescription(String cardDescription);
        void setCardDetails(String cardDetails);


    }
}
