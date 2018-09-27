package com.example.calorycountapp.Adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.TemporaryEntity;
import com.example.calorycountapp.R;

public class DailyStatisticsViewHolder extends
        BaseViewHolder<Entity, OnRecyclerObjectClickListener<Entity>> {

    private TextView temporaryEntityName;
    private TextView temporaryAmountOfData;
    private TextView temporaryTimeOrGramm;

    private View view;


    public DailyStatisticsViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        temporaryEntityName =  itemView.findViewById(R.id.textView);
        temporaryAmountOfData =  itemView.findViewById(R.id.countTextView);
        temporaryTimeOrGramm =  itemView.findViewById(R.id.timeOrGramm);

    }


    @Override
    public void onBind(final Entity item, @Nullable final OnRecyclerObjectClickListener<Entity> listener) {
        TemporaryEntity value = (TemporaryEntity) item;
        temporaryEntityName.setText(value.getTemporaryName());
        String entityCount = String.valueOf(value.getTemporaryCount());
        if(value.getEntityType().equals(EntityIdent.IS_PRODUCT)){
            temporaryEntityName.setTextColor(view.getResources().getColor(R.color.dot_dark_screen2));
            StringBuilder builder1 = new StringBuilder();
            builder1.append(value.getTemporaryConsumption());
            builder1.append(" грамм");
            temporaryTimeOrGramm.setText(builder1.toString());
            temporaryTimeOrGramm.setTextColor(view.getResources().getColor(R.color.dot_dark_screen2));
            StringBuilder builder=new StringBuilder();
            builder.append(" + ");
            builder.append(entityCount);
            temporaryAmountOfData.setText(builder.toString());
            temporaryAmountOfData.setTextColor(view.getResources().getColor(R.color.dot_dark_screen2));


        }
        if(value.getEntityType().equals(EntityIdent.IS_ACTIVE)){
            temporaryEntityName.setTextColor(view.getResources().getColor(R.color.tomato));
            StringBuilder builder1 = new StringBuilder();
            builder1.append(value.getTemporaryConsumption());
            builder1.append(" минут");
            temporaryTimeOrGramm.setText(builder1.toString());
            temporaryTimeOrGramm.setTextColor(view.getResources().getColor(R.color.tomato));
            StringBuilder builder=new StringBuilder();
            builder.append(" - ");
            builder.append(entityCount);
            temporaryAmountOfData.setText(builder.toString());
            temporaryAmountOfData.setTextColor(view.getResources().getColor(R.color.tomato));
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