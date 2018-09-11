package com.example.calorycountapp.Adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder <T, L extends BaseRecyclerListener> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(T item, @Nullable L listener);
}
