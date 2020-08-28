package com.example.javabase.MyBase.Base;


import android.view.View;


import com.example.javabase.MyBase.Listeners.OnItemClickListener;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;


/**
 * is a base class to extend from it the viewholder
 */
public class ParentRecyclerViewHolder extends RecyclerView.ViewHolder   {
    private View clickableRootView; // this is used to change the default onItemClickListener

    public ParentRecyclerViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        if (clickableRootView != null) {
            clickableRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        } else {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setClickableRootView(View clickableRootView) {
        this.clickableRootView = clickableRootView;
    }

    public View findViewById(int viewId) {
        if (itemView != null) {
            return itemView.findViewById(viewId);
        } else {
            return null;
        }
    }
}
