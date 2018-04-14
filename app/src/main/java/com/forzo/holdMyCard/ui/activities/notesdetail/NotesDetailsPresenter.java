package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.activities.remainderdetails.RemainderDetailsContract;
import com.forzo.holdMyCard.ui.models.MyNotes;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shriram on 4/13/2018.
 */

public class NotesDetailsPresenter extends BasePresenter<NotesDetailContract.View> implements NotesDetailContract.Presenter {


    private Context context;
    private ApiService mApiService;

    NotesDetailsPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void saveNote(NotesDetailsActivity notesDetailsActivity, EditText editText) {

        String content=editText.getText().toString();


        MyNotes myNotes=new MyNotes();
        myNotes.setId("1");
        myNotes.setNotes(content);


        mApiService.saveNotes(myNotes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyNotes>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // progressBar.smoothToShow();
                    }

                    @Override
                    public void onNext(MyNotes myNotes1) {
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

        String  noteDescription = intent.getStringExtra("noteDes");

        /*if (noteDescription!=null) {
            getView().setNotesValue(noteDescription);
        }*/
    }
}
