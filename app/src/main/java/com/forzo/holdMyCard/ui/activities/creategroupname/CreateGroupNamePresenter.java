package com.forzo.holdMyCard.ui.activities.creategroupname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;

import java.util.ArrayList;
import java.util.Objects;

public class CreateGroupNamePresenter extends BasePresenter<CreateGroupNameContract.View> implements CreateGroupNameContract.Presenter {


    private Context context;
    private static final String TAG = "CreateGroupNamePresenter";
    private ApiService mApiService;

    public CreateGroupNamePresenter() {
    }

    CreateGroupNamePresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void getIntentValues(Intent intent) {
        Log.e("arrayList", "Intent Called");
        ArrayList<String> stringArrayList = new ArrayList<>();

        stringArrayList = Objects.requireNonNull(intent.getExtras()).getStringArrayList("userList");

        // stringArrayList = intent.getStringArrayListExtra("userList");
        if (stringArrayList != null) {
            Log.e("arrayList", "" + stringArrayList.size());
            if (!stringArrayList.isEmpty()) {
                for (int i = 0; i <= stringArrayList.size() - 1; i++) {
                    Log.e("GotValue", "" + stringArrayList.get(i));
                }
            }
        }
    }
}
