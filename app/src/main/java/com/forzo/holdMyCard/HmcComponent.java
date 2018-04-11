package com.forzo.holdMyCard;

import dagger.Component;

/**
 * Created by Shriram on 4/11/2018.
 */

@Component(modules = {RetrofitModule.class, HmcAppModule.class})
@HmcScope
public interface HmcComponent {

    void inject(HmcApplication hmcApplication);

}
