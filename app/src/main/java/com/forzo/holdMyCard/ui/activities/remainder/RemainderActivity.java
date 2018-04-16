package com.forzo.holdMyCard.ui.activities.remainder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
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

public class RemainderActivity extends AppCompatActivity  implements RemainderContract.View{



    @Inject
    RemainderPresenter remainderPresenter;

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

    private Context mContext = RemainderActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);
        ButterKnife.bind(this);

        DaggerRemainderComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);



        backButtonOnToolbar(RemainderActivity.this);


        remainderPresenter.attach(this);
        remainderPresenter.setupShowsRecyclerView(recyclerView, emptyView);

    }



    @OnClick(R.id.add_remainder)
    public void addNoteSection() {

        Intent intentSave = new Intent(RemainderActivity.this, RemainderDetailsActivity.class);
        intentSave.putExtra("remainDesc","");
        intentSave.putExtra("remainTime","");
        intentSave.putExtra("remainDate","");
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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

    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(myRemainderRecyclerAdapter);
        remainderPresenter.populateRecyclerView(myRemainderArrayList);
    }

    @Override
    public void updateAdapter() {
        myRemainderRecyclerAdapter.notifyDataSetChanged();
    }
}
