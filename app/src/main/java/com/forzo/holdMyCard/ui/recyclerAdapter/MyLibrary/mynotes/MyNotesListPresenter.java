package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.mynotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.forzo.holdMyCard.ui.activities.notes.NotesActivity;
import com.forzo.holdMyCard.ui.activities.notesdetail.NotesDetailsActivity;
import com.forzo.holdMyCard.ui.models.MyNotes;

import java.util.ArrayList;

/**
 * Created by Shriram on 4/4/2018.
 */

public class MyNotesListPresenter implements MyNotesContract.Presenter {

    private Context context;
    private ArrayList<MyNotes> myNotes;

    public MyNotesListPresenter(Context context, ArrayList<MyNotes> myNotes) {
        this.context = context;
        this.myNotes = myNotes;
    }

    @Override
    public int getItemCount() {
        return (null != myNotes ? myNotes.size() : 0);
    }

    @Override
    public void onItemClick(int adapterPosition) {

        Log.e("val",""+myNotes.get(adapterPosition).getId());
        Activity activity = (Activity) context;
        Intent noteDetailsIntent = new Intent(context, NotesDetailsActivity.class);
        noteDetailsIntent.putExtra("noteId",myNotes.get(adapterPosition).getId());
        noteDetailsIntent.putExtra("noteDes",myNotes.get(adapterPosition).getNotes());
        noteDetailsIntent.putExtra("libraryProfile", "" + myNotes.get(adapterPosition).getLibraryProfileId());
        noteDetailsIntent.putExtra("libraryProfileImage", "" + myNotes.get(adapterPosition).getLibraryProfileImage());
        context.startActivity(noteDetailsIntent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void bindEventRow(int position, MyNotesContract.MyNotesRowView rowView) {

        MyNotes notes=myNotes.get(position);

        rowView.setCardName(notes.getNotes());
    }
}
