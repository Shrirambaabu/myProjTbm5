package com.forzo.holdMyCard.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.echodev.resizer.Resizer;

/**
 * Created by Shriram on 4/4/2018.
 */

public class Utils {
    private static final String TAG = "Utils";
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


    public static String convertResponseToString(BatchAnnotateImagesResponse response) throws IOException {
        Log.e(TAG, "convertResponseToString: " + response.toPrettyString());
        String textResult = "No Data";
        final TextAnnotation text = response.getResponses()
                .get(0).getFullTextAnnotation();
        if (text == null) {
            Log.e("msg", "nodate");
        } else {
            Log.e("msg", "" + text.getText());
            textResult = text.getText();
        }
        return textResult;
    }


    public static String parseNameFromEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    public static String parseCompanyNameFromEmail(String email) {
        return email.substring(email.indexOf("@") + 1, email.lastIndexOf("."));
    }


    public static String parseEmail(String results) {
        String EMAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
        Matcher m = Pattern.compile(EMAIL_REGEX).matcher(results);
        String parsedEmail = "Error";
        while (m.find()) {
            parsedEmail = m.group();
        }
        return parsedEmail;
    }

    public static String parseWebsite(String results) {
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
        String parsedUrl = "Error";
        String[] words = results.split(" ");
        for (String word : words) {
            Matcher m = Pattern.compile(URL_REGEX).matcher(word);
            if (m.find()) {
                parsedUrl = m.group();
            }
        }
        return parsedUrl;
    }

