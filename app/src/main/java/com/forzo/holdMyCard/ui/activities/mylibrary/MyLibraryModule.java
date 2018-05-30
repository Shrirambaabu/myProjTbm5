package com.forzo.holdMyCard.ui.activities.mylibrary;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.fragments.mycurrentlibrary.MyCurrentLibraryFragment;
import com.forzo.holdMyCard.ui.fragments.mygroups.MyGroupsFragment;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 3/29/2018.
 */

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class MyLibraryModule {

    private MyLibraryActivity libraryActivity;

    MyLibraryModule(MyLibraryActivity libraryActivity) {
        this.libraryActivity = libraryActivity;
    }

    @Provides
    @PerActivityScope
    MyLibraryPresenter libraryPresenter(Context context) {
        return new MyLibraryPresenter(context);
    }

    @Provides
    @PerActivityScope
    SectionsStatePagerAdapter providesSectionsStatePagerAdapter() {
        return new SectionsStatePagerAdapter(libraryActivity.getSupportFragmentManager());
    }

    @Provides
    @PerActivityScope
    MyCurrentLibraryFragment myLibraryFragment() {
        return new MyCurrentLibraryFragment();
    }

    @Provides
    @PerActivityScope
    MyGroupsFragment myGroupsFragment() {
        return new MyGroupsFragment();
    }
}
