package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import com.forzo.holdMyCard.base.PerFragmentScope;

import dagger.Component;

/**
 * Created by Shriram on 3/31/2018.
 */
@Component(modules = {MyCurrentLibraryFragmentModule.class})
@PerFragmentScope
public interface MyCurrentLibraryFragmentComponent {


    void inject(MyCurrentLibraryFragment myCurrentLibraryFragment);

}
