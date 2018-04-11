package com.forzo.holdMyCard;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 4/11/2018.
 */
@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @HmcScope
    public Context getContext() {
        return context;
    }
}
