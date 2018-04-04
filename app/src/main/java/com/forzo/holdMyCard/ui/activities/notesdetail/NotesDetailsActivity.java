package com.forzo.holdMyCard.ui.activities.notesdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.forzo.holdMyCard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesDetailsActivity extends AppCompatActivity {



    @BindView(R.id.save_note)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);
        ButterKnife.bind(this);

    }
}
