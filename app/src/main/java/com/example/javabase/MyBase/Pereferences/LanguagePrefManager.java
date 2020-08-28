package com.example.javabase.MyBase.Pereferences;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguagePrefManager {
    private final static String SHARED_PREF_NAME = "app_pref";
    private final static String App_LANGUAGE = "app_language";

    private Context mContext;

    public LanguagePrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public String getAppLanguage() {
        final SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREF_NAME, 0);
//        return sharedPreferences.getString(App_LANGUAGE, Locale.getDefault().getLanguage());
        return sharedPreferences.getString(App_LANGUAGE, "ar");
    }

    public void setAppLanguage(String language) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(App_LANGUAGE, language);
        editor.apply();
    }
}
