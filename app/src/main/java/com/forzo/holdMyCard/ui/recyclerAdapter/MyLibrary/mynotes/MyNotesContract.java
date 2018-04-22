package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.mynotes;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotesContract {


    interface Presenter {

        int getItemCount();

        void onItemClick(int adapterPosition);

        void bindEventRow(int position, MyNotesRowView holder);

    }

    interface MyNotesRowView {

        void setCardName(String cardName);




    }
}
