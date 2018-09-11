package com.example.calorycountapp.Adapter;


import android.content.Context;
import android.view.ViewGroup;

import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.R;

public class CategoryAdapter  extends
        GenericRecyclerAdapter<Entity, OnRecyclerObjectClickListener<Entity>,
                CategoryViewHolder> {

    public CategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(inflate(R.layout.category_item, parent));
    }
}
