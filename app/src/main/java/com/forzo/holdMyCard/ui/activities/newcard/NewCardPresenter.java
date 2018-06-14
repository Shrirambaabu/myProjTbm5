package com.forzo.holdMyCard.ui.activities.newcard;

import android.app.Activity;
import android.content.Context;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;

public class NewCardPresenter extends BasePresenter<NewCardContract.View> implements NewCardContract.Presenter {

    private ApiService mApiService;
    private Context context;

    NewCardPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());

    }
}
