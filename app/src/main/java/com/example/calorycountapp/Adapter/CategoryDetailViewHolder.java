package com.example.calorycountapp.Adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.R;

public class CategoryDetailViewHolder extends
        BaseViewHolder<Entity, OnRecyclerObjectClickListener<Entity>> {

    private TextView categoryDetailName;

    public CategoryDetailViewHolder(View itemView) {
        super(itemView);
        categoryDetailName = (TextView) itemView.findViewById(R.id.textViewDetail);
    }

    @Override
    public void onBind(final Entity item, @Nullable final OnRecyclerObjectClickListener<Entity> listener) {

        if(item instanceof Product) {
            categoryDetailName.setText(((Product) item).getName());
        }
        else if(item instanceof Active) {
            categoryDetailName.setText(((Active) item).getNameActive());
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