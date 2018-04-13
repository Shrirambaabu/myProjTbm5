package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.widget.EditText;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.activities.remainderdetails.RemainderDetailsContract;

/**
 * Created by Shriram on 4/13/2018.
 */

public interface NotesDetailContract {

    interface Presenter extends BaseMvpPresenter<NotesDetailContract.View> {

void saveNote(NotesDetailsActivity notesDetailsActivity, EditText editText);

    }

    interface View {

        void savedSuccessfully();

    }
}
