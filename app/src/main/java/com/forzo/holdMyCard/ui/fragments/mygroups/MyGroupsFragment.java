package com.forzo.holdMyCard.ui.fragments.mygroups;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyGroupsFragment extends Fragment {
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

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
/*
    @OnClick(R.id.fab)
    public void fabButtonGroups() {

        Toast.makeText(getActivity(),"Clicked Fab",Toast.LENGTH_LONG).show();

    }*/

}
