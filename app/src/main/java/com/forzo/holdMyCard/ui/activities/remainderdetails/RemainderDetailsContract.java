package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.content.Intent;
import android.widget.Button;
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

        void getIntentValues(Intent intent, Button button);

    }

    interface View {

    void savedSuccessfully();

    void remainderText(String remainder);
    void remainderDate(String date);
    void remainderTime(String time);
    }
}