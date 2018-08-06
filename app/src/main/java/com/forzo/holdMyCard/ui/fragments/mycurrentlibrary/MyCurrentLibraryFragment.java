package com.forzo.holdMyCard.ui.fragments.mycurrentlibrary;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.newcard.NewCardActivity;
import com.forzo.holdMyCard.ui.activities.pdfView.PdfViewerActivity;
import com.forzo.holdMyCard.ui.activities.sortDialog.SortDialogActivity;
import com.forzo.holdMyCard.ui.activities.userprofile.UserProfileActivity;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibraryListPresenter;
import com.forzo.holdMyCard.ui.recyclerAdapter.MyLibrary.MyLibraryRecyclerAdapter;
import com.forzo.holdMyCard.utils.EmptyRecyclerView;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.snatik.storage.Storage;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyCurrentLibraryFragment extends Fragment implements MyCurrentLibraryFragmentContract.View, SearchView.OnQueryTextListener {


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
    @BindView(R.id.excel_export)
    TextView excelExport;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.fab)
    com.github.clans.fab.FloatingActionButton floatingActionButton;
    SearchView searchView;
    private Storage storage;
    private String newDir;
    private int WRITE_EXTERNAL_STORAGE = 111;
    private Context context;

    public MyCurrentLibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

 /*   @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }
*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view_menu_item, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_library, container, false);
        ButterKnife.bind(MyCurrentLibraryFragment.this, view);

        myCurrentLibraryFragmentPresenter.attach(this);

        //init
        storage = new Storage(getActivity());
        // get external storage
        String path = storage.getExternalStorageDirectory();

        // new dir
        newDir = path + File.separator + "HMC Excel";
        storage.createDirectory(newDir);

        boolean hasPermission = (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

        if (!hasPermission) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS,
                            Manifest.permission.INTERNET
                    }, WRITE_EXTERNAL_STORAGE);
        }
        myCurrentLibraryFragmentPresenter.setupShowsRecyclerView(recyclerView, emptyView);

        return view;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        // myLibraryRecyclerAdapter.getFilter().filter(newText);
        Log.e("MyLibraryFragmentChange", "" + newText);


        final ArrayList<MyLibrary> filtermodelist = filter(myLibraryArrayList, newText);
        myLibraryListPresenter.setfilter(filtermodelist, myLibraryRecyclerAdapter);

        return true;
    }

    private ArrayList<MyLibrary> filter(ArrayList<MyLibrary> myLibraryArrayList, String query) {

        query = query.toLowerCase();
        final ArrayList<MyLibrary> filteredModeList = new ArrayList<>();
        for (MyLibrary model : myLibraryArrayList) {
            final String text = model.getCardName().toLowerCase();
            final String email = model.getCardDescription().toLowerCase();
            final String phone = model.getCardDetails().toLowerCase();
            if (text.contains(query) || email.contains(query) || phone.contains(query)) {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    @OnClick(R.id.fab)
    public void fabButton() {
        Intent newCard = new Intent(context, NewCardActivity.class);
        newCard.putExtra("ActivityAction", "NewCard");
        startActivity(newCard);
    }

    @OnClick(R.id.sort_text)
    public void fabSortButton() {
        if (myLibraryArrayList.size() > 0) {
            myCurrentLibraryFragmentPresenter.sortClicked(myLibraryArrayList, myLibraryRecyclerAdapter);
        } else {
            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.excel_export)
    public void excelExport() {

        if (myLibraryArrayList.size() > 0) {

            Map<String, Object[]> data = new LinkedHashMap<>();
            data.put("1", new Object[]{"Name", "Email", "Phone"});

            for (int i = 0; i <= myLibraryArrayList.size() - 1; i++) {
                Log.e("ArrValueID", String.valueOf(i + 2));
                Log.e("ArrValue", "" + myLibraryArrayList.get(i).getCardName());
                Log.e("ArrValueUserId", "" + myLibraryArrayList.get(i).getUserId());
                Log.e("ArrValueEmail", "" + myLibraryArrayList.get(i).getCardDescription());
                Log.e("ArrValuePhone", "" + myLibraryArrayList.get(i).getCardDetails());

                data.put(String.valueOf(i + 2), new Object[]{myLibraryArrayList.get(i).getCardName(),
                        myLibraryArrayList.get(i).getCardDescription(), myLibraryArrayList.get(i).getCardDetails()});
            }
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sample sheet");

            Set<String> keyset = data.keySet();
            int rowNum = 0;
            for (String key : keyset) {
                Row row = sheet.createRow(rowNum++);
                Object[] objArr = data.get(key);
                Log.e("Excel", "onViewClicked: " + key);
                Log.e("Excel", "onViewClicked: " + Arrays.toString(objArr));
                int cellNum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellValue(String.valueOf(obj));
                    if (obj instanceof Date)
                        cell.setCellValue((Date) obj);
                    else if (obj instanceof Boolean)
                        cell.setCellValue((Boolean) obj);
                    else if (obj instanceof String)
                        cell.setCellValue((String) obj);
                    else if (obj instanceof Double)
                        cell.setCellValue((Double) obj);
                }
            }

            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
                String xlsPath = newDir + File.separator + "new_" + timeStamp + "_"
                        + PreferencesAppHelper.getUserId() + ".xls";
                File xlFile = new File(xlsPath);
                FileOutputStream out = new FileOutputStream(xlFile);
                workbook.write(out);
                out.close();
                Log.e("Excel", "onViewClicked: " + "Excel written successfully..");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(xlFile), "application/vnd.ms-excel");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.e("er", "" + e.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), "Format not supported to open the file", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Excel", "onViewClicked: " + e.getMessage());
                Toast.makeText(getActivity(), "Excel couldn't be saved to " + newDir + File.separator + "new.xls", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "No Data found", Toast.LENGTH_LONG).show();
        }
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
        myLibraryRecyclerAdapter.MyLibraryRecyclerAdapter(myLibraryArrayList, myLibraryRecyclerAdapter);
        recyclerView.setAdapter(myLibraryRecyclerAdapter);
        myCurrentLibraryFragmentPresenter.populateRecyclerView(myLibraryArrayList);
    }

    @Override
    public void updateAdapter() {

        int nameSort = 1;

        if (nameSort == 0) {
            Collections.reverse(myLibraryArrayList);
        }
        myLibraryRecyclerAdapter.notifyDataSetChanged();
    }

}
