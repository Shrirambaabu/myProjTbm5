package com.forzo.holdMyCard.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public static Bitmap resizeBitmap(Bitmap bitmap) {

        int maxDimension = 1024;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    public static ArrayList<String> convertToHourMinuteTimeSet(int hourOfDay, int minute) {

        ArrayList<String> integerArrayList = new ArrayList<>();


        int hour = hourOfDay;
        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }
        String hourFinal = "";
        if (hour < 10) {
            hourFinal = "0" + hour;
        }else {
            hourFinal=""+hour;
        }

        String min = "";
        if (minute < 10)
            min = "0" + minute;
        else
            min = String.valueOf(minute);

        integerArrayList.add(hourFinal);
        integerArrayList.add(min);
        integerArrayList.add(timeSet);

        return integerArrayList;

    }


    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DATE, dayOfMonth);
        Date date = calendar.getTime();
        //Date date = new Date(newYear, monthOfYear, dayOfMonth);
        return parseFormat.format(date);
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

    public static Bitmap getImageCompressImage(Bitmap bitmap) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        return decoded;
    }

    public static String dateToDb(String date) {
        String ActDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");//set format of date you receiving from db
        try {
            Date dateFotmat = (Date) sdf.parse(date);
            SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd");
            ActDate = newDate.format(dateFotmat);// here is your new date !

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("err", "" + e.getMessage());
        }

        return ActDate;

    }

    public static String dateToUI(String date) {
        String ActDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//set format of date you receiving from db
        try {
            Date dateFotmat = (Date) sdf.parse(date);
            SimpleDateFormat newDate = new SimpleDateFormat("dd-MM-yyyy");
            ActDate = newDate.format(dateFotmat);// here is your new date !

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("err", "" + e.getMessage());
        }

        return ActDate;

    }
    public static String dateReminderToUI(String date) {
        String ActDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//set format of date you receiving from db
        try {
            Date dateFotmat = (Date) sdf.parse(date);
            SimpleDateFormat newDate = new SimpleDateFormat("dd MMMM, yyyy ");
            ActDate = newDate.format(dateFotmat);// here is your new date !

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("err", "" + e.getMessage());
        }

        return ActDate;

    }

    public static String timeToDb(String time) {


        String timeMeridian = "";

        String hour = "";

        hour = time.substring(0, 2);

        int hourValue = Integer.parseInt(hour);
        String minute = time.substring(3, 5);


        timeMeridian = time.substring(6, 8);

        if (timeMeridian.equals("PM")) {

            hourValue = Integer.parseInt(hour) + 12;

        }

        String timeInDbFormat = "" + hourValue + ":" + minute + ":" + "00";


        return timeInDbFormat;

    }

    public static String timeToUi(String time) {


        String timeInDbFormat = "";

        String timeMeridaian = "AM";
        String timeHour = time.substring(0, 2);

        int timeInHour = Integer.parseInt(timeHour);
        String finalHour = "" + timeInHour;

        if (timeInHour > 12) {

            timeInHour = timeInHour - 12;
            timeMeridaian = "PM";

            if (timeInHour < 10) {
                finalHour = "0" + timeInHour;
            }
        }

        String minute = time.substring(3, 5);

        timeInDbFormat = "" + finalHour + ":" + minute + " " + timeMeridaian;

        return timeInDbFormat;

    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }


        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static File savebitmap(String filename,Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename+"_1"+ ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            // make a new bitmap from your file
             bitmap = BitmapFactory.decodeFile(file.getName());

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }


    public static Bitmap getResizedBitmapFile(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
    }


    public static File getBitmapLowFile(Bitmap bitmap){


        File image=null;

        // EasyImage.openChooserWithGallery(HomeActivity.this, OPEN_CAMERA_OR_GALLERY_TO_CHOOSE_AN_IMAGE, TYPE);
        Calendar cal = Calendar.getInstance();


        // fetching the root directory
        String root = Environment.getExternalStorageDirectory().toString()
                + "/HMC";

        // Creating folders for Image
        String imageFolderPath = root + "/saved_images";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file name
        String imageName = cal.getTimeInMillis()+"_" + PreferencesAppHelper.getUserId() + ".png";

        // Creating image here
        image = new File(imageFolderPath, imageName);


        OutputStream outStream = null;

        try {
            outStream = new FileOutputStream(image);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return image;
    }

}
