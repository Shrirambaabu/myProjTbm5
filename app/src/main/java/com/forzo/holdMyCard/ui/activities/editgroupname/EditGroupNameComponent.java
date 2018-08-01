package com.forzo.holdMyCard.ui.activities.editgroupname;


import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {EditGroupNameModule.class})
@PerActivityScope
public interface EditGroupNameComponent {

    void inject(EditGroupNameActivity editGroupNameActivity);
}
