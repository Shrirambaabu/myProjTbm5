package com.forzo.holdMyCard.ui.fragments.myregister;

import android.content.Context;

import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.base.PerFragmentScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {FragmentContext.class})
@PerFragmentScope
public class MyRegisterModule {


    @Provides
    @PerFragmentScope
    MyRegisterPresenter myRegisterPresenter(Context context) {
        return new MyRegisterPresenter(context);
    }
}
