package com.forzo.holdMyCard.ui.recyclerAdapter.mygroups;

import android.widget.ImageView;

public class MyGroupsContract {


    interface Presenter {

        int getItemCount();

        void setImage(String image, ImageView imageView);

        void bindEventRow(int position, MyGroupsRow holder,MyGroupsRecyclerAdapter myGroupsRecyclerAdapter);

        void clickGroup(int position, String groupName);

        void longPress(int adapterPosition);
        void deleteGroup(String groupId);
    }

    interface MyGroupsRow {

        void setCardImage(String image);

        void setGroupName(String groupName);

        void setGroupMembers(String groupMembers);

        void setCreatedOn(String createdOn);
    }
}
