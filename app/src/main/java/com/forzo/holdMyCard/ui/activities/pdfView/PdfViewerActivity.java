package com.forzo.holdMyCard.ui.activities.pdfView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.forzo.holdMyCard.BuildConfig;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.forzo.holdMyCard.ui.activities.userprofile.UserProfileActivity;
import com.forzo.holdMyCard.utils.PreferencesAppHelper;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class PdfViewerActivity extends AppCompatActivity {
    private static final String TAG = "PdfViewerActivity";
    public int pageNumber = 0;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    private File sharingFile;
    private boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        ButterKnife.bind(this);
        backButtonOnToolbar(this);
        String filePath = getIntent().getStringExtra("file_path");
        sharingFile = new File(filePath);
        pdfView.fromUri(Uri.fromFile(sharingFile))
                .defaultPage(pageNumber)
                .enableAnnotationRendering(true)
                .spacing(10)
                .onError(t -> Log.e(TAG, "onCreate: " + t))
                .load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                if (sharingFile != null) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    Uri outputImgUri = FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID + ".provider", sharingFile);
                    intent.setDataAndType(outputImgUri, "application/pdf");
                    startActivity(intent);
                }
                return true;
            case R.id.download:
                delete = false;
                Toast.makeText(getApplicationContext(), "File Downloaded to" + sharingFile, Toast.LENGTH_LONG).show();
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userProfile", PreferencesAppHelper.getUserId());
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userProfile", PreferencesAppHelper.getUserId());
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (delete) {
            if (sharingFile.exists()) {
                sharingFile.delete();
            }
        }
    }
}
