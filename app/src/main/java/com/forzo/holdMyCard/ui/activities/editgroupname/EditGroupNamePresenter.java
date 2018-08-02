package com.forzo.holdMyCard.ui.activities.editgroupname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyGroups;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditGroupNamePresenter extends BasePresenter<EditGroupNameContract.View> implements EditGroupNameContract.Presenter {

    private static final String TAG = "EditGroupName";
    private Context context;
    private ApiService mApiService;

    EditGroupNamePresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void getGroupName(Intent intent) {
        String groupName,groupId;

        Bundle bundle = intent.getExtras();


        if (bundle != null) {
            if (bundle.getString("groupName") != null) {
                groupName = intent.getExtras().getString("groupName");
                getView().setGroupName(groupName);
            }   if (bundle.getString("groupId") != null) {
                groupId = intent.getExtras().getString("groupId");
                getView().setGroupId(groupId);
            }
        }
    }

    @Override
    public void updateGroupName(String groupId,String groupName) {

        mApiService.renameMyGroup(groupId,groupName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyGroups>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getView().activityLoader();
                    }

                    @Override
                    public void onNext(MyGroups myGroupsList1) {

                        Log.e("renameGroup",""+myGroupsList1.getRenameStatus());
                        getView().renameStatus(myGroupsList1.getRenameStatus());
                        getView().hideLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        getView().hideLoader();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
}
