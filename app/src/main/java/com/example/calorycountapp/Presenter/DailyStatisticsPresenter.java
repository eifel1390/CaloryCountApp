package com.example.calorycountapp.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.TemporaryEntity;
import com.example.calorycountapp.View.DailyStatisticsFragment;
import com.example.calorycountapp.View.MvpView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DailyStatisticsPresenter extends PresenterBase  {
    private DailyStatisticsFragment fragment;
    private DB model;
    private Context context;

    public DailyStatisticsPresenter(MvpView view) {
        fragment = (DailyStatisticsFragment) view;
        context = fragment.getContext();
        model = new DB(context);
    }

    @Override
    public void displayAnotherScreen(String nameOfScreen, String entityIdent) {}

    @Override
    public void viewIsReady(String ident) {
        //запрос к временной таблице,получение данных
        showDataInView();
    }

    public void showDataInView(){
        DailyStatisticsPresenter.TemporaryDataTask task = new DailyStatisticsPresenter.TemporaryDataTask(model);
        task.execute();
        try {
            fragment.showData(task.get());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class TemporaryDataTask extends AsyncTask<Void, Void, List<Entity>> {

        private DB db;

        TemporaryDataTask(DB db){
            this.db = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected List<Entity> doInBackground(Void... params) {
            List<Entity>entityList = new ArrayList<>();

            Cursor c = db.getAllDataFromTemporaryTable();
            if(c.moveToFirst()) {
                int entityNameIndex = c.getColumnIndex(DB.Table.TEMPORARY_ENTITY_NAME);
                int entityCountIndex = c.getColumnIndex(DB.Table.TEMPORARY_ENTITY_COST);
                int entityTypeIndex = c.getColumnIndex(DB.Table.TEMPORARY_ENTITY_TYPE);
                do {
                    TemporaryEntity temporaryEntity = new TemporaryEntity();
                    temporaryEntity.setTemporaryName(c.getString(entityNameIndex));
                    temporaryEntity.setTemporaryCount(c.getInt(entityCountIndex));
                    temporaryEntity.setEntityType(c.getString(entityTypeIndex));
                    entityList.add(temporaryEntity);
                }
                while (c.moveToNext());
            }
            c.close();
            return entityList;
        }

        @Override
        protected void onPostExecute(List<Entity>list) {
            super.onPostExecute(list);
            db.close();
        }
    }
}

