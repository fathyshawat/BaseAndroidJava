package com.example.javabase.MyBase.Listeners;


import android.util.Log;


import com.example.javabase.MyBase.Uitls.CommonUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Pagination
 * Created by Suleiman19 on 10/15/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */
public abstract class PaginationGridLayoutScrollListener extends RecyclerView.OnScrollListener {

    private GridLayoutManager layoutManager;

    /**
     * Supporting only LinearLayoutManager for now.
     */
    public PaginationGridLayoutScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        CommonUtil.PrintLogE("visibleItemCount : " + visibleItemCount + "  totalItemCount : " + totalItemCount
                + "firstVisibleItemPosition :" + firstVisibleItemPosition);

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                Log.e("here","here");
                CommonUtil.PrintLogE("Scroll");
                loadMoreItems();
            } else {
                CommonUtil.PrintLogE("Not Scroll");
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
