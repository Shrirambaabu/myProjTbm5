package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibraryListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibraryRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCurrentLibraryFragment extends Fragment implements MyCurrentLibraryFragmentContract.View {


    @Inject
    MyCurrentLibraryFragmentPresenter myCurrentLibraryFragmentPresenter;
    @Inject
    MyLibraryRecyclerAdapter myLibraryRecyclerAdapter;
    @Inject
    ArrayList<MyLibrary> myLibraryArrayList;
    @Inject
    MyLibraryListPresenter myLibraryListPresenter;

    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private Context context;

    public MyCurrentLibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_library, container, false);
        ButterKnife.bind(MyCurrentLibraryFragment.this, view);

        myCurrentLibraryFragmentPresenter.attach(this);
        myCurrentLibraryFragmentPresenter.setupShowsRecyclerView(recyclerView, emptyView);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;


        DaggerMyCurrentLibraryFragmentComponent.builder()
                .fragmentContext(new FragmentContext(context))
                .build()
                .inject(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        myCurrentLibraryFragmentPresenter.detach();
    }


    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(myLibraryRecyclerAdapter);
        myCurrentLibraryFragmentPresenter.populateRecyclerView(myLibraryArrayList);
    }

    @Override
    public void updateAdapter() {
        myLibraryRecyclerAdapter.notifyDataSetChanged();
    }
}
