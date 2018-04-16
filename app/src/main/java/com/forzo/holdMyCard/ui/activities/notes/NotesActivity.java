package com.forzo.holdMyCard.ui.activities.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.notesdetail.NotesDetailsActivity;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.mynotes.MyNotesRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class NotesActivity extends AppCompatActivity implements NotesContract.View{


    @Inject
    MyNotesPresenter notesPresenter;

    @Inject
    MyNotesRecyclerAdapter notesRecyclerAdapter;


    @Inject
    ArrayList<MyNotes> myNotesArrayList;


    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    @BindView(R.id.add_note)
    Button button;


    private Context mContext = NotesActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);

        DaggerNotesComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);


        backButtonOnToolbar(NotesActivity.this);



        notesPresenter.attach(this);
        notesPresenter.setupShowsRecyclerView(recyclerView, emptyView);

    }

    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(notesRecyclerAdapter);
        notesPresenter.populateRecyclerView(myNotesArrayList);
    }

    @Override
    public void updateAdapter() {
        notesRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        notesPresenter.attach(this);
        notesRecyclerAdapter.notifyDataSetChanged();
        super.onResume();

    }
    @OnClick(R.id.add_note)
    public void addNoteSection() {

        Intent intentSave = new Intent(NotesActivity.this, NotesDetailsActivity.class);
        intentSave.putExtra("noteDes","");
        startActivity(intentSave);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);    }

}
