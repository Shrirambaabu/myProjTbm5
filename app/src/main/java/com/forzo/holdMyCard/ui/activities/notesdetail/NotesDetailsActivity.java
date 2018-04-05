package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.notes.NotesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class NotesDetailsActivity extends AppCompatActivity {



    @BindView(R.id.save_note)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);
        ButterKnife.bind(this);
        backButtonOnToolbar(NotesDetailsActivity.this);
    }

    @OnClick(R.id.save_note)
    public void notesSection() {

        Intent intent=new Intent(NotesDetailsActivity.this, NotesActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
