package com.example.calorycountapp;

import com.example.calorycountapp.Adapter.BaseRecyclerListener;

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {

    void onItemClicked(T item);

}
