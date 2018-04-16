package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.forzo.holdMyCard.base.BaseMvpPresenter;
import com.forzo.holdMyCard.ui.activities.remainderdetails.RemainderDetailsContract;

/**
 * Created by Shriram on 4/13/2018.
 */

public interface NotesDetailContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void saveNote(NotesDetailsActivity notesDetailsActivity, EditText editText);

        void getIntentValues(Intent intent, Button button);

    }

    interface View {

        void savedSuccessfully();
void setNotesValueEnabled();
        void setNotesValue(String value);
    }
}
