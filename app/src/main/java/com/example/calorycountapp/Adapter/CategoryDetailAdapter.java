package com.example.calorycountapp.Adapter;


import android.content.Context;
import android.view.ViewGroup;

import com.example.calorycountapp.R;

public class CategoryDetailAdapter extends
        GenericRecyclerAdapter<Entity, OnRecyclerObjectClickListener<Entity>,
                CategoryDetailViewHolder> {

    public CategoryDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public CategoryDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryDetailViewHolder(inflate(R.layout.category_detail_item, parent));
    }
}
