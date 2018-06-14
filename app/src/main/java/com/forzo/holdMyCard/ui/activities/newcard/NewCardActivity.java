package com.forzo.holdMyCard.ui.activities.newcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.notes.DaggerNotesComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class NewCardActivity extends AppCompatActivity implements NewCardContract.View {

    @Inject
    NewCardPresenter newCardPresenter;
    private Context mContext = NewCardActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        ButterKnife.bind(this);

        backButtonOnToolbar(NewCardActivity.this);
        DaggerNewCardComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        newCardPresenter.attach(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(NewCardActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }
}
