package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.BaseView;
import com.forzo.holdMyCard.ui.activities.notes.NotesActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class NotesDetailsActivity extends AppCompatActivity implements NotesDetailContract.View {


    @BindView(R.id.save_note)
    Button button;
    @BindView(R.id.note_des)
    EditText noteDes;

    @BindView(R.id.update_delete)
    LinearLayout linearLayout;
    @BindView(R.id.update_button)
    Button buttonUpdate;
    @BindView(R.id.delete_button)
    Button buttonDelete;

    private String notesDetailId = "";

    @Inject
    NotesDetailsPresenter notesDetailsPresenter;

    private String notePrimaryValue = "";
    private String libraryImageValue = "";
    private Context mContext = NotesDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);
        ButterKnife.bind(this);

        DaggerNotesDetailsComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);
        notesDetailsPresenter.attach(this);
        notesDetailsPresenter.getIntentValues(getIntent(), button);
        backButtonOnToolbar(NotesDetailsActivity.this);
    }

    @OnClick(R.id.save_note)
    public void notesSection() {

        Log.e("Text", "" + noteDes.getText().toString());

        if (noteDes.getText().toString().equals("")) {
            Log.e("Text", "null");
            Toast.makeText(getApplicationContext(), "Please enter your content", Toast.LENGTH_LONG).show();
            return;
        } else {

            notesDetailsPresenter.saveNote(NotesDetailsActivity.this, noteDes, notePrimaryValue);

            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }


        //      if (noteDes.getText())

       /* Intent intent=new Intent(NotesDetailsActivity.this, NotesActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/

    }

    @OnClick(R.id.update_button)
    public void updateNotesSection() {

        Log.e("TextNotesIdUpdate", "" + notesDetailId);

        notesDetailsPresenter.updateNotes(notesDetailId, noteDes.getText().toString());

    }

    @OnClick(R.id.delete_button)
    public void deleteNotesSection() {

        Log.e("TextNotesIdUpdate", "" + notesDetailId);

        notesDetailsPresenter.deleteNotes(notesDetailId);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void savedSuccessfully() {
        Toast.makeText(getApplicationContext(), "Note Added Successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(NotesDetailsActivity.this, NotesActivity.class);
        intent.putExtra("libraryProfile", "" + notePrimaryValue);
        intent.putExtra("libraryProfileImage", "" + libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void updatedSuccessfully() {

        Toast.makeText(getApplicationContext(), "Note Updated Successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(NotesDetailsActivity.this, NotesActivity.class);
        intent.putExtra("libraryProfile", "" + notePrimaryValue);
        intent.putExtra("libraryProfileImage", "" + libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void deletedSuccessfully() {

        Toast.makeText(getApplicationContext(), "Note Deleted Successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(NotesDetailsActivity.this, NotesActivity.class);
        intent.putExtra("libraryProfile", "" + notePrimaryValue);
        intent.putExtra("libraryProfileImage", "" + libraryImageValue);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void setNotesPrimaryValue(String value, String image) {
        libraryImageValue = image;
        notePrimaryValue = value;
    }

    @Override
    public void setNotesValueEnabled() {
        noteDes.setEnabled(false);
    }

    @Override
    public void setNotesValue(String value, String notesId) {
        noteDes.setText(value);
        button.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        notesDetailId = notesId;
    }

    @Override
    public void setSaveButton() {

        button.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

    }

}
