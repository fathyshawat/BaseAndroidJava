package com.example.javabase.MyBase.Listeners;



import com.example.javabase.MyBase.Uitls.CommonUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Pagination
 * Created by Suleiman19 on 10/15/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;

    /**
     * Supporting only LinearLayoutManager for now.
     */
    public PaginationScrollListener(LinearLayoutManager layoutManager) {
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
