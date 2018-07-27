package com.forzo.holdMyCard.ui.recyclerAdapter.creategroup;

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
 * Created by Shriram on 4/3/2018.
 */

public class CreateGroupRecyclerAdapter extends RecyclerView.Adapter<CreateGroupHolder> implements Filterable {
    public CreateGroupRecyclerAdapter() {
    }

    private static final String TAG = "NotificationRecyclerAda";
    CreateGroupListPresenter createGroupListPresenter;

    private ArrayList<MyLibrary> mArrayList;
    private ArrayList<MyLibrary> mFilteredList;

    CreateGroupRecyclerAdapter adapter;

    public void CreateGroupRecyclerAdapter(ArrayList<MyLibrary> arrayList, CreateGroupRecyclerAdapter recyclerAdapter) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
        this.adapter = recyclerAdapter;
    }

    public CreateGroupRecyclerAdapter(CreateGroupListPresenter createGroupListPresenter) {
        this.createGroupListPresenter = createGroupListPresenter;
    }

    @Override
    public CreateGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CreateGroupHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_create_group, parent, false), createGroupListPresenter);
    }

    @Override
    public void onBindViewHolder(CreateGroupHolder holder, int position) {
        createGroupListPresenter.bindEventRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return createGroupListPresenter.getItemCount();
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
                            Log.e("CardName", "" + myLibrary.getCardName().toLowerCase());
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