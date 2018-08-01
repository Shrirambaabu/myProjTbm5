package com.forzo.holdMyCard.ui.activities.editgroupname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;

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
        String groupName;

        Bundle bundle = intent.getExtras();


        if (bundle != null) {
            if (bundle.getString("groupName") != null) {
                groupName = intent.getExtras().getString("groupName");
                getView().setGroupName(groupName);
            }
        }
    }
}