    public static ArrayList<String> parseMobile(String bCardText) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        Iterable<PhoneNumberMatch> numberMatches = phoneNumberUtil.findNumbers(bCardText, Locale.US.getCountry());
        ArrayList<String> data = new ArrayList<>();
        for (PhoneNumberMatch number : numberMatches) {

            String s = number.rawString();

            data.add(s);

        }
        return data;
    }


    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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
        } else {
            hourFinal = "" + hour;
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

    public static Bitmap resize(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = new Resizer(context)
//                    .setTargetLength(1080)
                    .setSourceImage(new File(uri.getPath()))
                    .getResizedBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Base64 encode the JPEG
        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
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

    public static String timeHourSelect(String time) {

        String meridian = time.substring(6, 8);
        String newHour = time.substring(0, 2);

        int finalTimeHour = Integer.parseInt(newHour);


        if (meridian.equals("PM") && !newHour.equals("12")) {

            finalTimeHour = Integer.parseInt(newHour) + 12;
        }

        if (meridian.equals("AM") && newHour.equals("12")) {
            finalTimeHour = 0;
        }

        String timeDone = String.valueOf(finalTimeHour);

        return timeDone;

    }

    public static String timeMinuteSelect(String time) {


        String newMinute = time.substring(3, 5);


        return newMinute;

    }

    public static String dateSelect(String date) {
        String newDate = date.substring(0, 2);
        if (Integer.parseInt(newDate) < 10) {
            newDate = newDate.substring(1, 2);
        }
        return newDate;
    }

    public static String dateMonthSelect(String date) {
        String newDate = date.substring(3, 5);


        int integerDate = Integer.parseInt(newDate);

        integerDate = integerDate - 1;

        newDate = String.valueOf(integerDate);


        return newDate;
    }

    public static String timeToDb(String time) {

        String meridian = "";
        meridian = time.substring(6, 8);

        String hour = "";

        hour = time.substring(0, 2);


        int hourValue = Integer.parseInt(hour);

        String minute = time.substring(3, 5);

        String hourString = "";

        if (meridian.equals("PM") && hourValue != 12) {
            hourValue = hourValue + 12;
        }
        if (meridian.equals("AM") && hourValue == 12) {
            hourValue = 0;
        }
        if (hourValue < 10) {
            hourString = "0" + hourValue;
        } else {
            hourString = "" + hourValue;
        }


        String timeInDbFormat = "" + hourString + ":" + minute + ":" + "00";


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
        }
        if (timeInHour == 12) {
            timeMeridaian = "PM";
        }

        if (timeInHour < 10) {
            finalHour = "0" + timeInHour;
        }


        String minute = time.substring(3, 5);

        timeInDbFormat = "" + finalHour + ":" + minute + " " + timeMeridaian;

        return timeInDbFormat;

    }

    public static String newUtcFormat(Date date) {

        Date d1 = null;
        String finalTime = "";

        SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        try {
            d1 = sdf3.parse(String.valueOf(date));

            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

            finalTime = sdf2.format(d1);

            Log.e("newStr", "" + finalTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalTime;
    }

    public static String newUtcTimeFormat(Date date) {

        Date d1 = null;
        String finalTime = "";

        SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        try {
            d1 = sdf3.parse(String.valueOf(date));

            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");

            finalTime = sdf2.format(d1);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalTime;
    }

    public static Date localToGMT(String dateTime) {


        Date newDate = null;
        Date fromGmt = null;
        Date utcToLocal = null;
        String finalUtcTime = "";
        try {
            newDate = new SimpleDateFormat("dd-MM-yyyy hh:mm aa").parse(dateTime);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("localGMT", "" + e.getMessage());
        }

        if (newDate != null) {
            fromGmt = new Date(newDate.getTime() - TimeZone.getDefault().getOffset(newDate.getTime()));
            utcToLocal = new Date(fromGmt.getTime() + Calendar.getInstance().getTimeZone().getOffset(fromGmt.getTime()));
           /* Log.e("GtoLoc","Local time: " + newDate.toString() +" --> UTC time::" + fromGmt.toString() );
            Log.e("GMT loc","UTC-Loc "+new Date(fromGmt.getTime() + Calendar.getInstance().getTimeZone().getOffset(fromGmt.getTime())));
*/
            finalUtcTime = fromGmt.toString();
            Log.e("newGmtTOLocal", "" + fromGmt);
            Log.e("newLocalToGmt", "" + utcToLocal);
        }


        return fromGmt;
    }


    public static Date gmtToLocal(String date) {


        Log.e("dateL", "" + date);

        Date newDate = null;
        Date fromGmt = null;
        Date utcToLocal = null;
        String finalUtcTime = "";
        try {
            newDate = new SimpleDateFormat("dd-MM-yyyy hh:mm aa").parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("localGMT", "" + e.getMessage());
        }

        if (newDate != null) {
            fromGmt = new Date(newDate.getTime() + TimeZone.getDefault().getOffset(newDate.getTime()));
            utcToLocal = new Date(fromGmt.getTime() + Calendar.getInstance().getTimeZone().getOffset(fromGmt.getTime()));
           /* Log.e("GtoLoc","Local time: " + newDate.toString() +" --> UTC time::" + fromGmt.toString() );
            Log.e("GMT loc","UTC-Loc "+new Date(fromGmt.getTime() + Calendar.getInstance().getTimeZone().getOffset(fromGmt.getTime())));
*/
            finalUtcTime = fromGmt.toString();
            Log.e("newGmtTOLocal", "" + fromGmt);
            Log.e("newLocalToGmt", "" + utcToLocal);
        }


        return fromGmt;
    }


    public static String  gmtToLocalLibrary(String date) {


        Date newDate = null;
        Date fromGmt = null;
        Date utcToLocal = null;
        String finalUtcTime = "";
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("localGMT", "" + e.getMessage());
        }

        if (newDate != null) {
            fromGmt = new Date(newDate.getTime() + TimeZone.getDefault().getOffset(newDate.getTime()));
            utcToLocal = new Date(fromGmt.getTime() + Calendar.getInstance().getTimeZone().getOffset(fromGmt.getTime()));
           /* Log.e("GtoLoc","Local time: " + newDate.toString() +" --> UTC time::" + fromGmt.toString() );
            Log.e("GMT loc","UTC-Loc "+new Date(fromGmt.getTime() + Calendar.getInstance().getTimeZone().getOffset(fromGmt.getTime())));
*/
        }

        SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        Date d1;

        try {
            d1 = sdf3.parse(String.valueOf(fromGmt));

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            finalUtcTime = sdf2.format(d1);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalUtcTime;
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


    public static File savebitmap(String filename, Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + "_1" + ".png");
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


    public static File getBitmapLowFile(Bitmap bitmap) {


        File image = null;

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
        String imageName = cal.getTimeInMillis() + "_" + PreferencesAppHelper.getUserId() + ".png";

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
