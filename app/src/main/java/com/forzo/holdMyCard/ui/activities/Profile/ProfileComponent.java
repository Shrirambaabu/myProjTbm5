package com.forzo.holdMyCard.ui.activities.Profile;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 3/29/2018.
 */
@Component(modules = {ProfileModule.class})
@PerActivityScope
public interface ProfileComponent {
    void inject(ProfileActivity profileActivity);
}
