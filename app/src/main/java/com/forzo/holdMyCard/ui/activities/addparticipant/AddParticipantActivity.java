package com.forzo.holdMyCard.ui.activities.addparticipant;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.groupdetails.GroupDetailsActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant.AddParticipantRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class AddParticipantActivity extends AppCompatActivity  implements AddParticipantContract.View {

    private Context mContext = AddParticipantActivity.this;


    @Inject
    AddParticipantPresenter addParticipantPresenter;

    @Inject
    AddParticipantRecyclerAdapter addParticipantRecyclerAdapter;


    @Inject
    ArrayList<MyLibrary> myLibraryArrayList;


    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    TextView addUser;
    @BindView(R.id.search_groups)
    android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        ButterKnife.bind(this);
        backButtonOnToolbar(AddParticipantActivity.this);

        DaggerAddParticipantComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);

        searchView.setQueryHint("Search for people to add");

        addParticipantPresenter.attach(this);
        addParticipantPresenter.getIntentValues(getIntent());
        addParticipantPresenter.setupShowsRecyclerView(recyclerView, emptyView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                searchView.clearFocus();
            }
        }, 300);


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_participant, menu);//Menu Resource, Menu

        MenuItem searchItem = menu.findItem(R.id.action_add_user);
        addUser = (TextView) MenuItemCompat.getActionView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_next:


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void updateAdapter() {
        addParticipantRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(addParticipantRecyclerAdapter);
        addParticipantPresenter.populateRecyclerView(myLibraryArrayList);
    }
}
