package com.forzo.holdMyCard.ui.activities.forgetpassword;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {ForgetPasswordModule.class})
@PerActivityScope
public interface ForgetPasswordComponent {
    void inject(ForgetPasswordActivity forgetPasswordActivity);
}
