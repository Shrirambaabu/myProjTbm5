package com.forzo.holdMyCard.ui.recyclerAdapter.groupname;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.activities.creategroupname.CreateGroupNameActivity;
import com.forzo.holdMyCard.ui.activities.creategroupname.CreateGroupNamePresenter;
import com.forzo.holdMyCard.ui.models.Groups;

import java.util.ArrayList;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

public class GroupNameListPresenter implements GroupNameContract.Presenter {

    private Context context;
    private ArrayList<Groups> groups;
    private GroupNameRecyclerAdapter groupNameRecyclerAdapter = new GroupNameRecyclerAdapter();
    private CreateGroupNamePresenter createGroupNamePresenter = new CreateGroupNamePresenter();
    private CreateGroupNameActivity createGroupNameActivity=new CreateGroupNameActivity();

    public GroupNameListPresenter(Context context, ArrayList<Groups> groups1) {
        this.context = context;
        this.groups = groups1;
    }

    @Override
    public int getItemCount() {
        return (null != groups ? groups.size() : 0);
    }

    @Override
    public void bindEventRow(int position, GroupNameContract.GroupNameRowView holder) {

        Groups groups1 = groups.get(position);
        Log.e("HolderUserId", "" + groups1.getUserId());
        holder.setCardImage(groups1.getImageName());
    }

    @Override
    public void setImage(String image, ImageView imageView) {

        Glide.with(context)
                .load(IMAGE_URL + image)
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public void performCloseAction(int adapterPosition) {
        Log.e("Clicked", "" + groups.get(adapterPosition).getUserId());
        Log.e("Clicked", "" + groups.get(adapterPosition));
        Log.e("arraySize", "" + groups.size());

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Confirm !!!");
        alertDialog.setMessage("Are you sure you want to remove this user?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    dialog.dismiss();
                    groups.remove(adapterPosition);
                    //createGroupNamePresenter.updateDataValues(groups);
                    groupNameRecyclerAdapter.notifyDataSetChanged();
                    groupNameRecyclerAdapter.notifyItemChanged(adapterPosition);
                    groupNameRecyclerAdapter.notifyItemRangeChanged(adapterPosition, groups.size());
                    Log.e("arraySize", "" + groups.size());
                    groupNameRecyclerAdapter.notifyDataSetChanged();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}
