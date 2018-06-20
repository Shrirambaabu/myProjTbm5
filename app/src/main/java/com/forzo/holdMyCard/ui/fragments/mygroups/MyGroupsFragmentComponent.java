package com.forzo.holdMyCard.ui.fragments.mygroups;

import com.forzo.holdMyCard.base.PerFragmentScope;

import dagger.Component;

@Component(modules = {MyGroupsFragmentModule.class})
@PerFragmentScope
public interface MyGroupsFragmentComponent {

    void inject(MyGroupsFragment myGroupsFragment);
}
