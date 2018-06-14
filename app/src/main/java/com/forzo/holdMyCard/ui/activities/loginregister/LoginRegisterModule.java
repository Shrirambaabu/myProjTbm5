package com.forzo.holdMyCard.ui.activities.loginregister;


import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.fragments.mylogin.MyLogin;
import com.forzo.holdMyCard.ui.fragments.myregister.MyRegister;
import com.forzo.holdMyCard.utils.SectionsStatePagerAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class LoginRegisterModule {


    private LoginRegisterActivity loginRegisterActivity;

    LoginRegisterModule(LoginRegisterActivity loginRegisterActivity1) {
        this.loginRegisterActivity = loginRegisterActivity1;
    }


    @Provides
    @PerActivityScope
    LoginRegisterPresenter loginRegisterPresenter(Context context) {
        return new LoginRegisterPresenter(context);
    }


    @Provides
    @PerActivityScope
    SectionsStatePagerAdapter providesSectionsStatePagerAdapter() {
        return new SectionsStatePagerAdapter(loginRegisterActivity.getSupportFragmentManager());
    }


    @Provides
    @PerActivityScope
    MyLogin myLogin() {
        return new MyLogin();
    }

    @Provides
    @PerActivityScope
    MyRegister myRegister() {
        return new MyRegister();
    }
}
