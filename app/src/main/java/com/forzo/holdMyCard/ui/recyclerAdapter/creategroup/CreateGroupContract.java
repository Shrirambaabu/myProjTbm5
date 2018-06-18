package com.forzo.holdMyCard.ui.recyclerAdapter.creategroup;

import android.widget.ImageView;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupContract {

    interface Presenter {

        int getItemCount();

        void setImage(String image, ImageView imageView);

        void bindEventRow(int position, CreateGroupRowView holder);

        void performClick(int adapterPosition,String value);

    }

    interface CreateGroupRowView {

        void setCardName(String cardName);

        void setCardDescription(String cardDescription);

        void setCardDetails(String cardDetails);

        void setImageCard(String Image);


    }
}
