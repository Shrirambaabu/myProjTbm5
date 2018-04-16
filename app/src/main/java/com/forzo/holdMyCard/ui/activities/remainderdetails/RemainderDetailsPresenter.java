package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyRemainder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.forzo.holdMyCard.utils.Utils.dateToDb;
import static com.forzo.holdMyCard.utils.Utils.timeToDb;

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

        String remainderContent = editText.getText().toString();

        String date = dateText.getText().toString();
        String time = timeText.getText().toString();


       date=dateToDb(date);
       Log.e("CDate",""+date);
       time=timeToDb(time);
       Log.e("Ctime",""+time);

        MyRemainder myRemainder = new MyRemainder();

        myRemainder.setUserId("1");
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

    @Override
    public void getIntentValues(Intent intent, Button button) {

        String remainderContent = intent.getStringExtra("remainDesc");
        String remainderDate = intent.getStringExtra("remainDate");
        String remainderTime = intent.getStringExtra("remainTime");


        Log.e("values", "" + remainderContent);
        Log.e("values", "" + remainderDate);
        Log.e("values", "" + remainderTime);

        if (remainderContent != null || remainderDate != null || remainderTime != null) {

            getView().remainderText(remainderContent);
            getView().remainderDate(remainderDate);
            getView().remainderTime(remainderTime);
            button.setVisibility(View.GONE);
        }else {
            button.setVisibility(View.VISIBLE);
        }

        if (remainderContent.equals("") || remainderDate.equals("") || remainderTime.equals("")) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }
}
