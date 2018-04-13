package com.forzo.holdMyCard.ui.activities.remainderdetails;

import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;

import dagger.Component;

/**
 * Created by Shriram on 4/13/2018.
 */

@Component(modules = {RemainderDetailsModule.class})
@PerActivityScope
public interface RemainderDetailsComponent {

    void inject(RemainderDetailsActivity remainderDetailsActivity);
}
