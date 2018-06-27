package com.forzo.holdMyCard.ui.fragments.mygroups;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.forzo.holdMyCard.ui.recyclerAdapter.mygroups.MyGroupsListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.mygroups.MyGroupsRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyGroupsFragment extends Fragment implements MyGroupsFragmentContract.View  {


    @Inject
    MyGroupsListPresenter myGroupsListPresenter;
    @Inject
    MyGroupsRecyclerAdapter myGroupsRecyclerAdapter;
    @Inject
    ArrayList<MyGroups> groupsArrayList;
    @Inject
    MyGroupsFragmentPresenter myGroupsFragmentPresenter;

    @BindView(R.id.recycler_view_empty)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    private Context context;
/*

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;*/
    public MyGroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_my_groups, container, false);
        ButterKnife.bind(MyGroupsFragment.this, view);

        myGroupsFragmentPresenter.attach(this);
        myGroupsFragmentPresenter.setupShowsRecyclerView(recyclerView, emptyView);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view_menu_item, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
       // searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        DaggerMyGroupsFragmentComponent.builder()
                .fragmentContext(new FragmentContext(context))
                .build()
                .inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myGroupsFragmentPresenter.detach();
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setAdapter(myGroupsRecyclerAdapter);
        myGroupsFragmentPresenter.populateRecyclerView(groupsArrayList);
    }

    @Override
    public void updateAdapter() {
        myGroupsRecyclerAdapter.notifyDataSetChanged();
    }
/*
    @OnClick(R.id.fab)
    public void fabButtonGroups() {

        Toast.makeText(getActivity(),"Clicked Fab",Toast.LENGTH_LONG).show();

    }*/

}
