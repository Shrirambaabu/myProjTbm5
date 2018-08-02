package com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails;

import android.widget.ImageView;

public class GroupDetailsAdapterContract {


    interface Presenter {
        int getItemCount();

        void setImage(String image, ImageView imageView);

        void bindEventRow(int position, GroupDetailsAdapterRowView holder, GroupDetailsRecyclerAdapter groupDetailsRecyclerAdapter);

        void longPress(int adapterPosition, String groupUserId);

        void onSingleClick(int adapterPosition, String groupUserId);

        void deleteUserFromGroup(String contactId, String groupId);


    }

    interface GroupDetailsAdapterRowView {

        void setCardImage(String image);

        void setCardName(String cardName);

        void setCardDescription(String cardDescription);

        void setCardDetails(String cardDetails);

        void setGroupUserId(String groupUserId);

        void setGroupId(String groupId);

    }
}
