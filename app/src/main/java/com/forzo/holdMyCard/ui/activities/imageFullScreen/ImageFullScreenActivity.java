package com.forzo.holdMyCard.ui.activities.imageFullScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.forzo.holdMyCard.R;

import java.util.NoSuchElementException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFullScreenActivity extends AppCompatActivity {

    private static final String TAG = "ImageFullScreenActivity";
    @BindView(R.id.imageView)
    ImageView imageView;
    private String imageUri;
    private Context mContext = ImageFullScreenActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null)
            imageUri = getIntent().getExtras().getString("image");

        if (imageUri != null)
            Glide.with(mContext)
                    .load(imageUri)
                    .into(imageView);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
