package com.example.javabase.MyBase.Base;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;


import com.example.javabase.MyBase.Listeners.OnItemClickListener;
import com.example.javabase.MyBase.Listeners.PaginationAdapterCallback;
import com.example.javabase.MyBase.Pereferences.LanguagePrefManager;
import com.example.javabase.MyBase.Pereferences.SharedPrefManager;
import com.example.javabase.MyBase.Uitls.DialogUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * is a base class to extend from it the recyclerview adapter
 */
public abstract class ParentRecyclerAdapter<Item> extends RecyclerView.Adapter<ParentRecyclerViewHolder> {

    protected Context mcontext;

    protected List<Item> data;

    protected int layoutId;

    protected boolean isLoadingAdded = false;

    protected boolean retryPageLoad = false;

    private Dialog mDialog;


    protected OnItemClickListener itemClickListener;

    protected PaginationAdapterCallback mPaginationAdapterCallback;

    protected SharedPrefManager mSharedPrefManager;

    protected LanguagePrefManager mLanguagePrefManager;

    protected void showMyProgressBar(){
        mDialog = DialogUtil.showMyProgressBar(mcontext);
    }

    protected void hideMyProgressBar(){
        if (mDialog != null){
            mDialog.dismiss();
        }
    }


    public ParentRecyclerAdapter(Context context) {
        this.mcontext = context;
        mSharedPrefManager = new SharedPrefManager(this.mcontext);
        mLanguagePrefManager = new LanguagePrefManager(this.mcontext);
    }

    public ParentRecyclerAdapter(Context context, List<Item> data) {
        this.mcontext = context;
        this.data = data;
        mSharedPrefManager = new SharedPrefManager(this.mcontext);
        mLanguagePrefManager = new LanguagePrefManager(this.mcontext);
    }

    public ParentRecyclerAdapter(Context context, List<Item> data, int layoutId) {
        this.mcontext = context;
        this.data = data;
        this.layoutId = layoutId;
        mSharedPrefManager = new SharedPrefManager(this.mcontext);
        mLanguagePrefManager = new LanguagePrefManager(this.mcontext);
    }


    public void setOnPaginationClickListener(PaginationAdapterCallback onPaginationClickListener) {
        this.mPaginationAdapterCallback = onPaginationClickListener;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public void InsertAll(List<Item> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void Insert(int position, Item item) {
        data.add(position, item);
        Log.e("Test_Test", position + "");
        notifyDataSetChanged();
    }

    public void Delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void update(int position, Item item) {
        data.remove(position);
        data.add(position, item);
        notifyDataSetChanged();
    }

    public void updateAll(List<Item> items) {
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    public List<Item> getData() {
        return data;
    }

    public void addFooterProgress() {
        this.data.add(null);
        notifyItemInserted(data.size() - 1);
    }

    public void removeFooterProgress() {
        data.remove(data.size() - 1);
        notifyItemRemoved(data.size());
        Log.e("footer", "gone");
    }



    public void addLoadingFooter(Item item) {
        isLoadingAdded = true;
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = data.size() - 1;
        data.remove(position);
        notifyItemRemoved(position);
    }
}
