package com.forzo.holdMyCard.ui.activities.userprofile;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {UserProfileModule.class})
@PerActivityScope
public interface UserProfileComponent {
    void inject(UserProfileActivity userProfileActivity);
}
