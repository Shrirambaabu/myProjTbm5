package com.forzo.holdMyCard.ui.activities.creategroup;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.activities.home.HomePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.creategroup.CreateGroupListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.creategroup.CreateGroupRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 4/3/2018.
 */

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class CreateGroupModule {

    @Provides
    @PerActivityScope
    CreateGroupPresenter createGroupPresenter(Context context) {
        return new CreateGroupPresenter(context);
    }


    @Provides
    @PerActivityScope
    ArrayList<MyLibrary> myLibraries() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivityScope
    CreateGroupListPresenter createGroupListPresenter(Context context, ArrayList<MyLibrary> currentArrayList) {
        return new CreateGroupListPresenter(context, currentArrayList);
    }

    @Provides
    @PerActivityScope
    CreateGroupRecyclerAdapter providesPaymentCurrentRecyclerAdapter(CreateGroupListPresenter currentFragmentListPresenter) {
        return new CreateGroupRecyclerAdapter(currentFragmentListPresenter);
    }
}
