package com.forzo.holdMyCard.ui.activities.groupdetails;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.addparticipant.AddParticipantActivity;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.groupdetails.GroupDetailsRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class GroupDetailsActivity extends AppCompatActivity implements GroupDetailsContract.View {


    @Inject
    GroupDetailsPresenter groupDetailsPresenter;

    @Inject
    GroupDetailsRecyclerAdapter groupDetailsRecyclerAdapter;


    @Inject
    ArrayList<MyLibrary> myLibraryArrayList;


    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    @BindView(R.id.group_name)
    EditText groupNameEditText;

    @BindView(R.id.add_users)
    Button addParticipant;

    private TextView updateGroup;
    private Context mContext = GroupDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_group_details);


        ButterKnife.bind(this);

        backButtonOnToolbar(GroupDetailsActivity.this);


        DaggerGroupDetailsComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);


        groupDetailsPresenter.attach(this);
        groupDetailsPresenter.getIntentValues(getIntent());
        groupDetailsPresenter.setupShowsRecyclerView(recyclerView, emptyView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_group, menu);//Menu Resource, Menu

        MenuItem searchItem = menu.findItem(R.id.action_update);
        updateGroup = (TextView) MenuItemCompat.getActionView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_next:

                if (groupNameEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter your group name", Toast.LENGTH_LONG).show();
                    return false;
                }


                Log.e("Create", "" + groupNameEditText.getText().toString());


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void updateAdapter() {
        groupDetailsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(groupDetailsRecyclerAdapter);
        groupDetailsPresenter.populateRecyclerView(myLibraryArrayList);
    }

    @Override
    public void setGroupName(String groupName) {
        groupNameEditText.setText(groupName);
    }

    @OnClick(R.id.add_users)
    public void addUsers() {
        Intent addUsersIntent = new Intent(GroupDetailsActivity.this, AddParticipantActivity.class);
        startActivity(addUsersIntent);
    }
}
