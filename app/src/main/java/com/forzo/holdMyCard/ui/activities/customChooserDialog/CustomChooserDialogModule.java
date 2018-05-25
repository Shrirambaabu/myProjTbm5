package com.forzo.holdMyCard.ui.activities.customChooserDialog;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 24-05-18.
 */
@Module(includes = {ActivityContext.class})
@PerActivityScope
public class CustomChooserDialogModule {

    @Provides
    @PerActivityScope
    CustomChooserDialogPresenter providesCustomChooserDialogPresenter(Context context) {
        return new CustomChooserDialogPresenter(context);
    }
}
