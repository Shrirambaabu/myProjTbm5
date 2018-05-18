package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 4/13/2018.
 */

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class RemainderDetailsModule {


    @Provides
    @PerActivityScope
    RemainderDetailsPresenter remainderDetailsPresenter(Context context) {
        return new RemainderDetailsPresenter(context);
    }
}
