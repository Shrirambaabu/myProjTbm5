package com.forzo.holdMyCard.ui.activities.addparticipant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddParticipantPresenter extends BasePresenter<AddParticipantContract.View> implements AddParticipantContract.Presenter {


    private Context context;
    private static final String TAG = "CreateGroupPresenter";
    private ApiService mApiService;

    private ArrayList<MyLibrary> myLibrariesOldList = new ArrayList<MyLibrary>();

    public AddParticipantPresenter() {
    }

    AddParticipantPresenter(Context context) {
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
       /* myLibraryList.addAll(DataService.getCurrentCardList());
        getView().updateAdapter();
*/
        if (PreferencesAppHelper.getUserId() != null) {
            mApiService.getUserLibrary(PreferencesAppHelper.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MyLibrary>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(List<MyLibrary> myLibraryList1) {

                            for (int i = 0; i < myLibraryList1.size(); i++) {
                                MyLibrary myLibrary = new MyLibrary();

                                myLibrary.setCardName(myLibraryList1.get(i).getCardName());
                                myLibrary.setCardDescription(myLibraryList1.get(i).getCardDescription());
                                myLibrary.setCardDetails(myLibraryList1.get(i).getCardDetails());
                                myLibrary.setImage(myLibraryList1.get(i).getImage());
                                myLibrary.setImageType(myLibraryList1.get(i).getImageType());
                                myLibrary.setUserId(myLibraryList1.get(i).getUserId());

                                myLibraryList.add(myLibrary);

                                getView().updateAdapter();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    @Override
    public void getIntentValues(Intent intent) {
        Bundle bundle = intent.getExtras();

        String groupId, groupName;

        if (bundle != null) {
            if (bundle.getString("groupId") != null) {
                groupId = intent.getExtras().getString("groupId");
                getView().setGroupId(groupId);
            }
            if (bundle.getString("groupName") != null) {
                groupName = intent.getExtras().getString("groupName");
                getView().setGroupName(groupName);
            }
        }
    }
}
