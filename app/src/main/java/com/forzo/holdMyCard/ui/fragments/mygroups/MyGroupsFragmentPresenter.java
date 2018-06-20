package com.forzo.holdMyCard.ui.fragments.mygroups;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyGroupsFragmentPresenter extends BasePresenter<MyGroupsFragmentContract.View>
        implements MyGroupsFragmentContract.Presenter {

    private static final String TAG = "MyGroupsFragmen";
    private Context context;
    private ApiService mApiService;

    MyGroupsFragmentPresenter(Context context) {
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
    public void populateRecyclerView(List<MyGroups> groups) {


        Log.e("Pref", "" + PreferencesAppHelper.getUserId());
        if (PreferencesAppHelper.getUserId() != null) {
            mApiService.getMyGroup(PreferencesAppHelper.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MyGroups>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(List<MyGroups> myGroupsList1) {

                            for (int i = 0; i < myGroupsList1.size(); i++) {
                                MyGroups myGroups = new MyGroups();

                                myGroups.setLibraryGroupName(myGroupsList1.get(i).getLibraryGroupName());
                              //  myGroups.setLibraryContactId(myGroupsList1.get(i).getLibraryContactId());
                                myGroups.setCreatedTs(myGroupsList1.get(i).getCreatedTs());
                                myGroups.setImageName(myGroupsList1.get(i).getImageName());
                                myGroups.setLibraryGroupId(myGroupsList1.get(i).getLibraryGroupId());
                                myGroups.setTotalMembers(myGroupsList1.get(i).getTotalMembers());

                                groups.add(myGroups);

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
}
