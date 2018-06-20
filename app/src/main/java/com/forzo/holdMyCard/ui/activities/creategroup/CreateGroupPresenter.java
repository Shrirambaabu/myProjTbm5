package com.forzo.holdMyCard.ui.activities.creategroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.forzo.holdMyCard.ui.activities.creategroupname.CreateGroupNameActivity;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.forzo.holdMyCard.utils.BottomNavigationHelper.setupBottomNavigationSetUp;

/**
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupPresenter extends BasePresenter<CreateGroupContract.View> implements CreateGroupContract.Presenter {


    private Context context;
    private static final String TAG = "CreateGroupPresenter";
    private ApiService mApiService;
    private static ArrayList<String> stringArrayList = new ArrayList<>();

    public CreateGroupPresenter() {
    }

    CreateGroupPresenter(Context context) {
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

/*
        myLibraryList.addAll(DataService.getCurrentCardList());
        getView().updateAdapter();*/

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

        String disabled = intent.getStringExtra("module");

        if (disabled != null) {
            getView().showDialog();
        }

    }

    @Override
    public void getGroupId(ArrayList<String> stringArrayList3) {
        Log.e("CRActivity", "" + stringArrayList3.size());
        this.stringArrayList = stringArrayList3;
        if (!stringArrayList3.isEmpty()) {
            for (int i = 0; i <= stringArrayList3.size() - 1; i++) {
                Log.e("CRContactLast", "" + stringArrayList3.get(i));

            }
            //getView().createGroupId(stringArrayList3);
        }
    }

    @Override
    public void gotoGroupName(String groupName) {
        Log.e("presenterDetailsNEXT", "" + stringArrayList.size());
        if (stringArrayList.size() == 0) {
            Toast.makeText(context, "Select at least one contact", Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i <= stringArrayList.size() - 1; i++) {
            String libraryContactId = stringArrayList.get(i);
            Groups myGroups = new Groups();
            myGroups.setLibraryOwnerId(PreferencesAppHelper.getUserId());
            myGroups.setLibraryContactId(libraryContactId);
            myGroups.setLibraryGroupName(groupName);

            mApiService.createGroupName(myGroups)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Groups>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Groups groups) {
                            // getView().updatedSuccessfully();
                            Log.e("SuccMsg",""+groups.getAddedLibrary());

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("err", "" + e.getMessage());

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


            if (i == stringArrayList.size() - 1) {
                getView().createGroupDone();
            }
        }
/*        Intent intent = new Intent(context, CreateGroupNameActivity.class);
        intent.putStringArrayListExtra("userList", stringArrayList);
        context.startActivity(intent);*/

    }


}
