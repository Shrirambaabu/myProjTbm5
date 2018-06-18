package com.forzo.holdMyCard.ui.activities.creategroupname;

import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupActivity;

import dagger.Component;

@Component(modules = {CreateGroupNameModule.class})
@PerActivityScope
public interface CreateGroupNameComponent {

    void inject(CreateGroupNameActivity createGroupNameActivity);
}
