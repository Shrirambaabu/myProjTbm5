package com.forzo.holdMyCard.ui.recyclerAdapter.groupname;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.forzo.holdMyCard.R;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupNameHolder extends RecyclerView.ViewHolder implements GroupNameContract.GroupNameRowView {

    GroupNameListPresenter groupNameListPresenter;


    @BindView(R.id.group_user_image)
    ImageView cardImageValue;

    @BindView(R.id.group_user_cancel)
    CircleButton closeButton;

    public GroupNameHolder(View itemView, GroupNameListPresenter groupNameListPresenter) {
        super(itemView);
        this.groupNameListPresenter = groupNameListPresenter;
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.group_user_cancel)
    public void cancelUser(){
        groupNameListPresenter.performCloseAction(getAdapterPosition());
    }
    @Override
    public void setCardImage(String image) {
        groupNameListPresenter.setImage(image,cardImageValue);
    }
}
