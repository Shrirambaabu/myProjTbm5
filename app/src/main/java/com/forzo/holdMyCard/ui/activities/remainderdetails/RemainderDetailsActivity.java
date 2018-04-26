package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.remainder.ReminderActivity;
import com.forzo.holdMyCard.utils.DatePickerFragment;
import com.forzo.holdMyCard.utils.TimePickerFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;
import static com.forzo.holdMyCard.utils.Utils.convertToHourMinuteTimeSet;
import static com.forzo.holdMyCard.utils.Utils.formatDate;

public class RemainderDetailsActivity extends AppCompatActivity implements RemainderDetailsContract.View {

    static final int DATE_PICKER_ID = 1111;


    @BindView(R.id.save_remainder)
    Button button;
    @BindView(R.id.date_remainder)
    TextView datePickerValue;
    @BindView(R.id.time_remainder)
    TextView timePickerValue;

    @BindView(R.id.remain_des)
    EditText remainDes;
    @BindView(R.id.update_delete)
    LinearLayout linearLayout;
    @BindView(R.id.update_button)
    Button buttonUpdate;
    @BindView(R.id.delete_button)
    Button buttonDelete;

    @Inject
    RemainderDetailsPresenter remainderDetailsPresenter;

    private String reminderDetailId = "";
    private String reminderDetailDate = "";
    private String reminderDetailTime = "";
    private String result;

    private String libraryImageValue;
    @BindView(R.id.date_layout)
    LinearLayout datePickerLayout;
    private Context mContext = RemainderDetailsActivity.this;

    private String remainderKey="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_details);
        ButterKnife.bind(this);

        DaggerRemainderDetailsComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);
        remainderDetailsPresenter.attach(this);
        remainderDetailsPresenter.getIntentValues(getIntent(), button);
        backButtonOnToolbar(RemainderDetailsActivity.this);
    }

    @OnClick(R.id.save_remainder)
    public void remainderSection() {

        if (remainDes.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter your content", Toast.LENGTH_LONG).show();
            return;
        }
        if (datePickerValue.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please select the Date", Toast.LENGTH_LONG).show();
            return;
        }
        if (timePickerValue.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please select the time", Toast.LENGTH_LONG).show();
            return;
        } else {

            remainderDetailsPresenter.saveRemainder(RemainderDetailsActivity.this, remainDes, datePickerValue, timePickerValue,remainderKey);

        }


    }

    @OnClick(R.id.date_layout)
    public void showDialogPicker() {

        // remainderDetailsPresenter.showDatePicker();

        DatePickerFragment dateStart = new DatePickerFragment();
        // Set Up Current Date Into dialog
        dateStart.setArguments(dateDialog());
        //Set Call back to capture selected date
        dateStart.setCallBack(onStartDateSetListener);
        dateStart.show(getSupportFragmentManager(), "Date Picker");

    }

    @OnClick(R.id.update_button)
    public void updateReminderSection() {

        remainderDetailsPresenter.updateReminder(reminderDetailId, remainDes.getText().toString(), timePickerValue.getText().toString(), datePickerValue.getText().toString());

    }

    @OnClick(R.id.delete_button)
    public void deleteReminderSection() {

        remainderDetailsPresenter.deleteReminder(reminderDetailId);

    }

    @OnClick(R.id.time_layout)
    public void showTimePicker() {
        result = null;
        // remainderDetailsPresenter.showDatePicker();
        TimePickerFragment timeStart = new TimePickerFragment();
        timeStart.setArguments(timePickerDialog());
        timeStart.setCallBack(ontime);
        timeStart.show(getSupportFragmentManager(), "Time Picker");

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    private Bundle dateDialog() {

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        return args;
    }

    private Bundle timePickerDialog() {

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", Calendar.HOUR_OF_DAY);
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("minute", calender.get(Calendar.MINUTE));
        return args;
    }


    DatePickerDialog.OnDateSetListener onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            String dateSet = formatDate(year, monthOfYear, dayOfMonth);

            Log.e("Date", "" + dateSet);

            datePickerValue.setText(dateSet);
            reminderDetailDate = dateSet;
        }
    };


    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            ArrayList<String> timeArrayList = convertToHourMinuteTimeSet(hourOfDay, minute);
            String timeStart = String.valueOf(timeArrayList.get(0) + timeArrayList.get(1) + timeArrayList.get(2));


            SimpleDateFormat dateFormat = new SimpleDateFormat("hhmmaa");

            try {
                Date date = dateFormat.parse(timeStart);

                SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
                result = df.format(date);


            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("time", "" + result);

            if (result == null) {

                Toast.makeText(getApplicationContext(), "Please set the correct time", Toast.LENGTH_LONG).show();

                Calendar cal = Calendar.getInstance();
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("hh:mm a");

                result = date.format(currentLocalTime);

            }
            timePickerValue.setText(result);
            reminderDetailTime = result;

        }

    };

    @Override
    public void savedSuccessfully() {
        Toast.makeText(getApplicationContext(), "Remainder Added Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RemainderDetailsActivity.this, ReminderActivity.class);
        intent.putExtra("libraryProfile",""+remainderKey);
        intent.putExtra("libraryProfileImage",""+libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void updatedSuccessfully() {

        Toast.makeText(getApplicationContext(), "Remainder Updated Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RemainderDetailsActivity.this, ReminderActivity.class);
        intent.putExtra("libraryProfile",""+remainderKey);
        intent.putExtra("libraryProfileImage",""+libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void deletedSuccessfully() {

        Toast.makeText(getApplicationContext(), "Remainder Deleted Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RemainderDetailsActivity.this, ReminderActivity.class);
        intent.putExtra("libraryProfile",""+remainderKey);
        intent.putExtra("libraryProfileImage",""+libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void setSaveVisible() {
        linearLayout.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void setReminderPrimaryValue(String profile,String image) {
        libraryImageValue=image;
        remainderKey=profile;
    }

    @Override
    public void remainderText(String remainder) {
        remainDes.setText(remainder);
    }

    @Override
    public void remainderDate(String date) {

        datePickerValue.setText(date);
    }

    @Override
    public void remainderTime(String time) {
        Log.e("Main",""+time);
        timePickerValue.setText(time);
        Log.e("MainAfter",""+timePickerValue.getText().toString());
    }

    @Override
    public void remainderDetails(String id, String datePickerValues, String timePickerValue) {
        reminderDetailId = id;
        button.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

    }
}
