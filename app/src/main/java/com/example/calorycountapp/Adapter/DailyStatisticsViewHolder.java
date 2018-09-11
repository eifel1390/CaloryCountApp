package com.example.calorycountapp.Adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DailyStatisticsViewHolder extends
        BaseViewHolder<Entity, OnRecyclerObjectClickListener<Entity>> {

    private TextView temporaryEntityName;
    private TextView temporaryAmountOfData;
    private LinearLayout layout;
    private View view;


    public DailyStatisticsViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        temporaryEntityName = (TextView) itemView.findViewById(R.id.textView);
        temporaryAmountOfData = (TextView) itemView.findViewById(R.id.countTextView);
        layout = (LinearLayout) itemView.findViewById(R.id.temporary_linearlayout);
    }


    @Override
    public void onBind(final Entity item, @Nullable final OnRecyclerObjectClickListener<Entity> listener) {
        TemporaryEntity value = (TemporaryEntity) item;
        temporaryEntityName.setText(value.getTemporaryName());
        String entityCount = String.valueOf(value.getTemporaryCount());
        temporaryAmountOfData.setText(entityCount);
        if(value.getEntityType().equals("product")){
            layout.setBackgroundColor(view.getResources().getColor(R.color.lightBlue));
        }
        if(value.getEntityType().equals("active")){
            layout.setBackgroundColor(view.getResources().getColor(R.color.pink));
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