package com.forzo.holdMyCard.ui.activities.home;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 3/29/2018.
 */
@Component(modules = {HomeModule.class})
@PerActivityScope
public interface HomeComponent {

    void inject(HomeActivity homeActivity);
}
