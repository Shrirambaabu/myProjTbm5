package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 4/13/2018.
 */

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class NotesDetailModule {

    @Provides
    @PerActivityScope
    NotesDetailsPresenter notesDetailsPresenter(Context context) {
        return new NotesDetailsPresenter(context);
    }
}
