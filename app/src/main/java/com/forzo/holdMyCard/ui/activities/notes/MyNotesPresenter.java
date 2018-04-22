package com.forzo.holdMyCard.ui.activities.notes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotesPresenter extends BasePresenter<NotesContract.View> implements NotesContract.Presenter {

    private ApiService mApiService;
    private Context context;

    MyNotesPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());

    }


    @Override
    public void setupShowsRecyclerView(EmptyRecyclerView emptyRecyclerView, RelativeLayout emptyView) {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        emptyRecyclerView.setLayoutManager(mLayoutManager);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setHasFixedSize(true);

        emptyRecyclerView.setEmptyView(emptyView);

        getView().showRecyclerView();

    }

    @Override
    public void populateRecyclerView(List<MyNotes> myNotes) {


      /*  myNotes.addAll(DataService.getCardNameList());
        getView().updateAdapter();
*/

        mApiService.getUserNotes("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyNotes>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  getView().showLoading();
                    }

                    @Override
                    public void onNext(List<MyNotes> myNotesList) {

                        Log.e("notes", "" + myNotesList);

                        for (int i = 0; i < myNotesList.size(); i++) {
                            MyNotes myNotes1 = new MyNotes();


                            myNotes1.setNotes(myNotesList.get(i).getNotes());

                            myNotes1.setId(myNotesList.get(i).getId());

                            myNotes.add(myNotes1);

                            getView().updateAdapter();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("notes","err "+e.getMessage());
                        // getView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        //  getView().hideLoading();
                    }
                });


    }


}
