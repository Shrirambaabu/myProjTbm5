package com.forzo.holdMyCard.ui.activities.creategroupname;


import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.base.PerFragmentScope;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.recyclerAdapter.groupname.GroupNameListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.groupname.GroupNameRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class CreateGroupNameModule {


    @Provides
    @PerActivityScope
    CreateGroupNamePresenter createGroupNamePresenter(Context context) {
        return new CreateGroupNamePresenter(context);
    }



    @Provides
    @PerActivityScope
    ArrayList<Groups> myLibraries() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivityScope
    GroupNameListPresenter currentArrayList(Context context, ArrayList<Groups> currentArrayList) {
        return new GroupNameListPresenter(context, currentArrayList);
    }

    @Provides
    @PerActivityScope
    GroupNameRecyclerAdapter groupNameRecyclerAdapter(GroupNameListPresenter groupNameListPresenter) {
        return new GroupNameRecyclerAdapter(groupNameListPresenter);
    }


}
