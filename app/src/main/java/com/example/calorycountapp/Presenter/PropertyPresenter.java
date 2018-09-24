package com.example.calorycountapp.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Database.IntroDataSharedPreference;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.View.MvpView;
import com.example.calorycountapp.View.Property;

import java.util.concurrent.ExecutionException;

public class PropertyPresenter extends PresenterBase {

    private Property activity;
    private DB model;
    private Context context;

    public PropertyPresenter(MvpView view) {
        activity = (Property) view;
        context = activity;
        model = new DB(context);
    }

    @Override
    public void displayAnotherScreen(String nameOfScreen,String entityIdent) {}

    public void getValueToPreference(int value,String entityType){
        int savedValue = NumberCaloryPreferences.getStoredCalory(context);

        if(entityType.equals(EntityIdent.IS_PRODUCT)) {
            int resultValue = savedValue + value;
            NumberCaloryPreferences.setStoredCalory(context, resultValue);
        }
        else if(entityType.equals(EntityIdent.IS_ACTIVE)) {
            int resultValue = savedValue - value;
            NumberCaloryPreferences.setStoredCalory(context, resultValue);
        }

        activity.returnToPreviouslyActivity();
    }

    public void addEntityToTemporaryTable(String entityName,int value,String entityType){
        AddEntityToDatabase task = new AddEntityToDatabase(model,entityName,value,entityType);
        task.execute();
    }

    @Override
    public void viewIsReady(String ident) {

        if(ident.equals(EntityIdent.IS_PRODUCT)){

            GetProductCaloryTask task =new GetProductCaloryTask(model,EntityIdent.IS_PRODUCT);

            task.execute(activity.entityName);
            try {
                int conversionCalory = conversionCaloryDependingOnAge(task.get());
                activity.setProductCalory(conversionCalory);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        else if(ident.equals(EntityIdent.IS_ACTIVE)){
            GetProductCaloryTask task =new GetProductCaloryTask(model,EntityIdent.IS_ACTIVE);

            task.execute(activity.entityName);
            try {
                activity.setProductCalory(conversionCaloryDependingOnAge(task.get()));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private int conversionCaloryDependingOnAge(int calory){
        int weight = IntroDataSharedPreference.getUserWeight(context);
        int norm = 50;
        int percent = calory/5;
        if(weight<norm){
            for(int i=norm;i>weight;i-=10){
                calory = calory - percent;
            }
        }
        if(weight>norm) {
            for(int i = norm;i<weight;i+=10){
                calory = calory + percent;
            }
        }
        else {
            return calory;
        }

        return calory;
    }

    private class GetProductCaloryTask extends AsyncTask<String, Void, Integer> {

        private DB db;
        private String entityType;

        GetProductCaloryTask (DB db, String entityType){
            this.db = db;
            this.entityType = entityType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int result = 0;
            if(entityType.equals(EntityIdent.IS_PRODUCT)) {
                Cursor c = db.getCaloryForProduct(params[0]);
                if (c.moveToFirst()) {
                    int caloryIndex = c.getColumnIndex(DB.Table.CALORICITY_PRODUCT);

                    do {
                        result = c.getInt(caloryIndex);
                    }
                    while (c.moveToNext());
                }
                c.close();
            }

            if(entityType.equals(EntityIdent.IS_ACTIVE)) {
                Cursor c = db.getCostForActive(params[0]);
                if (c.moveToFirst()) {
                    int caloricityCost = c.getColumnIndex(DB.Table.CALORICITYCOST_ACTIVE);

                    do {
                        result = c.getInt(caloricityCost);
                    }
                    while (c.moveToNext());
                }
                c.close();
            }
            return result;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            db.close();
        }
    }

    //TODO посмотреть можно ли сделать общий класс AsynkTask чтобы не копипастить одинаковые onPreExecute и onPostExecute
    private class AddEntityToDatabase extends AsyncTask<Void,Void,Void>{

        private DB db;
        private String entityName;
        private int entityCount;
        private String entityType;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        public AddEntityToDatabase(DB db, String entityName, int entityCount, String entityType) {
            this.db = db;
            this.entityName = entityName;
            this.entityCount = entityCount;
            this.entityType = entityType;
        }

        @Override
        protected Void doInBackground(Void ... params) {
            db.addEntityToTemporaryDatabase(entityName,entityCount,entityType);
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            super.onPostExecute(params);
            db.close();
        }
    }
}

