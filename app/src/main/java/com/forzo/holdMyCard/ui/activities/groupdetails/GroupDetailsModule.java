package com.forzo.holdMyCard.ui.activities.groupdetails;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails.GroupDetailsAdapterListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails.GroupDetailsRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class GroupDetailsModule {


    @Provides
    @PerActivityScope
    GroupDetailsPresenter groupDetailsPresenter(Context context) {
        return new GroupDetailsPresenter(context);
    }


    @Provides
    @PerActivityScope
    ArrayList<MyLibrary> myLibraries() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivityScope
    GroupDetailsAdapterListPresenter createGroupListPresenter(Context context, ArrayList<MyLibrary> currentArrayList) {
        return new GroupDetailsAdapterListPresenter(context, currentArrayList);
    }

    @Provides
    @PerActivityScope
    GroupDetailsRecyclerAdapter createGroupRecyclerAdapter(GroupDetailsAdapterListPresenter groupDetailsAdapterListPresenter) {
        return new GroupDetailsRecyclerAdapter(groupDetailsAdapterListPresenter);
    }
}
