package com.forzo.holdMyCard.ui.activities.personalizedqr;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class QRModule{
    @Provides
    @PerActivityScope
    QRPresenter notesDetailsPresenter(Context context) {
        return new QRPresenter(context);
    }
}
