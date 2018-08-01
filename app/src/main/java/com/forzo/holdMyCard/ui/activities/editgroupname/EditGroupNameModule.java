package com.forzo.holdMyCard.ui.activities.editgroupname;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class EditGroupNameModule {


    @Provides
    @PerActivityScope
    EditGroupNamePresenter groupDetailsPresenter(Context context) {
        return new EditGroupNamePresenter(context);
    }

}
