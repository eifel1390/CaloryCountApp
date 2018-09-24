package com.example.calorycountapp.Adapter;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.ItemTouchHelperClass;
import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.Model.TemporaryEntity;
import com.example.calorycountapp.R;

import java.util.Collections;
import java.util.List;

public class CategoryDetailAdapter extends
        GenericRecyclerAdapter<Entity, OnRecyclerObjectClickListener<Entity>,
                CategoryDetailViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter {

    private List<Entity> items;
    private EntityDeleteFromDatabase task;
    private Entity deletedItem;
    private int indexOfDeletedItem;
    private CoordinatorLayout coordinatorLayout;
    private DB db;

    public CategoryDetailAdapter(Context context,CoordinatorLayout layout) {
        super(context);
        this.coordinatorLayout = layout;
        db = new DB(context);
        this.items = super.items;
    }

    @Override
    public CategoryDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryDetailViewHolder(inflate(R.layout.category_detail_item2, parent));
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
        deletedItem = items.remove(position);
        Log.d("23","deletedItem class is "+String.valueOf(deletedItem.getClass()));
        indexOfDeletedItem = position;


        if(deletedItem instanceof Product) {
            Product product = (Product) deletedItem;
            Log.d("23","deletedItem category is "+String.valueOf(product.getCategoryProduct()));
            task = new EntityDeleteFromDatabase(db,product.getName(),"product");
            task.execute();
        }

        if(deletedItem instanceof Active) {
            Active active = (Active) deletedItem;
            task = new EntityDeleteFromDatabase(db,active.getNameActive(),"active");
            task.execute();
        }

        notifyItemRemoved(position);

        Snackbar.make(coordinatorLayout, "Удалено "+kindOfEntity(deletedItem),Snackbar.LENGTH_LONG)

                .setAction("ОТМЕНИТЬ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.add(indexOfDeletedItem, deletedItem);

                        if(deletedItem instanceof Product){
                            Log.d("23","deletedItem is null? "+String.valueOf(deletedItem==null));
                            Product product = (Product) deletedItem;
                            Log.d("23","product is null? "+String.valueOf(product==null));
                            //String name = p.getName();
                            //int calory = ((Product) deletedItem).getCaloricity();
                            //String category = ((Product) deletedItem).getCategoryProduct();
                            //Log.d("23",String.valueOf(product.getCategoryProduct()));
                            EntityAddToDatabase task = new EntityAddToDatabase(db,product.getName(),product.getCaloricity(),
                                    "product",product.getCategoryProduct());
                            task.execute();
                        }
                        if(deletedItem instanceof Active){
                            Log.d("23","deletedItem is null? "+String.valueOf(deletedItem==null));
                            Active active = (Active) deletedItem;
                            Log.d("23","active is null? "+String.valueOf(active==null));
                            EntityAddToDatabase task = new EntityAddToDatabase(db,active.getNameActive(),
                                    active.getCaloricityCost(),"active",active.getCategoryActive());
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
            if(identOfEntity.equals("product")) {
                Log.d("34","in delete "+name);
                db.deleteProductFromDatabase(name);


            }
            if(identOfEntity.equals("active")) {
                Log.d("34",name);
                Log.d("34","in delete "+name);
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
            if(type.equals("product")){
                Log.d("34","in insert "+category);
                db.addProductToDatabase(category,name,caloryOrCost);

            }
            if(type.equals("active")){
                Log.d("34","in insert "+category);
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
