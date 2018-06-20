package com.forzo.holdMyCard.ui.activities.sortDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SortDialogActivity extends AppCompatActivity {


    @BindView(R.id.asc_order)
    TextView ascendingDate;
    @BindView(R.id.des_order)
    TextView descDate;
    @BindView(R.id.atoz)
    TextView ascendingAlpha;
    @BindView(R.id.ztoa)
    TextView descAlpha;

    private static int dateValue = 0;
    private static int alphabetValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_dialog);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ascDateSort();
        ascAlphaSort();
    }


    @OnClick(R.id.cancel_sort)
    public void cancelSort() {
        finish();
        Log.e("Cancel", "Date: " + dateValue + " Alpha: " + alphabetValue);
    }

    @OnClick(R.id.accept_sort)
    public void acceptSort() {
        Intent intent=new Intent(SortDialogActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("alpha",""+alphabetValue);
        intent.putExtra("date",""+dateValue);
        startActivity(intent);
        Log.e("Accept", "Date: " + dateValue + " Alpha: " + alphabetValue);
    }

    @OnClick(R.id.asc_order)
    public void ascDateSort() {

        ascendingDate.setBackgroundResource(R.drawable.sort_selected);
        ascendingDate.setTextColor(Color.WHITE);
        descDate.setBackgroundResource(R.drawable.sort_type);
        descDate.setTextColor(Color.BLACK);
        dateValue = 0;
    }

    @OnClick(R.id.atoz)
    public void ascAlphaSort() {

        ascendingAlpha.setBackgroundResource(R.drawable.sort_selected);
        ascendingAlpha.setTextColor(Color.WHITE);
        descAlpha.setBackgroundResource(R.drawable.sort_type);
        descAlpha.setTextColor(Color.BLACK);
        alphabetValue = 0;
    }

    @OnClick(R.id.des_order)
    public void dscDateSort() {

        descDate.setBackgroundResource(R.drawable.sort_selected);
        descDate.setTextColor(Color.WHITE);
        ascendingDate.setBackgroundResource(R.drawable.sort_type);
        ascendingDate.setTextColor(Color.BLACK);
        dateValue = 1;
    }

    @OnClick(R.id.ztoa)
    public void dscAlphaSort() {

        descAlpha.setBackgroundResource(R.drawable.sort_selected);
        descAlpha.setTextColor(Color.WHITE);
        ascendingAlpha.setBackgroundResource(R.drawable.sort_type);
        ascendingAlpha.setTextColor(Color.BLACK);
        alphabetValue = 1;
    }
}
