package com.forzo.holdMyCard.ui.activities.login;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 5/7/2018.
 */

@Component(modules = {LoginModule.class})
@PerActivityScope
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
