package com.example.calorycountapp.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.ItemTouchHelperClass;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.TemporaryEntity;
import com.example.calorycountapp.R;
import com.example.calorycountapp.View.DailyStatisticsFragment;

import java.util.Collections;
import java.util.List;

public class DailyStatisticsAdapter extends GenericRecyclerAdapter<Entity, OnRecyclerObjectClickListener<Entity>,
        DailyStatisticsViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {

    private TemporaryEntity deletedItem;
    private int indexOfDeletedItem;
    private Context context;
    private DailyStatisticsFragment fragment;
    private TemporaryDeleteDataTask task;
    private DB db;
    private CoordinatorLayout coordinatorLayout;
    private List<Entity> items;
    private int result;

    public DailyStatisticsAdapter(Context context, DailyStatisticsFragment fragment,CoordinatorLayout layout) {
        super(context);
        this.context = context;
        this.fragment = fragment;
        db = new DB(context);
        coordinatorLayout = layout;
        this.items = super.items;
    }

    @Override
    public DailyStatisticsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DailyStatisticsViewHolder(inflate(R.layout.temporary_item, parent));
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if(fromPosition<toPosition){
            for(int i=fromPosition; i<toPosition; i++){
                Collections.swap(items, i, i+1);
            }
        }
        else {
            for(int i=fromPosition; i > toPosition; i--){
                Collections.swap(items, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRemoved(int position) {
        deletedItem = (TemporaryEntity)items.remove(position);
        indexOfDeletedItem = position;

        int numberCalory = NumberCaloryPreferences.getStoredCalory(context);

        if(deletedItem.getEntityType().equals(EntityIdent.IS_PRODUCT)) {
            result = numberCalory-deletedItem.getTemporaryCount();
            NumberCaloryPreferences.setStoredCalory(context,result);
            fragment.passValue(result);
            task = new TemporaryDeleteDataTask(db,deletedItem.getTemporaryName());
            task.execute();
        }

        if(deletedItem.getEntityType().equals(EntityIdent.IS_ACTIVE)) {
            result = numberCalory+deletedItem.getTemporaryCount();
            NumberCaloryPreferences.setStoredCalory(context,result);
            fragment.passValue(result);
            task = new TemporaryDeleteDataTask(db,deletedItem.getTemporaryName());
            task.execute();
        }

        notifyItemRemoved(position);

        Snackbar.make(coordinatorLayout, "Удалено "+deletedItem.getTemporaryName(),Snackbar.LENGTH_LONG)

                .setAction("ОТМЕНИТЬ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.add(indexOfDeletedItem, deletedItem);
                        TemporaryAddDataTask task = new TemporaryAddDataTask(db,deletedItem.getTemporaryName(),
                                deletedItem.getTemporaryCount(),deletedItem.getEntityType(),deletedItem.getTemporaryConsumption());
                        task.execute();
                        if(deletedItem.getEntityType().equals(EntityIdent.IS_PRODUCT)) {
                            int number = NumberCaloryPreferences.getStoredCalory(context);
                            result = number+deletedItem.getTemporaryCount();
                            NumberCaloryPreferences.setStoredCalory(context,result);
                            fragment.passValue(result);
                        }

                        if(deletedItem.getEntityType().equals(EntityIdent.IS_ACTIVE)) {
                            int number = NumberCaloryPreferences.getStoredCalory(context);
                            result = number-deletedItem.getTemporaryCount();
                            NumberCaloryPreferences.setStoredCalory(context,result);
                            fragment.passValue(result);
                        }

                        notifyItemInserted(indexOfDeletedItem);
                    }
                }).show();

    }

    private class TemporaryDeleteDataTask extends AsyncTask<Void, Void, Void> {

        private DB db;
        String name;

        TemporaryDeleteDataTask(DB db, String name){
            this.db = db;
            this.name = name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Void doInBackground(Void...params) {
            db.deleteEntityFromTemporaryDatabase(name);
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            db.close();
        }
    }

    private class TemporaryAddDataTask extends AsyncTask<Void, Void, Void> {

        private DB db;
        private String name;
        private int value;
        private String type;
        private int consumption;

        TemporaryAddDataTask(DB db, String entityName, int value, String type, int consumption){
            this.db = db;
            this.name = entityName;
            this.value = value;
            this.type = type;
            this.consumption = consumption;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Void doInBackground(Void...params) {
            db.addEntityToTemporaryDatabase(name,value,type,consumption);
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            db.close();
        }
    }
}