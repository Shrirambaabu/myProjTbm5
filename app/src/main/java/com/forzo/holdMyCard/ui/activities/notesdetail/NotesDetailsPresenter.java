package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
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
    public void saveNote(NotesDetailsActivity notesDetailsActivity, EditText editText, String notePrimaryValue) {

        String content = editText.getText().toString();


        MyNotes myNotes = new MyNotes();
        myNotes.setUserId(notePrimaryValue);
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
                        Log.e("err", "" + e.getMessage());
                        //  progressBar.smoothToHide();

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void getIntentValues(Intent intent, Button button) {

        String noteDescription = intent.getStringExtra("noteDes");
        String notesId = intent.getStringExtra("noteId");
        String profile = intent.getStringExtra("libraryProfile");
        String libraryImageValue = intent.getStringExtra("libraryProfileImage");


        if (profile != null && libraryImageValue != null) {
            getView().setNotesPrimaryValue(profile, libraryImageValue);
        }

        if (noteDescription != null && notesId != null) {
            Log.e("Value", notesId);
            getView().setNotesValue(noteDescription, notesId);
            //   getView().setNotesValueEnabled();
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
            getView().setSaveButton();
        }

        if (noteDescription.equals("")) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateNotes(String notesId, String noteDescp) {


        MyNotes myNotes = new MyNotes();
        myNotes.setId(notesId);
        myNotes.setNotes(noteDescp);


        mApiService.updateNotes(myNotes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyNotes>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // progressBar.smoothToShow();
                    }

                    @Override
                    public void onNext(MyNotes myNotes1) {
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
    public void deleteNotes(String notesId) {

        mApiService.deleteUserNotes(notesId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyNotes>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  getView().showLoading();
                    }

                    @Override
                    public void onNext(MyNotes myNotes) {

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
