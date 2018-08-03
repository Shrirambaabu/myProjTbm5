package com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.ui.activities.newcard.NewCardActivity;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.forzo.holdMyCard.ui.models.MyLibrary;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.forzo.holdMyCard.HmcApplication.IMAGE_URL;

public class GroupDetailsAdapterListPresenter implements GroupDetailsAdapterContract.Presenter {

    private Context context;
    private ArrayList<MyLibrary> myLibraries;
    GroupDetailsRecyclerAdapter groupDetailsRecyclerAdapter = new GroupDetailsRecyclerAdapter();
    private ApiService mApiService;

    public GroupDetailsAdapterListPresenter(Context context, ArrayList<MyLibrary> myLibraries) {
        this.context = context;
        this.myLibraries = myLibraries;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public int getItemCount() {
        return (null != myLibraries ? myLibraries.size() : 0);
    }

    @Override
    public void setImage(String image, ImageView imageView) {

        Glide.with(context)
                .load(IMAGE_URL + image)
                .thumbnail(0.1f)
                .into(imageView);
    }

    @Override
    public void bindEventRow(int position, GroupDetailsAdapterContract.GroupDetailsAdapterRowView holder, GroupDetailsRecyclerAdapter groupDetailsRecyclerAdapter) {
        this.groupDetailsRecyclerAdapter = groupDetailsRecyclerAdapter;
        MyLibrary myLibrary = myLibraries.get(position);


        holder.setCardImage(myLibrary.getImage());
        holder.setCardName(myLibrary.getCardName());
        holder.setCardDescription(myLibrary.getCardDescription());
        holder.setCardDetails(myLibrary.getCardDetails());
        holder.setGroupUserId(myLibrary.getUserId());
        holder.setGroupId(myLibrary.getGroupId());

    }

    @Override
    public void longPress(int adapterPosition, String groupId) {

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Confirm !!!");
        alertDialog.setMessage("Are you sure to remove this user ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    dialog.dismiss();
                    deleteUserFromGroup(myLibraries.get(adapterPosition).getUserId(), groupId);
                    myLibraries.remove(adapterPosition);
                    groupDetailsRecyclerAdapter.notifyItemRemoved(adapterPosition);
                    groupDetailsRecyclerAdapter.notifyItemRangeChanged(adapterPosition, myLibraries.size());
                    groupDetailsRecyclerAdapter.notifyDataSetChanged();



                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }

    @Override
    public void onSingleClick(int adapterPosition, String groupUserId) {

        Activity activity = (Activity) context;
        Intent myLibraryIntent = new Intent(context, NewCardActivity.class);
        myLibraryIntent.putExtra("libraryProfile", "" + myLibraries.get(adapterPosition).getUserId());
        myLibraryIntent.putExtra("libraryProfileImage", "" + myLibraries.get(adapterPosition).getImage());
        myLibraryIntent.putExtra("ActivityAction", "MyLibrary");
        context.startActivity(myLibraryIntent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void deleteUserFromGroup(String contactId, String groupId) {

        mApiService.deleteGroupMember(contactId,groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyGroups>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyGroups myGroupsList1) {

                        Log.e("deleteGroup",""+myGroupsList1.getDeleteStatus());

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
