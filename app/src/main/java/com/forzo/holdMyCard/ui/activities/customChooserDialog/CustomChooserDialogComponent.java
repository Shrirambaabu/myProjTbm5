package com.forzo.holdMyCard.ui.activities.customChooserDialog;

import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

/**
 * Created by Leon on 24-05-18.
 */

@Component(modules = {CustomChooserDialogModule.class})
@PerActivityScope
public interface CustomChooserDialogComponent {
    void inject(CustomChooserDialog chooserDialog);
}
