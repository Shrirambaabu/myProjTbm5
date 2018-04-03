package com.forzo.holdMyCard.ui.activities.Profile;

import android.graphics.Bitmap;
import android.widget.PopupWindow;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.google.api.services.vision.v1.model.Feature;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Shriram on 3/29/2018.
 */

public interface ProfileContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void bottomNavigationViewSetup(BottomNavigationViewEx bottomNavigationViewEx);

        void callCloudVision(Bitmap bitmap, Feature feature);

    }

    interface View {

        void viewBottomNavigation(BottomNavigationViewEx bottomNavigationViewEx);

    }

}
