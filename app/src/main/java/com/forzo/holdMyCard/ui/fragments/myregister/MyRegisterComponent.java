package com.forzo.holdMyCard.ui.fragments.myregister;

import com.forzo.holdMyCard.base.PerFragmentScope;

import dagger.Component;

@Component(modules = {MyRegisterModule.class})
@PerFragmentScope
public interface MyRegisterComponent {
    void inject(MyRegister myRegister);
}
