package com.forzo.holdMyCard.ui.activities.remainderdetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.remainder.RemainderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class RemainderDetailsActivity extends AppCompatActivity {



    @BindView(R.id.save_remainder)
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_details);
        ButterKnife.bind(this);


        backButtonOnToolbar(RemainderDetailsActivity.this);
    }

    @OnClick(R.id.save_remainder)
    public void remainderSection() {

        Intent intent=new Intent(RemainderDetailsActivity.this, RemainderActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
