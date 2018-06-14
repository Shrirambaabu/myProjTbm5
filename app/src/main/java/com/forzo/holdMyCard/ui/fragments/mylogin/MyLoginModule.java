package com.forzo.holdMyCard.ui.fragments.mylogin;

import android.content.Context;

import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.base.PerFragmentScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {FragmentContext.class})
@PerFragmentScope
public class MyLoginModule {

    @Provides
    @PerFragmentScope
    MyLoginPresenter myLoginPresenter(Context context) {
        return new MyLoginPresenter(context);
    }
}
