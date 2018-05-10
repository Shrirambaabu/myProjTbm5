package com.forzo.holdMyCard.ui.activities.notes;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.recyclerAdapter.mynotes.MyNotesListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.mynotes.MyNotesRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 4/4/2018.
 */

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class NotesModule {


    @Provides
    @PerActivityScope
    MyNotesPresenter myNotesPresenter(Context context) {
        return new MyNotesPresenter(context);
    }


    @Provides
    @PerActivityScope
    ArrayList<MyNotes> myNotes() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivityScope
    MyNotesListPresenter myNotesListPresenter(Context context, ArrayList<MyNotes> currentArrayList) {
        return new MyNotesListPresenter(context, currentArrayList);
    }

    @Provides
    @PerActivityScope
    MyNotesRecyclerAdapter notesRecyclerAdapter(MyNotesListPresenter currentFragmentListPresenter) {
        return new MyNotesRecyclerAdapter(currentFragmentListPresenter);
    }
}
