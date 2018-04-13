package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.List;

/**
 * Created by Shriram on 4/13/2018.
 */

public interface RemainderDetailsContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void showDatePicker();
        void saveRemainder(RemainderDetailsActivity remainderDetailsActivity, EditText editText, TextView dateText,TextView timeText);

    }

    interface View {

    void savedSuccessfully();
    }
}
