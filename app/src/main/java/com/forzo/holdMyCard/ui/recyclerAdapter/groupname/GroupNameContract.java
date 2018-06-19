package com.forzo.holdMyCard.ui.recyclerAdapter.groupname;

import android.widget.ImageView;

public class GroupNameContract {


    interface Presenter {
        int getItemCount();

        void bindEventRow(int position, GroupNameRowView holder);

        void setImage(String image, ImageView imageView);

        void performCloseAction(int adapterPosition);
    }

    interface GroupNameRowView {
        void setCardImage(String image);
    }
}
