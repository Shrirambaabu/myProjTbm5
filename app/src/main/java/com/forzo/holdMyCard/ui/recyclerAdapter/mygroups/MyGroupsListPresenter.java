package com.forzo.holdMyCard.ui.recyclerAdapter.mygroups;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
import static com.forzo.holdMyCard.utils.Utils.gmtToLocalLibrary;

public class MyGroupsListPresenter implements MyGroupsContract.Presenter {


    private Context context;
    private ArrayList<MyGroups> groupsArrayList;

    public MyGroupsListPresenter(Context context, ArrayList<MyGroups> myLibraries) {
        this.context = context;
        this.groupsArrayList = myLibraries;
    }

    @Override
    public int getItemCount() {
        return (null != groupsArrayList ? groupsArrayList.size() : 0);
    }

    @Override
    public void setImage(String image, ImageView imageView) {

        Glide.with(context)
                .load(IMAGE_URL + image)
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public void bindEventRow(int position, MyGroupsContract.MyGroupsRow myGroupsRow) {

        MyGroups myGroups = groupsArrayList.get(position);

        String  newDate=gmtToLocalLibrary(myGroups.getCreatedTs());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(newDate);
            long timeInMilliseconds = mDate.getTime();
            myGroupsRow.setCreatedOn(TimeAgo.using(Long.parseLong(String.valueOf(timeInMilliseconds))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myGroupsRow.setCardImage(myGroups.getImageName());
        myGroupsRow.setGroupName(myGroups.getLibraryGroupName());
      //  myGroupsRow.setCreatedOn(myGroups.getCreatedTs());
        myGroupsRow.setGroupMembers(myGroups.getTotalMembers());


    }
}
