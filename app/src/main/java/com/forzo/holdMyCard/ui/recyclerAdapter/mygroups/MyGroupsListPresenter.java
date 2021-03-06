package com.forzo.holdMyCard.ui.recyclerAdapter.mygroups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.ui.activities.groupdetails.GroupDetailsActivity;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;
import static com.forzo.holdMyCard.utils.Utils.gmtToLocalLibrary;

public class MyGroupsListPresenter implements MyGroupsContract.Presenter {


    private Context context;
    private ArrayList<MyGroups> groupsArrayList;
    private ApiService mApiService;
    private MyGroupsRecyclerAdapter myGroupsRecyclerAdapter = new MyGroupsRecyclerAdapter();

    public MyGroupsListPresenter(Context context, ArrayList<MyGroups> myLibraries) {
        this.context = context;
        this.groupsArrayList = myLibraries;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
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
    public void bindEventRow(int position, MyGroupsContract.MyGroupsRow myGroupsRow, MyGroupsRecyclerAdapter myGroupsRecyclerAdapter) {
        this.myGroupsRecyclerAdapter = myGroupsRecyclerAdapter;

        MyGroups myGroups = groupsArrayList.get(position);

        String newDate = gmtToLocalLibrary(myGroups.getCreatedTs());

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

    @Override
    public void clickGroup(int position, String groupName) {
        // Toast.makeText(context, "Edit Group is under development", Toast.LENGTH_LONG).show();
        Intent groupDetailsIntent = new Intent(context, GroupDetailsActivity.class);
        groupDetailsIntent.putExtra("adapterPosition", "" + position);
        groupDetailsIntent.putExtra("groupName", "" + groupName);
        groupDetailsIntent.putExtra("groupId", "" + groupsArrayList.get(position).getLibraryGroupId());
        context.startActivity(groupDetailsIntent);
    }

    @Override
    public void longPress(int adapterPosition) {
        Log.e("LongClickPos:", "" + adapterPosition);
        Log.e("LongClickPosName:", "" + groupsArrayList.get(adapterPosition).getLibraryGroupId());

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Confirm !!!");
        alertDialog.setMessage("Are you sure you delete this group ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    dialog.dismiss();
                    deleteGroup(groupsArrayList.get(adapterPosition).getLibraryGroupId());
                    groupsArrayList.remove(adapterPosition);
                    myGroupsRecyclerAdapter.notifyItemRemoved(adapterPosition);
                    myGroupsRecyclerAdapter.notifyItemRangeChanged(adapterPosition, groupsArrayList.size());
                    myGroupsRecyclerAdapter.notifyDataSetChanged();



                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    public void deleteGroup(String groupId) {
        mApiService.deleteMyGroup(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyGroups>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyGroups myGroupsList1) {

                        Log.e("renameGroup", "" + myGroupsList1.getDeleteGroup());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
