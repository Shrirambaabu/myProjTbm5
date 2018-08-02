package com.forzo.holdMyCard.ui.activities.editgroupname;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.wang.avi.AVLoadingIndicatorView;

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

    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;
    private String userGroupId="";
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

    @Override
    public void setGroupId(String groupId) {
        this.userGroupId=groupId;
    }

    @Override
    public void renameStatus(String renameStatus) {
        if (renameStatus.equals("true")){
            Toast.makeText(getApplicationContext(),"Group Renamed",Toast.LENGTH_LONG).show();
            Intent myLibrary = new Intent(EditGroupNameActivity.this, MyLibraryActivity.class);
            myLibrary.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myLibrary);
        }else if (renameStatus.equals("false")){
            Toast.makeText(getApplicationContext(),"There was Error in updating Group Name",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void activityLoader() {
        relativeLayout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();
    }

    @Override
    public void hideLoader() {
        relativeLayout.setVisibility(View.GONE);
        avLoadingIndicatorView.setVisibility(View.GONE);
        avLoadingIndicatorView.hide();
    }
    @OnClick(R.id.okay_name)
    public void editGroupName() {
        if (groupNameEditText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Enter the Group Name",Toast.LENGTH_LONG).show();
            return;
        }
        editGroupNamePresenter.updateGroupName(userGroupId,groupNameEditText.getText().toString());
    }
    @OnClick(R.id.cancel_name)
    public void editCancelGroupName() {
        finish();
    }
}
