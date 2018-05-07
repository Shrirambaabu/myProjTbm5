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
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.forzo.holdMyCard.utils.Utils.dateToDb;
import static com.forzo.holdMyCard.utils.Utils.dateToUI;
import static com.forzo.holdMyCard.utils.Utils.gmtToLocal;
import static com.forzo.holdMyCard.utils.Utils.localToGMT;
import static com.forzo.holdMyCard.utils.Utils.newUtcFormat;
import static com.forzo.holdMyCard.utils.Utils.newUtcTimeFormat;
import static com.forzo.holdMyCard.utils.Utils.timeToDb;
import static com.forzo.holdMyCard.utils.Utils.timeToUi;

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
    public void saveRemainder(RemainderDetailsActivity remainderDetailsActivity, EditText editText, TextView dateText, TextView timeText, String remainderKey) {

        String remainderContent = editText.getText().toString();

        String date = dateText.getText().toString();
        String time = timeText.getText().toString();

        Log.e("saveDate", "" + date);
        Log.e("saveTime", "" + time);


        Date convertUtc = localToGMT(date + " " + time);


        String dateFinal = newUtcFormat(convertUtc);

        String timeFinal = newUtcTimeFormat(convertUtc);

        date = dateToDb(dateFinal);

        time = timeToDb(timeFinal);


        Log.e("saveDateDB", "" + date);
        Log.e("saveTimeDB", "" + time);

        MyRemainder myRemainder = new MyRemainder();

        myRemainder.setUserId(remainderKey);
        myRemainder.setCreatedUserId(PreferencesAppHelper.getUserId());
        myRemainder.setName(remainderContent);
        myRemainder.setDate(date);
        myRemainder.setTime(time);

        mApiService.saveRemainder(myRemainder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyRemainder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(MyRemainder myNotes1) {
                        getView().savedSuccessfully();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("err",""+e.getMessage());
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
        String remainderId = intent.getStringExtra("remainId");
        String remainderAction = intent.getStringExtra("remainAction");

        String profile = intent.getStringExtra("libraryProfile");
        String libraryImageValue = intent.getStringExtra("libraryProfileImage");

        if (remainderAction != null) {
            getView().notifyPush();
        } else {
            remainderAction = "";
        }
        if (profile != null && libraryImageValue != null) {
            getView().setReminderPrimaryValue(profile, libraryImageValue);
        }

        if (remainderId != null) {

            if (remainderId.equals("new")) {

                Calendar cal = Calendar.getInstance();
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("hh:mm a");

                String localTime = date.format(currentLocalTime);
                getView().remainderTime(localTime);

                Date c = Calendar.getInstance().getTime();


                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c);

                getView().remainderDate(formattedDate);
            } else {
                getView().reminderDetails(remainderId, remainderAction);
            }
        }


/*
        if (!remainderDate.equals("")) {
            remainderDate = dateToUI(remainderDate);
        }
        if (!remainderTime.equals("")) {
            remainderTime = timeToUi(remainderTime);
        }

        if (remainderContent != null || remainderDate != null || remainderTime != null || remainderId != null) {

            getView().remainderText(remainderContent);
            getView().remainderDate(remainderDate);
            getView().remainderTime(remainderTime);
            getView().remainderDetails(remainderId, remainderDate, remainderTime);
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
            getView().setSaveVisible();

        }

        if (remainderTime.equals("")) {

            Calendar cal = Calendar.getInstance();
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("hh:mm a");

            String localTime = date.format(currentLocalTime);
            getView().remainderTime(localTime);
        }

        if (remainderDate.equals("")) {
            Date c = Calendar.getInstance().getTime();


            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);

            getView().remainderDate(formattedDate);
        }

        if (remainderContent.equals("") || remainderDate.equals("") || remainderTime.equals("")) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void callReminderDetails(String s, String remainderAction) {


        mApiService.getUserRemainderDetails(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyRemainder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  getView().showLoading();
                    }

                    @Override
                    public void onNext(MyRemainder myRemainderList) {


                        MyRemainder myRemainder = new MyRemainder();

                        myRemainder.setName(myRemainderList.getName());
                        myRemainder.setDate(myRemainderList.getDate());
                        myRemainder.setTime(myRemainderList.getTime());
                        myRemainder.setId(myRemainderList.getId());
                        myRemainder.setReminderCd(myRemainderList.getReminderCd());

                        String reminderDate = dateToUI(myRemainderList.getDate());
                        String reminderTime = timeToUi(myRemainderList.getTime());


                        Date convertUtc = gmtToLocal(reminderDate + " " + reminderTime);

                        Log.e("Get", "" + convertUtc);
                        String dateFinal = newUtcFormat(convertUtc);

                        String timeFinal = newUtcTimeFormat(convertUtc);

                        getView().remainderText(myRemainderList.getName());
                        getView().remainderDate(dateFinal);
                        getView().remainderTime(timeFinal);

                        getView().remainderDetails(s, myRemainderList.getDate(), myRemainderList.getTime());

                        if (!remainderAction.equals("")) {
                            getView().notifyPush();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("notes", "err " + e.getMessage());
                        // getView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        //  getView().hideLoading();
                    }
                });


    }

    @Override
    public void updateReminder(String reminderId, String reminderDesc, String timePicker, String datePicker) {

        Date convertUtc = localToGMT(datePicker + " " + timePicker);

        String dateFinal = newUtcFormat(convertUtc);

        String timeFinal = newUtcTimeFormat(convertUtc);

        datePicker = dateToDb(dateFinal);
        timePicker = timeToDb(timeFinal);


        MyRemainder myRemainder = new MyRemainder();
        myRemainder.setId(reminderId);
        myRemainder.setName(reminderDesc);
        myRemainder.setDate(datePicker);
        myRemainder.setTime(timePicker);


        mApiService.updateReminder(myRemainder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyRemainder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // progressBar.smoothToShow();
                    }

                    @Override
                    public void onNext(MyRemainder myNotes1) {
                        getView().updatedSuccessfully();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("err", "" + e.getMessage());
                        //  progressBar.smoothToHide();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteReminder(String reminderId) {


        mApiService.deleteUserReminder(reminderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyRemainder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  getView().showLoading();
                    }

                    @Override
                    public void onNext(MyRemainder myRemainder) {

                        getView().deletedSuccessfully();

                    }

                    @Override
                    public void onError(Throwable e) {
                        // getView().hideLoading();
                        Log.e("err", "" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //  getView().hideLoading();
                    }
                });


    }
}
