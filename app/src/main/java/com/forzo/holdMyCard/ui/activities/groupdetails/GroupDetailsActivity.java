package com.forzo.holdMyCard.ui.activities.groupdetails;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.creategroup.CreateGroupActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class GroupDetailsActivity extends AppCompatActivity {



    @BindView(R.id.group_name)
    EditText groupNameEditText;

    @BindView(R.id.add_users)
    Button addParticipant;

    private TextView updateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);


        ButterKnife.bind(this);

        backButtonOnToolbar(GroupDetailsActivity.this);

        String val= getIntent().getStringExtra("adapterPosition");

        if (val!=null){
            Log.e("GroupPosition",""+val);
        }
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
       finish();
    }
}
