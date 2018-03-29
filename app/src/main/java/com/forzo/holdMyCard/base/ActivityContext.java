package com.forzo.holdMyCard.base;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 3/29/2018.
 */
@Module
@PerActivityScope
public class ActivityContext {

    private Context context;

    public ActivityContext(Context context) {
        this.context = context;
    }

    @PerActivityScope
    @Provides
    public Context getContext() {
        return context;
    }
}
