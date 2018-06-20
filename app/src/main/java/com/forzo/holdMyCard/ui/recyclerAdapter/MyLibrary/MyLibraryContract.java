package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary;

import android.widget.ImageView;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibraryContract {

    interface Presenter {

        int getItemCount();

        void onItemClick(int adapterPosition);

        void setImage(String image, ImageView imageView);

        void bindEventRow(int position, MyLibraryRowView holder);

    }

    interface MyLibraryRowView {

        void setCardImage(String image);

        void setCardName(String cardName);

        void setCardDescription(String cardDescription);

        void setCardDetails(String cardDetails);


    }
}
