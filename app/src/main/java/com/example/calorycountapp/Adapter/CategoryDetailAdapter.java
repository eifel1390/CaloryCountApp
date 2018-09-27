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
import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.R;

import java.util.Collections;
import java.util.List;

public class CategoryDetailAdapter extends
        GenericRecyclerAdapter<Entity, OnRecyclerObjectClickListener<Entity>,
                CategoryDetailViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {

    private List<Entity> items;
    private EntityDeleteFromDatabase task;
    private int indexOfDeletedItem;
    private CoordinatorLayout coordinatorLayout;
    private DB db;
    private Context context;

    public CategoryDetailAdapter(Context context,CoordinatorLayout layout) {
        super(context);
        this.context = context;
        this.coordinatorLayout = layout;
        db = new DB(context);
        this.items = super.items;
    }

    @Override
    public CategoryDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryDetailViewHolder(inflate(R.layout.category_detail_item, parent));
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
    public void onItemRemoved(final int position) {
        Entity value = items.remove(position);
        indexOfDeletedItem = position;

        if (value instanceof Product) {
            Product product1 = (Product)value;
            task = new EntityDeleteFromDatabase(db,product1.getName(),"product");
            task.execute();
            setShackBar(product1);
        }
        if (value instanceof Active) {
            Active active1 = (Active)value;
            task = new EntityDeleteFromDatabase(db,active1.getNameActive(),"active");
            task.execute();
            setShackBar(active1);
        }
        notifyItemRemoved(position);
    }


    private void setShackBar(final Entity entity){
        Snackbar.make(coordinatorLayout, R.string.deleted+" "+kindOfEntity(entity),Snackbar.LENGTH_LONG)

                .setAction(R.string.cancelled, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.add(indexOfDeletedItem, entity);

                        if(entity instanceof Product){
                            Product product = (Product) entity;
                            EntityAddToDatabase task = new EntityAddToDatabase(db,product.getName(),product.getCaloricity(),
                                    EntityIdent.IS_PRODUCT,NumberCaloryPreferences.getCategoryName(context));
                            task.execute();
                        }
                        if(entity instanceof Active){
                            Active active = (Active) entity;
                            EntityAddToDatabase task = new EntityAddToDatabase(db,active.getNameActive(),
                                    active.getCaloricityCost(),EntityIdent.IS_ACTIVE,NumberCaloryPreferences.getCategoryName(context));
                            task.execute();
                        }

                        notifyItemInserted(indexOfDeletedItem);
                    }
                }).show();

    }

    private String kindOfEntity(Entity entity){

        if(entity instanceof Product){
            Product product = (Product) entity;
            return product.getName();
        }
        if(entity instanceof Active){
            Active active = (Active) entity;
            return active.getNameActive();
        }
        return "";
    }

    private class EntityDeleteFromDatabase extends AsyncTask<Void, Void, Void> {

        private DB db;
        String name;
        String identOfEntity;

        EntityDeleteFromDatabase(DB db, String name,String identOfEntity){
            this.db = db;
            this.name = name;
            this.identOfEntity = identOfEntity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Void doInBackground(Void...params) {
            if(identOfEntity.equals(EntityIdent.IS_PRODUCT)) {
                db.deleteProductFromDatabase(name);
            }
            if(identOfEntity.equals(EntityIdent.IS_ACTIVE)) {
                db.deleteActiveFromDatabase(name);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            db.close();
        }
    }

    private class EntityAddToDatabase extends AsyncTask<Void, Void, Void> {

        private DB db;
        private String type;
        private String name;
        private int caloryOrCost;
        private String category;


        EntityAddToDatabase(DB db, String entityName, int value, String type,String category){
            this.db = db;
            this.name = entityName;
            this.type = type;
            this.category = category;
            this.caloryOrCost = value;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Void doInBackground(Void...params) {
            if(type.equals(EntityIdent.IS_PRODUCT)){
                db.addProductToDatabase(category,name,caloryOrCost);
            }
            if(type.equals(EntityIdent.IS_ACTIVE)){
                db.addActiveToDatabase(category,name,caloryOrCost);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            db.close();
        }
    }
}
