package com.forzo.holdMyCard.ui.activities.forgetpassword;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class ForgetPasswordModule {

    @Provides
    @PerActivityScope
    ForgetPasswordPresenter forgetPasswordPresenter(Context context) {
        return new ForgetPasswordPresenter(context);
    }

}
