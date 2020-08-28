package com.example.javabase;

import android.os.Bundle;

import com.example.javabase.MyBase.Base.ParentActivity;
import com.example.javabase.databinding.ActivityMainBinding;

public class MainActivity extends ParentActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBinding.test.setText("Hello World");

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isEnableToolbar() {
        return false;
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected boolean isEnableBack() {
        return false;
    }

    @Override
    protected boolean hideInputType() {
        return false;
    }

}
