package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyRemainder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shriram on 4/13/2018.
 */

public class RemainderDetailsPresenter extends BasePresenter<RemainderDetailsContract.View> implements RemainderDetailsContract.Presenter {


    private Context context;
    private ApiService mApiService;
    RemainderDetailsPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());

    }


    @Override
    public void showDatePicker() {



    }

    @Override
    public void saveRemainder(RemainderDetailsActivity remainderDetailsActivity, EditText editText, TextView dateText, TextView timeText) {

        String remainderContent=editText.getText().toString();

        String  date=dateText.getText().toString();
        String  time=timeText.getText().toString();

        MyRemainder myRemainder=new MyRemainder();

        myRemainder.setId("1");
        myRemainder.setName(remainderContent);
        myRemainder.setDate(date);
        myRemainder.setTime(time);

        mApiService.saveRemainder(myRemainder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyRemainder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // progressBar.smoothToShow();
                    }

                    @Override
                    public void onNext(MyRemainder myNotes1) {
                        getView().savedSuccessfully();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  progressBar.smoothToHide();

                    }

                    @Override
                    public void onComplete() {

                    }
                });



    }
}
