package com.forzo.holdMyCard.ui.activities.creategroup;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 4/3/2018.
 */

@Component(modules = {CreateGroupModule.class})
@PerActivityScope
public interface CreateGroupComponent {

    void inject(CreateGroupActivity createGroupActivity);
}
