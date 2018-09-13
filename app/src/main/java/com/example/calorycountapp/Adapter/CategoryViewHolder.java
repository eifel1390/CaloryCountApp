package com.example.calorycountapp.Adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.R;

public class CategoryViewHolder extends
        BaseViewHolder<Entity, OnRecyclerObjectClickListener<Entity>> {

    private TextView categoryName;
    private ImageView categoryImage;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        categoryName = (TextView) itemView.findViewById(R.id.textView3);
        categoryImage = (ImageView) itemView.findViewById(R.id.imageView);
    }


    @Override
    public void onBind(final Entity item, @Nullable final OnRecyclerObjectClickListener<Entity> listener) {

        if(item instanceof Product) {
            categoryName.setText(((Product) item).getCategoryProduct());
            if(((Product) item).getCategoryProduct().equals("Фрукты")){
                categoryImage.setImageResource(R.drawable.ic_orange);
            }
            if(((Product) item).getCategoryProduct().equals("Овощи")){
                categoryImage.setImageResource(R.drawable.ic_vegetable);
            }
            if(((Product) item).getCategoryProduct().equals("Напитки")){
                categoryImage.setImageResource(R.drawable.ic_beverage);
            }
            if(((Product) item).getCategoryProduct().equals("Алкоголь")){
                categoryImage.setImageResource(R.drawable.ic_alcohol_beverage);
            }
            if(((Product) item).getCategoryProduct().equals("Сладости")){
                categoryImage.setImageResource(R.drawable.ic_sweets);
            }
            if(((Product) item).getCategoryProduct().equals("Хлеб и выпечка")){
                categoryImage.setImageResource(R.drawable.ic_bread);
            }
            if(((Product) item).getCategoryProduct().equals("Рыба и морепродукты")){
                categoryImage.setImageResource(R.drawable.ic_fish);
            }
            if(((Product) item).getCategoryProduct().equals("Масло")){
                categoryImage.setImageResource(R.drawable.ic_butter);
            }
            if(((Product) item).getCategoryProduct().equals("Крупы")){
                categoryImage.setImageResource(R.drawable.ic_cereals);
            }
            if(((Product) item).getCategoryProduct().equals("Консервы")){
                categoryImage.setImageResource(R.drawable.ic_conservative);
            }
            if(((Product) item).getCategoryProduct().equals("Снеки")){
                categoryImage.setImageResource(R.drawable.ic_snack);
            }
            if(((Product) item).getCategoryProduct().equals("Соусы и приправы")){
                categoryImage.setImageResource(R.drawable.ic_sauce);
            }
            if(((Product) item).getCategoryProduct().equals("Мясо")){
                categoryImage.setImageResource(R.drawable.ic_meat);
            }
            if(((Product) item).getCategoryProduct().equals("Молоко")){
                categoryImage.setImageResource(R.drawable.ic_milk);
            }
            if(((Product) item).getCategoryProduct().equals("Сыры")){
                categoryImage.setImageResource(R.drawable.ic_cheese);
            }
            if(((Product) item).getCategoryProduct().equals("Соки")){
                categoryImage.setImageResource(R.drawable.ic_orange_juice);
            }
            if(((Product) item).getCategoryProduct().equals("Грибы")){
                categoryImage.setImageResource(R.drawable.ic_mushroom);
            }
            if(((Product) item).getCategoryProduct().equals("Орехи")){
                categoryImage.setImageResource(R.drawable.ic_hazelnut);
            }
            if(((Product) item).getCategoryProduct().equals("Колбаса и копчености")){
                categoryImage.setImageResource(R.drawable.ic_salami);
            }
            if(((Product) item).getCategoryProduct().equals("Яйца")){
                categoryImage.setImageResource(R.drawable.ic_fried_egg);
            }
            if(((Product) item).getCategoryProduct().equals("Зелень")){
                categoryImage.setImageResource(R.drawable.ic_salad);
            }

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