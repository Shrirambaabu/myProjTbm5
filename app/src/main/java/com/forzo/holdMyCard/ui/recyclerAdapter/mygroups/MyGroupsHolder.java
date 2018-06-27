package com.forzo.holdMyCard.ui.recyclerAdapter.mygroups;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGroupsHolder extends RecyclerView.ViewHolder implements MyGroupsContract.MyGroupsRow, View.OnClickListener {


    MyGroupsListPresenter myGroupsListPresenter;


    @BindView(R.id.card_image)
    ImageView cardImage;
    @BindView(R.id.group_name)
    TextView groupNameText;
    @BindView(R.id.group_members)
    TextView groupMembersText;
    @BindView(R.id.group_created_on)
    TextView groupCreatedOn;

    public MyGroupsHolder(View itemView, MyGroupsListPresenter fragmentListPresenter) {
        super(itemView);
        this.myGroupsListPresenter = fragmentListPresenter;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        myGroupsListPresenter.clickGroup(getAdapterPosition());
    }

    @Override
    public void setCardImage(String image) {
        myGroupsListPresenter.setImage(image, cardImage);
    }

    @Override
    public void setGroupName(String groupName) {
        groupNameText.setText(groupName);
    }

    @Override
    public void setGroupMembers(String groupMembers) {
        groupMembersText.setText(groupMembers+" Members");
    }

    @Override
    public void setCreatedOn(String createdOn) {
        groupCreatedOn.setText(createdOn);
    }
}
