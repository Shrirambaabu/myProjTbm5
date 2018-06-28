package com.forzo.holdMyCard.ui.activities.addparticipant;

import android.content.Context;

import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.base.PerActivityScope;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant.AddParticipantAdapterListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.addparticipant.AddParticipantRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ActivityContext.class})
@PerActivityScope
public class AddParticipantModule {

    @Provides
    @PerActivityScope
    AddParticipantPresenter createGroupPresenter(Context context) {
        return new AddParticipantPresenter(context);
    }


    @Provides
    @PerActivityScope
    ArrayList<MyLibrary> myLibraries() {
        return new ArrayList<>();
    }

    @Provides
    @PerActivityScope
    AddParticipantAdapterListPresenter addParticipantAdapterListPresenter(Context context, ArrayList<MyLibrary> currentArrayList) {
        return new AddParticipantAdapterListPresenter(context, currentArrayList);
    }

    @Provides
    @PerActivityScope
    AddParticipantRecyclerAdapter providesPaymentCurrentRecyclerAdapter(AddParticipantAdapterListPresenter currentFragmentListPresenter) {
        return new AddParticipantRecyclerAdapter(currentFragmentListPresenter);
    }

}
