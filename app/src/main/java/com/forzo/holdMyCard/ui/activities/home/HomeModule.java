package com.forzo.holdMyCard.ui.activities.home;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 3/29/2018.
 */
@Module(includes = {ActivityContext.class})
@PerActivityScope
public class HomeModule {

    @Provides
    @PerActivityScope
    HomePresenter providesHomePresenter(Context context) {
        return new HomePresenter(context);
    }

}
