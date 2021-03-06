package com.forzo.holdMyCard.ui.activities.sort;

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
public class SortCardModule {


    @Provides
    @PerActivityScope
    SortCardPresenter sortCardPresenter(Context context) {
        return new SortCardPresenter(context);
    }
}
