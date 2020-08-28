package com.example.javabase.MyBase.Base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.javabase.MyBase.Pereferences.LanguagePrefManager;
import com.example.javabase.MyBase.Pereferences.SharedPrefManager;
import com.example.javabase.MyBase.Uitls.DialogUtil;


public abstract class BaseFragment<D extends ViewDataBinding> extends Fragment {

    public SharedPrefManager mSharedPrefManager;

    protected LanguagePrefManager mLanguagePrefManager;

    protected Context mContext;

    public Bundle mSavedInstanceState;

    private ProgressDialog mProgressDialog;

    private Dialog mDialog;

    protected D dataBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false);
        mContext = getActivity();
        mSharedPrefManager = new SharedPrefManager(mContext);
        mLanguagePrefManager = new LanguagePrefManager(mContext);
        this.mSavedInstanceState = savedInstanceState;


        return dataBinding.getRoot();
    }


    protected abstract int getLayoutResource();


    /**
     * it the current activity is a recycle
     */

    protected void showMyProgressBar() {
        mDialog = DialogUtil.showMyProgressBar(mContext);
    }

    protected void hideMyProgressBar() {
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }

    protected void showProgressDialog(String message) {
        mProgressDialog = DialogUtil.showProgressDialog(getActivity(), message, false);
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


}
