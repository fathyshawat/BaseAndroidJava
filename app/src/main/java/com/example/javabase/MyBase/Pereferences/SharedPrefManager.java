package com.example.javabase.MyBase.Pereferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.javabase.MyBase.Uitls.Constant;


public class SharedPrefManager {

    Context mContext;

    SharedPreferences mSharedPreferences;

    SharedPreferences.Editor mEditor;
    //

    private static String deviceToken;

    public SharedPrefManager(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(Constant.SharedPrefKey.SHARED_PREF_NAME, mContext.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }


    public Boolean getLoginStatus() {
        Boolean value = mSharedPreferences.getBoolean(Constant.SharedPrefKey.LOGIN_STATUS, false);
        return value;
    }

    public void setLoginStatus(Boolean status) {
        mEditor.putBoolean(Constant.SharedPrefKey.LOGIN_STATUS, status);
        mEditor.commit();
    }

    public void setUserLatLng(double userLat , double userLng){
        mEditor.putString(Constant.SharedPrefKey.USERLAT, String.valueOf(userLat));
        mEditor.putString(Constant.SharedPrefKey.USERLNG, String.valueOf(userLng));
        mEditor.commit();
    }

    public Double getUserLat(){
        String latUser = mSharedPreferences.getString(Constant.SharedPrefKey.USERLAT,"0.0");
        Double userLat = Double.valueOf(latUser);
        return userLat;
    }

    public Double getUserLng(){
        String lngUser = mSharedPreferences.getString(Constant.SharedPrefKey.USERLNG,"0.0");
        Double userLng = Double.valueOf(lngUser);
        return userLng;
    }

    public void setUserDeviceId(String deviceId){
        mEditor.putString(Constant.SharedPrefKey.DEVICE_ID,deviceId);
        mEditor.commit();
    }

    public String getUserDeviceId(){
        String deviceId = mSharedPreferences.getString(Constant.SharedPrefKey.DEVICE_ID,"");
        return deviceId;
    }


    public Boolean getNotificationStatus() {
        Boolean value = mSharedPreferences.getBoolean(Constant.SharedPrefKey.NOTIFICATION, true);
        return value;
    }

    public void setNotificationStatus(Boolean status) {
        mEditor.putBoolean(Constant.SharedPrefKey.NOTIFICATION, status);
        mEditor.commit();
    }




//    public void setUserData(UserModel userModel) {
//        mEditor.putString(Constant.SharedPrefKey.USER, new Gson().toJson(userModel));
//        mEditor.apply();
//    }
//
//    public UserModel getUserData() {
//        Gson gson = new Gson();
//        return gson.fromJson(mSharedPreferences.getString(Constant.SharedPrefKey.USER, null), UserModel.class);
//    }



    public void Logout() {
        mEditor.clear();
        mEditor.apply();
    }
}
