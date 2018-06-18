package com.forzo.holdMyCard.ui.activities.creategroupname;

import android.content.Intent;

import com.forzo.holdMyCard.base.BaseMvpPresenter;

public interface CreateGroupNameContract {


    interface Presenter extends BaseMvpPresenter<View> {
        void getIntentValues(Intent intent);
    }

    // Action callbacks. Activity/Fragment will implement
    interface View {

    }
}
