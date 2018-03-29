package com.forzo.holdMyCard.ui.activities.sort;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Shriram on 3/29/2018.
 */

@Component(modules = {SortCardModule.class})
@PerActivityScope
public interface SortCardComponent {

    void inject(SortCardActivity sortCardActivity);

}
