package com.forzo.holdMyCard.ui.activities.creategroupname;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class CreateGroupNameActivity extends AppCompatActivity implements CreateGroupNameContract.View {


    TextView mCreate;


    @Inject
    CreateGroupNamePresenter createGroupNamePresenter;

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

        createGroupNamePresenter.getIntentValues(getIntent());
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

                Log.e("Create","Clicked");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
