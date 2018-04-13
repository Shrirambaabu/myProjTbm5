package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.app.Activity;
import android.content.Context;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.base.BasePresenter;

/**
 * Created by Shriram on 4/13/2018.
 */

public class RemainderDetailsPresenter extends BasePresenter<RemainderDetailsContract.View> implements RemainderDetailsContract.Presenter {


    private Context context;

    RemainderDetailsPresenter(Context context) {
        this.context = context;

    }


    @Override
    public void showDatePicker() {



    }
}
