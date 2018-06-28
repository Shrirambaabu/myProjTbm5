package com.forzo.holdMyCard.ui.activities.groupdetails;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {GroupDetailsModule.class})
@PerActivityScope
public interface GroupDetailsComponent {

    void inject(GroupDetailsActivity groupDetailsActivity);
}
