package com.forzo.holdMyCard.ui.activities.newcard;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {NewCardModule.class})
@PerActivityScope
public interface NewCardComponent {
    void inject(NewCardActivity newCardActivity);
}
