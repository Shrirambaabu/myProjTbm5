package com.forzo.holdMyCard.ui.activities.customChooserDialog;

import android.content.Context;

import com.forzo.holdMyCard.base.BasePresenter;

/**
 * Created by Leon on 24-05-18.
 */

public class CustomChooserDialogPresenter extends BasePresenter<CustomChooserDialogContract.View>
        implements CustomChooserDialogContract.Presenter {

    private Context context;

    public CustomChooserDialogPresenter(Context context) {
        this.context = context;
    }
}
