package com.forzo.holdMyCard.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Shriram on 4/13/2018.
 */

public class TimePickerFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener onTimeSet;

    public TimePickerFragment() {

    }
    public void setCallBack(TimePickerDialog.OnTimeSetListener ontime) {
        onTimeSet = ontime;
    }
    private int hour, minute;
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        minute = args.getInt("minute");

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), onTimeSet, hour, minute, false);
    }
}
