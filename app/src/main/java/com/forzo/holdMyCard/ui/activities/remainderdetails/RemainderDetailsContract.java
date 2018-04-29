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
        void saveRemainder(RemainderDetailsActivity remainderDetailsActivity, EditText editText, TextView dateText,TextView timeText,String key);

        void getIntentValues(Intent intent, Button button);

        void callReminderDetails(String s);

        void updateReminder(String reminderId,String reminderDesc,String timePicker,String datePicker);
        void deleteReminder(String reminderId);

    }

    interface View {

    void savedSuccessfully();
    void updatedSuccessfully();
    void deletedSuccessfully();
    void reminderDetails(String s);
    void setSaveVisible();
void setReminderPrimaryValue(String profile,String image);
    void remainderText(String remainder);
    void remainderDate(String date);
    void remainderTime(String time);
    void remainderDetails(String id,String date,String time);
    }
}
