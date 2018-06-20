package com.forzo.holdMyCard.ui.activities.creategroupname;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupActivity;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.recyclerAdapter.groupname.GroupNameListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.groupname.GroupNameRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class CreateGroupNameActivity extends AppCompatActivity implements CreateGroupNameContract.View {


    TextView mCreate;


    @Inject
    CreateGroupNamePresenter createGroupNamePresenter;

    @Inject
    GroupNameRecyclerAdapter groupNameRecyclerAdapter;
    @Inject
    ArrayList<Groups> groupsArrayList;
    @Inject
    GroupNameListPresenter groupNameListPresenter;

    @BindView(R.id.recycler_view_group_name)
    RecyclerView recyclerView;
    @BindView(R.id.card_number)
    TextView cardNumber;

    @BindView(R.id.group_name)
    EditText nameEditText;

    private Context mContext = CreateGroupNameActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_name);

        ButterKnife.bind(this);

        backButtonOnToolbar(CreateGroupNameActivity.this);

        DaggerCreateGroupNameComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);
        createGroupNamePresenter.attach(this);
        createGroupNamePresenter.getIntentValues(getIntent());
        createGroupNamePresenter.setupShowsRecyclerView(recyclerView);
    }


    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_group_name, menu);//Menu Resource, Menu

        MenuItem searchItem = menu.findItem(R.id.action_create);
        mCreate = (TextView) MenuItemCompat.getActionView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_create:

                if (nameEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter the Group name", Toast.LENGTH_LONG).show();
                    return false;
                }
                Log.e("Create","Clicked");
                Log.e("Create",""+groupsArrayList.size());
                if (!groupsArrayList.isEmpty()){
                    Log.e("Create",""+groupsArrayList.get(0).getUserId());
                    createGroupNamePresenter.createGroup(groupsArrayList,nameEditText.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(),"Group should contain atleast one member",Toast.LENGTH_LONG).show();
                }
               // createGroupNamePresenter.createNewGroup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(groupNameRecyclerAdapter);
        createGroupNamePresenter.populateRecyclerView(groupsArrayList);
    }

    @Override
    public void updateAdapter() {
        groupNameRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void cardNumber(int cardTotal) {
        cardNumber.setText("My Cards: "+cardTotal+" of 10");
    }

    @Override
    public void createGroupDone() {
        finish();
    }
}
