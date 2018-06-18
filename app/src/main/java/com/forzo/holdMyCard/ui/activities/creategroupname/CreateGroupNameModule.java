package com.forzo.holdMyCard.ui.activities.creategroupname;


import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

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

}
