package com.example.calorycountapp.Presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.View.AddNewEntity;
import com.example.calorycountapp.View.CategoryDetail;
import com.example.calorycountapp.View.MvpView;
import com.example.calorycountapp.View.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class CategoryDetailPresenter extends PresenterBase {

    private CategoryDetail activity;
    private Context context;
    private DB model;

    public CategoryDetailPresenter(MvpView view) {
        activity = (CategoryDetail) view;
        context = activity;
        model = new DB(context);
    }

    //вызывается если пользователь хочет добавить новый продукт или активность
    @Override
    public void displayAnotherScreen(String nameCategory, String entityIdent) {
        Intent intent = new Intent(activity, AddNewEntity.class);
        intent.putExtra(AddNewEntity.ENTITY_CATEGORY, nameCategory);
        intent.putExtra(AddNewEntity.ENTITY_TYPE,entityIdent);
        activity.startActivity(intent);
    }

    //вызывается при нажатии пользователем на продукт или активность
    public void displayPropertyActivity(String productName,String entityType){
        Intent intent = new Intent(activity,Property.class);
        intent.putExtra(Property.ENTITY_NAME, productName);
        intent.putExtra(Property.ENTITY_TYPE, entityType);
        activity.startActivity(intent);
    }

    //получает название категории и идент,который определяет загружать из базы
    //список из продуктов или активности
    public void loadNameByCategory(String categoryName,String entityIdent){
        if(entityIdent.equals(EntityIdent.IS_PRODUCT)) {
            DetailCategoryTask task = new DetailCategoryTask(model, EntityIdent.IS_PRODUCT);
            loadEntityList(task,categoryName);
        }

        if(entityIdent.equals(EntityIdent.IS_ACTIVE)) {
            DetailCategoryTask task = new DetailCategoryTask(model, EntityIdent.IS_ACTIVE);
            loadEntityList(task,categoryName);
        }
    }

    //получает таск,достает его результат(список продуктов или активностей)
    //и отображает его в активити
    public void loadEntityList(DetailCategoryTask task,String categoryName){
        task.execute(categoryName);
        try {
            activity.showData(task.get());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewIsReady(String ident) {}

    //таск который загружает из базы список продуктов или активностей
    private class DetailCategoryTask extends AsyncTask<String, Void, List<Entity>> {

        private DB db;
        private String ident;

        public DetailCategoryTask(DB db, String ident) {
            this.db = db;
            this.ident = ident;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected List<Entity> doInBackground(String... params) {
            List<Entity>entityList = new ArrayList<>();
            if(ident.equals(EntityIdent.IS_PRODUCT)) {
                Cursor c = db.getProductListByCategory(params[0]);

                if (c.moveToFirst()) {
                    int idNameProductIndex = c.getColumnIndex(DB.Table.NAME_PRODUCT);
                    int idProductCaloricity = c.getColumnIndex(DB.Table.CALORICITY_PRODUCT);

                    do {
                        Product product = new Product();
                        product.setName(c.getString(idNameProductIndex));
                        product.setCaloricity(c.getInt(idProductCaloricity));
                        entityList.add(product);
                    }
                    while (c.moveToNext());
                }
                c.close();
            }

            if(ident.equals(EntityIdent.IS_ACTIVE)) {
                Cursor c = db.getActiveListByCategory(params[0]);

                if (c.moveToFirst()) {
                    int idNameActiveIndex = c.getColumnIndex(DB.Table.NAME_ACTIVE);

                    int idActiveCaloricityCost = c.getColumnIndex(DB.Table.CALORICITYCOST_ACTIVE);

                    do {
                        Active active = new Active();
                        active.setNameActive(c.getString(idNameActiveIndex));
                        active.setCaloricityCost(c.getInt(idActiveCaloricityCost));
                        entityList.add(active);
                    }
                    while (c.moveToNext());
                }
                c.close();
            }
            return entityList;
        }



        @Override
        protected void onPostExecute(List<Entity> list) {
            super.onPostExecute(list);
            db.close();
        }
    }
}
