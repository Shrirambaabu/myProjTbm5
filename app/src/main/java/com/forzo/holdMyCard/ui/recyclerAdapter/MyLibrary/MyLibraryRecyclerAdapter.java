package com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.models.MyLibrary;

import java.util.ArrayList;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyLibraryRecyclerAdapter extends RecyclerView.Adapter<MyLibraryHolder> implements Filterable {

    private static final String TAG = "NotificationRecyclerAda";
    MyLibraryListPresenter currentFragmentListPresenter;

    public MyLibraryRecyclerAdapter(MyLibraryListPresenter currentFragmentListPresenter) {
        this.currentFragmentListPresenter = currentFragmentListPresenter;
    }

    private ArrayList<MyLibrary> mArrayList;
    private ArrayList<MyLibrary> mFilteredList;

    MyLibraryRecyclerAdapter adapter;

    public void MyLibraryRecyclerAdapter(ArrayList<MyLibrary> arrayList, MyLibraryRecyclerAdapter recyclerAdapter) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
        this.adapter = recyclerAdapter;
    }

    @Override
    public MyLibraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyLibraryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_my_library, parent, false), currentFragmentListPresenter);
    }

    @Override
    public void onBindViewHolder(MyLibraryHolder holder, int position) {
        currentFragmentListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return currentFragmentListPresenter.getItemCount();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<MyLibrary> filteredList = new ArrayList<>();

                    for (MyLibrary myLibrary : mArrayList) {

                        if (myLibrary.getCardName().toLowerCase().contains(charString)) {

                            filteredList.add(myLibrary);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                Log.e("FilResults", "" + filterResults.values);
                adapter.mFilteredList = (ArrayList<MyLibrary>) filterResults.values;
                adapter.notifyDataSetChanged();
            }
        };
    }
}