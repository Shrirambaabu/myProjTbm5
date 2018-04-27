package com.forzo.holdMyCard.ui.activities.remainder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shriram on 4/5/2018.
 */

public class ReminderPresenter extends BasePresenter<ReminderContract.View> implements ReminderContract.Presenter{

    private ApiService mApiService;

    private Context context;

    ReminderPresenter(Context context) {
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
    public void populateRecyclerView(List<MyRemainder> remainders,String remainderKey,String libraryImageValue) {

/*

        remainders.addAll(DataService.getRemainderList());
        getView().updateAdapter();

*/


        mApiService.getUserRemainder(remainderKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyRemainder>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  getView().showLoading();
                    }

                    @Override
                    public void onNext(List<MyRemainder> myRemainderList) {

                        for (int i = 0; i < myRemainderList.size(); i++) {
                            MyRemainder myRemainder = new MyRemainder();

                            myRemainder.setName(myRemainderList.get(i).getName());
                            myRemainder.setDate(myRemainderList.get(i).getDate());
                            myRemainder.setTime(myRemainderList.get(i).getTime());
                            myRemainder.setId(myRemainderList.get(i).getId());
                            myRemainder.setStatusId(myRemainderList.get(i).getStatusId());

                            myRemainder.setLibraryProfileId(remainderKey);
                            myRemainder.setLibraryProfileImage(libraryImageValue);

                            remainders.add(myRemainder);


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

    @Override
    public void getIntentValues(Intent intent) {

        String profile = intent.getStringExtra("libraryProfile");
        String profileImage = intent.getStringExtra("libraryProfileImage");
        if (profile!=null&&profileImage!=null){
            getView().setReminderPrimaryValue(profile,profileImage);
        }

    }


}
