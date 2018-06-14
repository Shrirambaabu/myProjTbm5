package com.forzo.holdMyCard.ui.activities.newcard;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class NewCardModule {
    @Provides
    @PerActivityScope
    NewCardPresenter newCardPresenter(Context context) {
        return new NewCardPresenter(context);
    }
}
