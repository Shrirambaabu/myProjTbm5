package com.forzo.holdMyCard.ui.activities.addparticipant;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.groupdetails.GroupDetailsActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant.AddParticipantAdapterListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant.AddParticipantRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class AddParticipantActivity extends AppCompatActivity implements AddParticipantContract.View {

    private Context mContext = AddParticipantActivity.this;


    @Inject
    AddParticipantPresenter addParticipantPresenter;
    @Inject
    AddParticipantAdapterListPresenter addParticipantAdapterListPresenter;

    @Inject
    AddParticipantRecyclerAdapter addParticipantRecyclerAdapter;


    @Inject
    ArrayList<MyLibrary> myLibraryArrayList;


    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;
    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;
    TextView addUser;
    private String groupId = "", groupName = "";

    private ArrayList<MyLibrary> myGroupsArrayList = new ArrayList<MyLibrary>();
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
        addParticipantPresenter.getIntentValues(getIntent(), myGroupsArrayList);
        addParticipantPresenter.setupShowsRecyclerView(recyclerView, emptyView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                searchView.clearFocus();
            }
        }, 300);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                final ArrayList<MyLibrary> filtermodelist = filter(myLibraryArrayList, newText);
                addParticipantAdapterListPresenter.setFilter(filtermodelist, addParticipantRecyclerAdapter);


                return true;
            }
        });
    }

    private ArrayList<MyLibrary> filter(ArrayList<MyLibrary> myLibraryArrayList, String query) {

        query = query.toLowerCase();
        final ArrayList<MyLibrary> filteredModeList = new ArrayList<>();
        for (MyLibrary model : myLibraryArrayList) {
            final String text = model.getCardName().toLowerCase();
            final String email = model.getCardDescription().toLowerCase();
            final String phone = model.getCardDetails().toLowerCase();
            if (text.contains(query) || email.contains(query) || phone.contains(query)) {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;

    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent addUsersIntent = new Intent(AddParticipantActivity.this, GroupDetailsActivity.class);
        addUsersIntent.putExtra("groupName", "" + groupName);
        addUsersIntent.putExtra("groupId", "" + groupId);
        startActivity(addUsersIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent addUsersIntent = new Intent(AddParticipantActivity.this, GroupDetailsActivity.class);
        addUsersIntent.putExtra("groupName", "" + groupName);
        addUsersIntent.putExtra("groupId", "" + groupId);
        startActivity(addUsersIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

            case R.id.action_add_user:

                addParticipantPresenter.addNewUserToGroup(groupId);

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

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public void myGroupMembers(ArrayList<MyLibrary> myGroupsArrayList2) {
        this.myGroupsArrayList = myGroupsArrayList2;
        addParticipantAdapterListPresenter.setPrevGroupList(myGroupsArrayList);
    }

    @Override
    public void personsAdded() {
        Toast.makeText(getApplicationContext(), "Users Added", Toast.LENGTH_LONG).show();
        Intent addUsersIntent = new Intent(AddParticipantActivity.this, GroupDetailsActivity.class);
        addUsersIntent.putExtra("groupName", "" + groupName);
        addUsersIntent.putExtra("groupId", "" + groupId);
        startActivity(addUsersIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showProgressBar() {
        relativeLayout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();
    }

    @Override
    public void hideProgressBar() {
        relativeLayout.setVisibility(View.GONE);
        avLoadingIndicatorView.setVisibility(View.GONE);
        avLoadingIndicatorView.hide();
    }
}
