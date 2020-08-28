package com.example.javabase.MyBase.Uitls;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static void displayLocationSettingsRequest(Context context , final Activity mActivity) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(mActivity, 100);
                        } catch (IntentSender.SendIntentException e) { }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }

    public static boolean isPermissionGranted(Activity activity, String permissionName, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(permissionName)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Tag", "Permission is granted");
                return true;
            } else {
                Log.v("tag", "Permission is revoked");
                ActivityCompat.requestPermissions(activity,
                        new String[]{permissionName}, requestCode);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("tag", "Permission is granted");
            return true;
        }
    }

    public static final String[] IMAGE_PERMISSIONS =
            {Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] DOWNLOAD_PERMISSIONS =
            {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final String[] GPS_PERMISSION =
            {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final String[] CALL_PHONE =
            {Manifest.permission.CALL_PHONE};

    public static final int IMAGE_PERMISSION_RESPONSE = 1;

    public static final int DOWNLOAD_PERMISSION_RESPONSE = 2;

    /**
     * @return whether all the required permission for taking and picking an image are granted or not
     */
    public static boolean ImagePermissionsGranted(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String permission : IMAGE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllPermissionGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("SSSs", permission.toString());
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean canMakeSmores(int BuildVersion) {
        return (Build.VERSION.SDK_INT > BuildVersion);

      // Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static  boolean isPhonePermissionGranted(Context context , Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {
                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }
}
