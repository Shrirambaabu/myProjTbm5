package com.forzo.holdMyCard.ui.activities.personalizedqr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.ActivityContext;
import com.forzo.holdMyCard.ui.activities.Profile.ProfileActivity;
import com.forzo.holdMyCard.ui.activities.mylibrary.MyLibraryActivity;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.forzo.holdMyCard.utils.Utils.backButtonOnToolbar;

public class QRActivity extends AppCompatActivity implements QRContract.View {


    @Inject
    QRPresenter qrPresenter;
    private Context mContext = QRActivity.this;


    @BindView(R.id.qrCode)
    ImageView qrCodeImageView;

    private String TAG = "QRActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ButterKnife.bind(this);
        backButtonOnToolbar(QRActivity.this);
        DaggerQRComponent.builder()
                .activityContext(new ActivityContext(mContext))
                .build()
                .inject(this);
        qrPresenter.attach(this);
        qrPresenter.getPersonalDetails();
    }

    @Override
    public void userDetails(String userName, String jobName, String companyName, String phoneNumberProfile, String phoneNumberProfile2, String phoneNumberProfile3, String emailIdProfile, String websiteProfile, String addressProfile) {
        Log.e(TAG, userName);
        Log.e(TAG, jobName);
        Log.e(TAG, companyName);
        Log.e(TAG, phoneNumberProfile);
        Log.e(TAG, phoneNumberProfile2);
        Log.e(TAG, phoneNumberProfile3);
        Log.e(TAG, emailIdProfile);
        Log.e(TAG, websiteProfile);
        Log.e(TAG, addressProfile);

        String json = "{'name':'" +
                userName +
                "','jobName':'" +
                jobName +
                "','companyName':'" +
                companyName +
                "','phoneNumberProfile':'" +
                phoneNumberProfile +
                "','phoneNumberProfile2':'" +
                phoneNumberProfile2 +
                "','phoneNumberProfile3':'" +
                phoneNumberProfile3 +
                "','websiteProfile':'" +
                websiteProfile +
                "','addressProfile':'" +
                addressProfile +
                "','email':'" +
                emailIdProfile +
                "'}";
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(json, BarcodeFormat.QR_CODE, 400, 400);
            qrCodeImageView.setImageBitmap(bitmap);
            Log.e(TAG, "onViewClicked: " + json);
            JSONObject jsonObj = new JSONObject(json);
            Log.e(TAG, "onViewClicked: " + jsonObj.getString("name"));
        } catch (Exception e) {
            Log.e(TAG, "onViewClicked: " + e.getMessage());
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(QRActivity.this, MyLibraryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

}
