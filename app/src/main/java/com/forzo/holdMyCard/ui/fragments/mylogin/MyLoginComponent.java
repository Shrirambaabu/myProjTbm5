package com.forzo.holdMyCard.ui.fragments.mylogin;

import com.forzo.holdMyCard.base.PerFragmentScope;

import dagger.Component;

@Component(modules = {MyLoginModule.class})
@PerFragmentScope
public interface MyLoginComponent {
    void inject(MyLogin myLogin);
}
