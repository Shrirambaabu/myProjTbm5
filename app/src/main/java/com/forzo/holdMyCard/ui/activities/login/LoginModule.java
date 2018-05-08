package com.forzo.holdMyCard.ui.activities.login;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 5/7/2018.
 */

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class LoginModule {


    @Provides
    @PerActivityScope
    LoginPresenter sortCardPresenter(Context context) {
        return new LoginPresenter(context);
    }
}
