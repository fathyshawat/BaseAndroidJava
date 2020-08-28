package com.example.javabase.MyBase.Uitls;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.javabase.MyBase.App.AppController;
import com.example.javabase.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

public class CommonUtil {

    public static boolean isALog = true;

    public static void onPrintLog(Object o) {
        if (isALog) {
            Log.e("Response >>>>", new Gson().toJson(o));
        }
    }


    public static void PrintLogE(String print) {
//        if (BuildConfig.DEBUG) {
//            Log.e(AppController.TAG, print);
//        }
        Log.e(AppController.TAG, print);
    }

    public static String makeURL(double sourceLat, double sourceLog, double destLat, double destLog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourceLat));
        urlString.append(",");
        urlString.append(Double.toString(sourceLog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destLat));
        urlString.append(",");
        urlString.append(Double.toString(destLog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        return urlString.toString();
    }

    public static String getLanguage() {
        String language = Locale.getDefault().getDisplayLanguage();
        return language;
    }


    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return networkInfo != null && networkInfo.isConnected();
    }

    public static void requestFocus(View view, Window window) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // check he SDK version of the software currently running on this hardware device.
    public static boolean canMakeSmores() {
        return (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP_MR1);
    }

    public static String getCurrentAddress(Context context, double addressLat, double addressLng) {
        String myAddress = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(addressLat, addressLng, 1);
            if (addresses.size() > 0) {
                myAddress = addresses.get(0).getLocality() + " " + addresses.get(0).getAdminArea();
//                if (myAddress == null) {
//                    myAddress = addresses.get(0).getSubAdminArea();
//                }
            } else {
                makeToast(context, context.getString(R.string.connection_error));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myAddress;
    }

    public static boolean canGetLocation(Context context) {
        return isLocationEnabled(context); // application context
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static void ShareApp(Context context) {
        Intent sendIntent = new Intent();
        String appPackageName = context.getPackageName();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check Takafol app at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }

    public static void RateApp(AppCompatActivity context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static int handleException(Context context, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            makeToast(context, R.string.time_out_error);
            return R.string.time_out_error;
        } else if (t instanceof UnknownHostException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof ConnectException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof NoRouteToHostException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof PortUnreachableException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof UnknownServiceException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else if (t instanceof BindException) {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        } else {
            makeToast(context, R.string.connection_error);
            return R.string.connection_error;
        }
    }

    public static void makeToast(Context context, int msgId) {
        Toaster toaster = new Toaster(context);
        toaster.makeToast(context.getString(msgId));

    }

    public static void makeToast(Context context, String msg) {
        Toaster toaster = new Toaster(context);
        toaster.makeToast(msg);

    }


    public static void setConfig(String language, Context context) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

    }

    public static void chooseDate(Context context, final TextView textView) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dlg;
        dlg = new DatePickerDialog(context, R.style.Calendar, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, month, dayOfMonth, 0, 0, 0);
                Date chosenDate = cal.getTime();
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                textView.setText(sdf.format(chosenDate));
            }
        }, year, month, day);
//        dlg.setTitle(getString(R.string.dtect_date));
        dlg.show();

    }

    public static void showHourPicker(Context context, final TextView textView) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog tlg = new TimePickerDialog(context, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar cal = Calendar.getInstance();
                cal.set(0, 0, 0, hourOfDay, minute);
                Date choseTime = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                Log.e("<<<time", sdf.format(choseTime));
                textView.setText(sdf.format(choseTime));
            }
        }, hour, minute, true);
        tlg.show();
    }


    public static String getFormattedTime(String date) {
        Date parse = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            parse = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        long updated = calendar.getTimeInMillis();
        return DateUtils.getRelativeTimeSpanString(updated, System.currentTimeMillis(), MINUTE_IN_MILLIS).toString();
    }


    public static void openWhatsappContact(AppCompatActivity context, String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent mWhatsAppIntent = new Intent(Intent.ACTION_SENDTO, uri);
        mWhatsAppIntent.setPackage("com.whatsapp");
        context.startActivity(Intent.createChooser(mWhatsAppIntent, ""));
    }

    public static void goToWebsite(Context context, String link) {
        Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static int getStakenumbers(Context context) {
        ActivityManager m = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfoList = m.getRunningTasks(10);
        Iterator<RunningTaskInfo> itr = runningTaskInfoList.iterator();
        int numOfActivities = 0;
        while (itr.hasNext()) {
            RunningTaskInfo runningTaskInfo = (RunningTaskInfo) itr.next();
            int id = runningTaskInfo.id;
            CharSequence desc = runningTaskInfo.description;
            numOfActivities = runningTaskInfo.numActivities;
            String topActivity = runningTaskInfo.topActivity
                    .getShortClassName();
            CommonUtil.PrintLogE("Activities number : " + numOfActivities + " Top Activies : " + topActivity);
            return numOfActivities;
        }
        return numOfActivities;
    }


    public static void setStrokInText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }


    @RequiresApi(api = VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = VERSION.SDK_INT >= VERSION_CODES.KITKAT;

// DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
// MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
