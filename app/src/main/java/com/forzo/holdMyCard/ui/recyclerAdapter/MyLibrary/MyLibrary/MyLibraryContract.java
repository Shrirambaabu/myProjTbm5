package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibrary;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibraryContract {

    interface Presenter {

        int getItemCount();

        void bindEventRow(int position, MyLibraryRowView holder);

    }

    interface MyLibraryRowView {

        void setCardName(String cardName);
        void setCardDescription(String cardDescription);
        void setCardDetails(String cardDetails);


    }
}
