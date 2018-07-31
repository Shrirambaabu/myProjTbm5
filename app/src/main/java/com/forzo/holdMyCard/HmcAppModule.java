package com.forzo.holdMyCard;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 4/11/2018.
 */

@Module
@HmcScope
public class HmcAppModule {

    private Application mApplication;

    HmcAppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @HmcScope
    Application provideApplication() {
        return mApplication;
    }


}
