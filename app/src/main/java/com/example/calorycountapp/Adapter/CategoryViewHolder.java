package com.example.calorycountapp.Adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.R;

public class CategoryViewHolder extends
        BaseViewHolder<Entity, OnRecyclerObjectClickListener<Entity>> {

    private TextView categoryName;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        categoryName = (TextView) itemView.findViewById(R.id.textView);
    }


    @Override
    public void onBind(final Entity item, @Nullable final OnRecyclerObjectClickListener<Entity> listener) {

        if(item instanceof Product) {
            categoryName.setText(((Product) item).getCategoryProduct());
        }

        else if(item instanceof Active) {
            categoryName.setText(((Active) item).getCategoryActive());
        }

        if (listener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(item);
                }
            });
        }
    }
}