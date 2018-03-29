package com.forzo.holdMyCard.ui.activities.library;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 3/29/2018.
 */
@Component(modules = {MyLibraryModule.class})
@PerActivityScope
public interface MyLibraryComponent {

    void inject(MyLibraryActivity myLibraryActivity);

}
