package com.forzo.holdMyCard.ui.activities.editgroupname;

import android.content.Intent;

import com.forzo.holdMyCard.base.BaseMvpPresenter;

public class EditGroupNameContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void getGroupName(Intent intent);

        void updateGroupName(String groupId,String groupName);
    }

    interface View {

        void setGroupName(String groupName);
        void setGroupId(String groupId);
        void renameStatus(String renameStatus);
        void activityLoader();
        void hideLoader();

    }
}
