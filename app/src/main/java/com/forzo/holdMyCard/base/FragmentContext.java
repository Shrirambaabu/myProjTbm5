package com.forzo.holdMyCard.base;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 3/29/2018.
 */

@Module
@PerFragmentScope
public class FragmentContext {

    private Context context;

    public FragmentContext(Context context) {
        this.context = context;
    }

    @Provides
    @PerFragmentScope
    public Context getContext() {
        return context;
    }
}
