package com.forzo.holdMyCard.ui.activities.notes;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 4/4/2018.
 */

@Component(modules = {NotesModule.class})
@PerActivityScope
public interface NotesComponent {

    void inject(NotesActivity notesActivity);
}
