package com.example.calorycountapp.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Database.MediumCaloriesPreferences;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.View.HistoryFragment;
import com.example.calorycountapp.View.MvpView;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class HistoryFragmentPresenter extends PresenterBase {

    private HistoryFragment fragment;
    private DB db;
    private Context context;
    private SharedPreferences sp;


    public HistoryFragmentPresenter(MvpView view) {
        fragment = (HistoryFragment) view;
        this.context = fragment.getContext();
        db = new DB(context);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void viewIsReady(String ident) {
        checkLimit();
        showDataInView();
    }

    private void showDataInView() {
        HistoryFragmentPresenter.GetDataFromHistory task = new HistoryFragmentPresenter.GetDataFromHistory(db);
        task.execute();

        try {
            fragment.showData(task.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    @Override
    public void displayAnotherScreen(String nameOfScreen, String entityIdent) {
    }

    private class GetDataFromHistory extends AsyncTask<Void, Void, Map<String, Integer>> {

        private DB db;

        public GetDataFromHistory(DB db) {
            this.db = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Map<String, Integer> doInBackground(Void... voids) {
            Map<String, Integer> historyResult = new TreeMap<>();
            Cursor c = db.getAllDataFromHistoryDatabase();

            if (c.moveToFirst()) {
                int historyDateIndex = c.getColumnIndex(DB.Table.HISTORY_DATE);
                int historyCaloryIndex = c.getColumnIndex(DB.Table.HISTORY_DATE_CALORY);

                do {
                    historyResult.put(c.getString(historyDateIndex), c.getInt(historyCaloryIndex));
                }
                while (c.moveToNext());
            }
            c.close();
            MediumCaloriesPreferences.setHistorySize(fragment.getContext(), historyResult.size());
            return historyResult;
        }

        @Override
        protected void onPostExecute(Map<String, Integer> map) {
            super.onPostExecute(map);
            db.close();
        }
    }


    public void countingMediumValue() {
        int historySize = MediumCaloriesPreferences.getHistorySize(context);
        int quantityCalory = NumberCaloryPreferences.getConstantCalory(context);

        if (historySize != 0) {
            fragment.showMediumValue(quantityCalory / historySize);
        } else fragment.showMediumValue(0);
    }

    private void checkLimit() {
        fragment.showLimit(NumberCaloryPreferences.getLimitCalory(context));
    }

}

