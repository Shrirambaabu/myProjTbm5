package com.forzo.holdMyCard.ui.activities.loginregister;


import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {LoginRegisterModule.class})
@PerActivityScope
public interface LoginRegisterComponent {

    void inject(LoginRegisterActivity loginRegisterActivity);

}
