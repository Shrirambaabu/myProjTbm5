package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.remainder;

/**
 * Created by Shriram on 4/5/2018.
 */

public class MyRemainderContract {


    interface Presenter {

        int getItemCount();

        void onItemClick(int adapterPosition);

        void bindEventRow(int position, MyRemainderRowView holder);

    }

    interface MyRemainderRowView {

        void setCardName(String cardName);


        void setDateTime(String dateTime);



    }
}
