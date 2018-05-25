package com.forzo.holdMyCard.ui.activities.customChooserDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.Profile.DaggerProfileComponent;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileContract;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leon on 24-05-18.
 */

public class CustomChooserDialog extends Activity implements CustomChooserDialogContract.View {
    private static final String TAG = "CustomChooserDialog";
    @Inject
    CustomChooserDialogPresenter customChooserDialogPresenter;
    private Context mContext = CustomChooserDialog.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialogue_choice);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DaggerCustomChooserDialogComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        customChooserDialogPresenter.attach(this);
    }

    @OnClick({R.id.create, R.id.view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create:
                String email = "";
                if (getIntent().getExtras() != null)
                    email = getIntent().getExtras().getString("email");
                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();
                endTime.add(Calendar.HOUR_OF_DAY, 1);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, email);
                startActivity(intent);
                break;
            case R.id.view:
                Intent calIntent = new Intent(Intent.ACTION_MAIN);
                calIntent.addCategory(Intent.CATEGORY_APP_CALENDAR);
                startActivity(calIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customChooserDialogPresenter.detach();
    }
}
