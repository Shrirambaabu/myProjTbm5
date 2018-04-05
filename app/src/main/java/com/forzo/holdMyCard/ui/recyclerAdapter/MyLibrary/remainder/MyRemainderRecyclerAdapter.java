package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.remainder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forzo.holdMyCard.R;

/**
 * Created by Shriram on 4/5/2018.
 */

public class MyRemainderRecyclerAdapter extends RecyclerView.Adapter<MyRemainderHolder> {

    private static final String TAG = "NotificationRecyclerAda";
    MyRemainderListPresenter myRemainderListPresenter;

    public MyRemainderRecyclerAdapter(MyRemainderListPresenter myRemainderListPresenter) {
        this.myRemainderListPresenter = myRemainderListPresenter;
    }

    @Override
    public MyRemainderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyRemainderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_remainder, parent, false), myRemainderListPresenter);
    }

    @Override
    public void onBindViewHolder(MyRemainderHolder holder, int position) {
        myRemainderListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return myRemainderListPresenter.getItemCount();
    }

}