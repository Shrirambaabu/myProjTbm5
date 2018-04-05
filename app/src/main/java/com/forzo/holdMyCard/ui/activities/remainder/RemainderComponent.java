package com.forzo.holdMyCard.ui.activities.remainder;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 4/5/2018.
 */

@Component(modules = {RemainderModule.class})
@PerActivityScope
public interface RemainderComponent {
    void inject(RemainderActivity remainderActivity);
}
