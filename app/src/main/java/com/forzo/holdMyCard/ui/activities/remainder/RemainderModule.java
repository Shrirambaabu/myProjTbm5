package com.forzo.holdMyCard.ui.activities.remainder;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.remainder.MyRemainderListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.remainder.MyRemainderRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 4/5/2018.
 */

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class RemainderModule {

    @Provides
    @PerActivityScope
    RemainderPresenter remainderPresenter(Context context) {
        return new RemainderPresenter(context);
    }


    @Provides
    @PerActivityScope
    ArrayList<MyRemainder> myRemainders() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivityScope
    MyRemainderListPresenter myNotesListPresenter(Context context, ArrayList<MyRemainder> currentArrayList) {
        return new MyRemainderListPresenter(context, currentArrayList);
    }

    @Provides
    @PerActivityScope
    MyRemainderRecyclerAdapter notesRecyclerAdapter(MyRemainderListPresenter myRemainderListPresenter) {
        return new MyRemainderRecyclerAdapter(myRemainderListPresenter);
    }

}
