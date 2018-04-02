package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import android.content.Context;

import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.base.PerFragmentScope;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibraryListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibraryRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shriram on 3/31/2018.
 */

@Module(includes = {FragmentContext.class})
@PerFragmentScope
public class MyCurrentLibraryFragmentModule {



    @Provides
    @PerFragmentScope
    MyCurrentLibraryFragmentPresenter myLibraryFragmentPresenter(Context context) {
        return new MyCurrentLibraryFragmentPresenter(context);
    }

    @Provides
    @PerFragmentScope
    ArrayList<MyLibrary> myLibraries() {
        return new ArrayList<>();
    }

    @Provides
    @PerFragmentScope
    MyLibraryListPresenter currentArrayList(Context context, ArrayList<MyLibrary> currentArrayList) {
        return new MyLibraryListPresenter(context, currentArrayList);
    }

    @Provides
    @PerFragmentScope
    MyLibraryRecyclerAdapter providesPaymentCurrentRecyclerAdapter(MyLibraryListPresenter currentFragmentListPresenter) {
        return new MyLibraryRecyclerAdapter(currentFragmentListPresenter);
    }

}
