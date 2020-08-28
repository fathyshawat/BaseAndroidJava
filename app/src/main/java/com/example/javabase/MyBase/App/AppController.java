package com.example.javabase.MyBase.App;

import android.app.Application;
import android.content.Context;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;

    private static Context mContext;

    public static synchronized AppController getInstance() {
        return mInstance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this;
    }

}