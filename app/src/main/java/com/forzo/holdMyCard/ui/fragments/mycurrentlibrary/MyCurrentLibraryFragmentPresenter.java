package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyCurrentLibraryFragmentPresenter extends BasePresenter<MyCurrentLibraryFragmentContract.View> implements MyCurrentLibraryFragmentContract.Presenter {


    private Context context;
    private ApiService mApiService;
    MyCurrentLibraryFragmentPresenter(Context context) {
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
    public void populateRecyclerView(List<MyLibrary> myLibraryList) {


        myLibraryList.addAll(DataService.getCurrentCardList());
        getView().updateAdapter();


        /*mApiService.getUserLibrary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyLibrary>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  getView().showLoading();
                    }

                    @Override
                    public void onNext(List<MyLibrary> myLibraryList1) {

                        for (int i = 0; i < myLibraryList1.size(); i++) {
                            MyLibrary myLibrary = new MyLibrary();

                            myLibrary.setCardName(myLibraryList1.get(i).getCardName());
                            myLibrary.setCardDescription(myLibraryList1.get(i).getCardDescription());
                            myLibrary.setCardDetails(myLibraryList1.get(i).getCardDetails());

                            myLibraryList1.add(myLibrary);

                            getView().updateAdapter();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // getView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        //  getView().hideLoading();
                    }
                });


*/

    }
}
