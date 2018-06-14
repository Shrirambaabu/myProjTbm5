package com.forzo.holdMyCard.ui.activities.userprofile;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class UserProfileModule {
    @Provides
    @PerActivityScope
    UserProfilePresenter sortCardPresenter(Context context) {
        return new UserProfilePresenter(context);
    }
}
