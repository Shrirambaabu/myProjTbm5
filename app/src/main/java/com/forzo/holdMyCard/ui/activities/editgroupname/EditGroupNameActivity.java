package com.forzo.holdMyCard.ui.activities.editgroupname;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class EditGroupNameActivity extends AppCompatActivity implements EditGroupNameContract.View {


    @Inject
    EditGroupNamePresenter editGroupNamePresenter;

    @BindView(R.id.group_name)
    EditText groupNameEditText;
    @BindView(R.id.okay_name)
    Button okayButton;
    @BindView(R.id.cancel_name)
    Button cancelButton;

    private Context mContext = EditGroupNameActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_name);


        ButterKnife.bind(this);

        backButtonOnToolbar(EditGroupNameActivity.this);


        DaggerEditGroupNameComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);
        editGroupNamePresenter.attach(this);
        editGroupNamePresenter.getGroupName(getIntent());
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
    public void setGroupName(String groupName) {
        groupNameEditText.setText(groupName);
    }

    @OnClick(R.id.okay_name)
    public void editGroupName() {
        Log.e("GName:",""+groupNameEditText.getText().toString());
    }
    @OnClick(R.id.cancel_name)
    public void editCancelGroupName() {
        finish();
    }
}
