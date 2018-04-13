package com.forzo.holdMyCard.ui.activities.notesdetail;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 4/13/2018.
 */

@Component(modules = {NotesDetailModule.class})
@PerActivityScope
public interface NotesDetailsComponent {
    void inject(NotesDetailsActivity notesDetailsActivity);
}
