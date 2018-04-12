package com.forzo.holdMyCard.utils;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;

/**
 * Created by Shriram on 4/4/2018.
 */

public class Utils {
    public static final String CLOUD_VISION_API_KEY = "AIzaSyCFVBIjD8Vk13VzO980yu_OsVL2-F5itpA";
    public static final String CLOUD_NATURAL_API_KEY = "AIzaSyB7jZrVxOUUagQWwqKe37bvBWDmBd1E7Bc";

   // public static String Base = "http://192.168.43.29:8080";
    public static String Base = "http://52.15.123.231:8080";

    public static String BaseUri = Base + "/basic/";
    public static void backButtonOnToolbar(AppCompatActivity mActivity) {
        if (mActivity.getSupportActionBar() != null) {
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public static Image getImageEncodeImage(Bitmap bitmap) {

        Image base64EncodedImage = new Image();
        // Convert the bitmap to a JPEG
        // Just in case it's a format that Android understands but Cloud Vision
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Base64 encode the JPEG
        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }


        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
