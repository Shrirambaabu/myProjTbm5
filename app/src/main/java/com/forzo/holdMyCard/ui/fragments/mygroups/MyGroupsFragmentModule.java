package com.forzo.holdMyCard.ui.fragments.mygroups;

import android.content.Context;

import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.base.PerFragmentScope;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.forzo.holdMyCard.ui.recyclerAdapter.mygroups.MyGroupsListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.mygroups.MyGroupsRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module(includes = {FragmentContext.class})
@PerFragmentScope
public class MyGroupsFragmentModule {


    @Provides
    @PerFragmentScope
    MyGroupsFragmentPresenter myGroupsFragmentPresenter(Context context) {
        return new MyGroupsFragmentPresenter(context);
    }

    @Provides
    @PerFragmentScope
    ArrayList<MyGroups> groupsArrayList() {
        return new ArrayList<>();
    }

    @Provides
    @PerFragmentScope
    MyGroupsListPresenter myGroupsListPresenter(Context context, ArrayList<MyGroups> currentArrayList) {
        return new MyGroupsListPresenter(context, currentArrayList);
    }

    @Provides
    @PerFragmentScope
    MyGroupsRecyclerAdapter myGroupsRecyclerAdapter(MyGroupsListPresenter currentFragmentListPresenter) {
        return new MyGroupsRecyclerAdapter(currentFragmentListPresenter);
    }
}
