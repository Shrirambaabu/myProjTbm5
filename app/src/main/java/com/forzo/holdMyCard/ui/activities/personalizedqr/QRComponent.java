package com.forzo.holdMyCard.ui.activities.personalizedqr;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {QRModule.class})
@PerActivityScope
public interface QRComponent {
    void inject(QRActivity qrActivity);
}
