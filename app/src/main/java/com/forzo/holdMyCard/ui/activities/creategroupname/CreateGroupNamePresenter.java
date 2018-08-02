package com.forzo.holdMyCard.ui.activities.creategroupname;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CreateGroupNamePresenter extends BasePresenter<CreateGroupNameContract.View> implements CreateGroupNameContract.Presenter {


    private Context context;
    private static final String TAG = "CreateGroupNamePresenter";
    private ApiService mApiService;
    private static ArrayList<String> stringArrayList = new ArrayList<>();
    private static ArrayList<Groups> stringUserArrayList = new ArrayList<>();

    public CreateGroupNamePresenter() {
    }

    CreateGroupNamePresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void getIntentValues(Intent intent) {
        Log.e("arrayList", "Intent Called");


        stringArrayList = Objects.requireNonNull(intent.getExtras()).getStringArrayList("userList");

        // stringArrayList = intent.getStringArrayListExtra("userList");
        if (stringArrayList != null) {
            Log.e("arrayList", "" + stringArrayList.size());
            if (!stringArrayList.isEmpty()) {
                for (int i = 0; i <= stringArrayList.size() - 1; i++) {
                    Log.e("GotValue", "" + stringArrayList.get(i));
                }
            }
        }
    }

    @Override
    public void setupShowsRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        getView().showRecyclerView();
    }

    @Override
    public void updateDataValues(ArrayList<Groups> groupsArrayList) {
        Log.e("Arr", "" + groupsArrayList.size());

        this.stringUserArrayList = groupsArrayList;

        if (!stringUserArrayList.isEmpty()) {

            Log.e("Val", "" + stringUserArrayList.size());
            Log.e("Val", "" + stringUserArrayList.get(0).getUserId());


        }
    }

    @Override
    public void createGroup(ArrayList<Groups> groupsArrayList, String groupName) {

        Log.e("Create:", "ArrSize:" + groupsArrayList.size());

        if (!groupsArrayList.isEmpty()){

            for (int i=0;i<=groupsArrayList.size()-1;i++){
                String libraryContactId=groupsArrayList.get(i).getUserId();

                Groups myGroups = new Groups();
                myGroups.setLibraryOwnerId(PreferencesAppHelper.getUserId());
              //  myGroups.setLibraryContactId(libraryContactId);
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
                if (groupsArrayList.size()==0){
                    getView().createGroupDone();
                }
            }

        }

       /**/


    }


    @Override
    public void populateRecyclerView(ArrayList<Groups> groups) {

        Log.e("Populate", "" + stringArrayList.size());
        if (stringArrayList != null) {
            getView().cardNumber(stringArrayList.size());
        }
        if (!stringArrayList.isEmpty()) {
            for (int i = 0; i <= stringArrayList.size() - 1; i++) {
                Log.e("GotValue", "" + stringArrayList.get(i));

                String userId = stringArrayList.get(i);
                mApiService.getUserProfileImagesGroup(userId, "BCF")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Groups>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(List<Groups> groupsList) {

                                for (int i = 0; i < groupsList.size(); i++) {

                                    Groups myGroups = new Groups();
                                    Log.e("userImage", "" + groupsList.get(i).getImageType());
                                    Log.e("userImageID", "" + userId);
                                    Log.e("userImage", "" + groupsList.get(i).getImageName());
                                    myGroups.setImageName(groupsList.get(i).getImageName());
                                    myGroups.setUserId(userId);
                                    groups.add(myGroups);
                                    getView().updateAdapter();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("err", "" + e.getMessage());
                                Log.e("err", "erorr image get");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });

            }
        }


    }
}
