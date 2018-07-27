package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forzo.holdMyCard.HmcApplication;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.api.ApiFactory;
import com.forzo.holdMyCard.api.ApiService;
import com.forzo.holdMyCard.base.BasePresenter;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibraryRecyclerAdapter;
import com.forzo.holdMyCard.ui.services.DataService;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Shriram on 3/31/2018.
 */

public class MyCurrentLibraryFragmentPresenter extends BasePresenter<MyCurrentLibraryFragmentContract.View>
        implements MyCurrentLibraryFragmentContract.Presenter {

    private static final String TAG = "MyCurrentLibraryFragmen";
    private Context context;
    private ApiService mApiService;
    private ArrayList<MyLibrary> myLibraryArrayList;
    MyLibraryRecyclerAdapter myLibraryRecyclerAdapter;
    private static int dateValue = 0;
    private static int alphabetValue = 0;

    MyCurrentLibraryFragmentPresenter(Context context) {
        this.context = context;
        mApiService = ApiFactory.create(HmcApplication.get((Activity) context).getRetrofit());
    }

    @Override
    public void setupShowsRecyclerView(EmptyRecyclerView emptyRecyclerView, RelativeLayout emptyView) {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        emptyRecyclerView.setLayoutManager(mLayoutManager);
        emptyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptyRecyclerView.setHasFixedSize(true);

        emptyRecyclerView.setEmptyView(emptyView);
        getView().showRecyclerView();

    }

    @Override
    public void sortClicked(ArrayList<MyLibrary> myLibraryArrayList, MyLibraryRecyclerAdapter myLibraryRecyclerAdapter) {
        this.myLibraryArrayList = myLibraryArrayList;
        this.myLibraryRecyclerAdapter = myLibraryRecyclerAdapter;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sort_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // set the custom dialog components - text, image and button
        TextView acceptSort = (TextView) dialog.findViewById(R.id.accept_sort);
        TextView ascOrderDate = (TextView) dialog.findViewById(R.id.asc_order);
        TextView dscOrderDate = (TextView) dialog.findViewById(R.id.des_order);
        TextView ascAlphabet = (TextView) dialog.findViewById(R.id.atoz);
        TextView dscAlphabet = (TextView) dialog.findViewById(R.id.ztoa);
        TextView cancelSort = (TextView) dialog.findViewById(R.id.cancel_sort);


        if (alphabetValue == 0) {
            ascAlphabet.setBackgroundResource(R.drawable.sort_selected);
            ascAlphabet.setTextColor(Color.WHITE);
            dscAlphabet.setBackgroundResource(R.drawable.sort_type);
            dscAlphabet.setTextColor(Color.BLACK);
            alphabetValue = 0;
        } else if (alphabetValue == 1) {
            dscAlphabet.setBackgroundResource(R.drawable.sort_selected);
            dscAlphabet.setTextColor(Color.WHITE);
            ascAlphabet.setBackgroundResource(R.drawable.sort_type);
            ascAlphabet.setTextColor(Color.BLACK);
            alphabetValue = 1;
        }
        if (dateValue == 0) {
            ascOrderDate.setBackgroundResource(R.drawable.sort_selected);
            ascOrderDate.setTextColor(Color.WHITE);
            dscOrderDate.setBackgroundResource(R.drawable.sort_type);
            dscOrderDate.setTextColor(Color.BLACK);
            dateValue = 0;
        } else if (dateValue == 1) {
            dscOrderDate.setBackgroundResource(R.drawable.sort_selected);
            dscOrderDate.setTextColor(Color.WHITE);
            ascOrderDate.setBackgroundResource(R.drawable.sort_type);
            ascOrderDate.setTextColor(Color.BLACK);
            dateValue = 1;
        }


        ascOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ascOrderDate.setBackgroundResource(R.drawable.sort_selected);
                ascOrderDate.setTextColor(Color.WHITE);
                dscOrderDate.setBackgroundResource(R.drawable.sort_type);
                dscOrderDate.setTextColor(Color.BLACK);
                dateValue = 0;
            }
        });

        dscOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dscOrderDate.setBackgroundResource(R.drawable.sort_selected);
                dscOrderDate.setTextColor(Color.WHITE);
                ascOrderDate.setBackgroundResource(R.drawable.sort_type);
                ascOrderDate.setTextColor(Color.BLACK);
                dateValue = 1;
            }
        });

        ascAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ascAlphabet.setBackgroundResource(R.drawable.sort_selected);
                ascAlphabet.setTextColor(Color.WHITE);
                dscAlphabet.setBackgroundResource(R.drawable.sort_type);
                dscAlphabet.setTextColor(Color.BLACK);
                alphabetValue = 0;
            }
        });
        dscAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dscAlphabet.setBackgroundResource(R.drawable.sort_selected);
                dscAlphabet.setTextColor(Color.WHITE);
                ascAlphabet.setBackgroundResource(R.drawable.sort_type);
                ascAlphabet.setTextColor(Color.BLACK);
                alphabetValue = 1;
            }
        });

        acceptSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("GetAlphabetValue:", "" + alphabetValue);
                Log.e("GetDateValue:", "" + dateValue);
                if (alphabetValue == 0) {
                    Collections.sort(myLibraryArrayList, new Comparator<MyLibrary>() {
                        @Override
                        public int compare(MyLibrary o1, MyLibrary o2) {
                            return o1.getCardName().compareTo(o2.getCardName());
                        }
                    });
                    myLibraryRecyclerAdapter.notifyDataSetChanged();
                } else if (alphabetValue == 1) {
                    Collections.sort(myLibraryArrayList, new Comparator<MyLibrary>() {
                        @Override
                        public int compare(MyLibrary o1, MyLibrary o2) {
                            return o2.getCardName().compareTo(o1.getCardName());
                        }
                    });
                    myLibraryRecyclerAdapter.notifyDataSetChanged();
                }
                if (dateValue == 0) {
                    Collections.sort(myLibraryArrayList, new Comparator<MyLibrary>() {

                        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                        @Override
                        public int compare(MyLibrary o1, MyLibrary o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });
                    myLibraryRecyclerAdapter.notifyDataSetChanged();
                }else if (dateValue==1){
                  DateFormat f = new SimpleDateFormat("dd-MM-yyyy");

                    Collections.sort(myLibraryArrayList, new Comparator<MyLibrary>() {
                        @Override
                        public int compare(MyLibrary o1, MyLibrary o2) {
                            return o2.getDate().compareTo(o1.getDate());
                        }
                    });
                    myLibraryRecyclerAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
            }
        });

        cancelSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("GetAlphabetValue:", "" + alphabetValue);
                Log.e("GetDateValue:", "" + dateValue);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void populateRecyclerView(List<MyLibrary> myLibraryList) {
        if (PreferencesAppHelper.getUserId() != null) {
            mApiService.getUserLibrary(PreferencesAppHelper.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MyLibrary>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(List<MyLibrary> myLibraryList1) {

                            for (int i = 0; i < myLibraryList1.size(); i++) {
                                MyLibrary myLibrary = new MyLibrary();

                                myLibrary.setCardName(myLibraryList1.get(i).getCardName());
                                myLibrary.setCardDescription(myLibraryList1.get(i).getCardDescription());
                                myLibrary.setCardDetails(myLibraryList1.get(i).getCardDetails());
                                myLibrary.setImage(myLibraryList1.get(i).getImage());
                                myLibrary.setImageType(myLibraryList1.get(i).getImageType());
                                myLibrary.setUserId(myLibraryList1.get(i).getUserId());
                                myLibrary.setDate(myLibraryList1.get(i).getDate());

                                Log.e("LibDate", "" + myLibraryList1.get(i).getDate());
                                myLibraryList.add(myLibrary);

                                getView().updateAdapter();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }
}
