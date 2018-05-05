package com.forzo.holdMyCard.ui.activities.remainder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.activities.remainderdetails.RemainderDetailsActivity;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.remainder.MyRemainderRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class ReminderActivity extends AppCompatActivity  implements ReminderContract.View{



    @Inject
    ReminderPresenter reminderPresenter;

    @Inject
    MyRemainderRecyclerAdapter myRemainderRecyclerAdapter;


    @Inject
    ArrayList<MyRemainder> myRemainderArrayList;


    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    @BindView(R.id.add_remainder)
    Button button;

    private String remainderKey="";
    private Context mContext = ReminderActivity.this;

    private String libraryImageValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);
        ButterKnife.bind(this);

        DaggerRemainderComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);



        backButtonOnToolbar(ReminderActivity.this);


        reminderPresenter.attach(this);
        reminderPresenter.getIntentValues(getIntent());
        reminderPresenter.setupShowsRecyclerView(recyclerView, emptyView);

    }



    @OnClick(R.id.add_remainder)
    public void addNoteSection() {

        Intent intentSave = new Intent(ReminderActivity.this, RemainderDetailsActivity.class);
        intentSave.putExtra("remainId","new");
        intentSave.putExtra("libraryProfile",""+remainderKey);
        intentSave.putExtra("libraryProfileImage",""+libraryImageValue);
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ReminderActivity.this, ProfileActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.putExtra("libraryProfile",""+remainderKey);
        intent.putExtra("libraryProfileImage",""+libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReminderActivity.this, ProfileActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.putExtra("libraryProfile",""+remainderKey);
        intent.putExtra("libraryProfileImage",""+libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(myRemainderRecyclerAdapter);
        reminderPresenter.populateRecyclerView(myRemainderArrayList,remainderKey,libraryImageValue);
    }

    @Override
    public void setReminderPrimaryValue(String reminderPrimaryValue,String image) {
        libraryImageValue=image;
        remainderKey=reminderPrimaryValue;
    }

    @Override
    public void updateAdapter() {
        myRemainderRecyclerAdapter.notifyDataSetChanged();
    }
}
