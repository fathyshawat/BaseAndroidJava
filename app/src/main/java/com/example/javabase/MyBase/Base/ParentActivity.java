package com.example.javabase.MyBase.Base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.javabase.MyBase.Pereferences.LanguagePrefManager;
import com.example.javabase.MyBase.Pereferences.SharedPrefManager;
import com.example.javabase.MyBase.Uitls.CommonUtil;
import com.example.javabase.MyBase.Uitls.DialogUtil;
import com.example.javabase.MyBase.Uitls.Toaster;

import java.util.Locale;


public abstract class ParentActivity<D extends ViewDataBinding> extends AppCompatActivity {
    protected AppCompatActivity mActivity;

    protected SharedPrefManager mSharedPrefManager;

    protected LanguagePrefManager mLanguagePrefManager;

    Toolbar toolbar;

    protected Context mContext;

    private int menuId;

    protected Toaster mToaster;

    protected Bundle mSavedInstanceState;

    private ProgressDialog mProgressDialog;

    private Dialog mDialog;

    protected D dataBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CommonUtil.setConfig(mLanguagePrefManager.getAppLanguage(), this);
        mContext = this;
        mActivity = this;
        mSharedPrefManager = new SharedPrefManager(mContext);
        mLanguagePrefManager = new LanguagePrefManager(mContext);
        CommonUtil.setConfig(mLanguagePrefManager.getAppLanguage(), this);
        mToaster = new Toaster(mContext);

        if (isFullScreen()) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);


        }

        // set layout resources
        dataBinding= DataBindingUtil.setContentView(mActivity,getLayoutResource());
        this.mSavedInstanceState = savedInstanceState;

        if (isEnableToolbar()) {
            configureToolbar();
        }


    }



    /**
     * this method is responsible for configure toolbar
     * it is called when I enable toolbar in my activity
     */
    private void configureToolbar() {
        //  toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //  toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorOrange));
        // check if enable back
        if (isEnableBack()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//             toolbar.setNavigationIcon(R.mipmap.back);
        }
    }


    /**
     * @return the layout resource id
     */
    protected abstract int getLayoutResource();

    /**
     * it is a boolean method which tell my if toolbar
     * is enabled or not
     */
    protected abstract boolean isEnableToolbar();

    /**
     * it is a boolean method which tell if full screen mode
     * is enabled or not
     */
    protected abstract boolean isFullScreen();

    /**
     * it is a boolean method which tell if back button
     * is enabled or not
     */
    protected abstract boolean isEnableBack();

    /**
     * it is a boolean method which tell if input is
     * is appeared  or not
     */
    protected abstract boolean hideInputType();




    /**
     * this method allowed me to create option menu
     */
    public void createOptionsMenu(int menuId) {
        Log.e("test", "test");
        this.menuId = menuId;
        invalidateOptionsMenu();
    }

    /**
     * this method allowed me to remove option menu
     */
    public void removeOptionsMenu() {
        menuId = 0;
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (menuId != 0) {
            getMenuInflater().inflate(menuId, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
//        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }

    public void hideInputTyping() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    protected void showMyProgressBar() {
        mDialog = DialogUtil.showMyProgressBar(mContext);
    }

    protected void hideMyProgressBar() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    protected void showProgressDialog(String message) {
        mProgressDialog = DialogUtil.showProgressDialog(this, message, false);
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }




    // add localization in all activities
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocale(String lang) {
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang)); // API 17+ only.
        res.updateConfiguration(conf, dm);
    }
}





