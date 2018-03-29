package com.forzo.holdMyCard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Bitmap bmp=getIntent().getParcelableExtra("image");

        TextView cancelText=findViewById(R.id.cancel_text);
        TextView saveText=findViewById(R.id.save_text);
        ImageView imageView=findViewById(R.id.image_view);

        imageView.setImageBitmap(bmp);


        cancelText.setOnClickListener(this);
        saveText.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cancel_text:

                Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(CardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                break;

            case R.id.save_text:


                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();

                Intent intentSave = new Intent(CardActivity.this, MainActivity.class);
                intentSave.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSave);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                break;
        }

    }
}
