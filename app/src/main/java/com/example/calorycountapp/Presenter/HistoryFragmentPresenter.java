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



    /*SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            Log.d("23","key is "+key);
            if (key.equals("enterCaloryLimit")) {
                Log.d("23",key);
                NumberCaloryPreferences.setSettingsIdent(context,"calculateByUser");

            }
            if (key.equals("screen_gender")||key.equals("enterAge")||key.equals("enterWeight")||key.equals("enterHeight")
                    ||key.equals("list")) {
                NumberCaloryPreferences.setSettingsIdent(context,"calculateByHelper");
            }
        }
    };*/

    public HistoryFragmentPresenter(MvpView view) {
        fragment = (HistoryFragment) view;
        this.context = fragment.getContext();
        db = new DB(context);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        //sp.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void viewIsReady(String ident) {
        checkLimit();
        showDataInView();
    }

    public void showDataInView() {
        HistoryFragmentPresenter.GetDataFromHistory task = new HistoryFragmentPresenter.GetDataFromHistory(db);
        task.execute();

        try {
            if (task.get().isEmpty()) {
                //fragment.showEmptyMessage();
            }
            fragment.showData(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
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

        /*String message = NumberCaloryPreferences.getSettingsIdent(context);


        if (message != null) {
            if (message.equals("calculateByHelper")) {

                if (sp.getString("screen_gender", "").length() > 0 && sp.getString("enterAge", "").length() > 0 && sp.getString("enterWeight", "").length() > 0 &&
                        sp.getString("enterHeight", "").length() > 0 && sp.getString("list", "не выбрано").length() > 0) {

                    String gender = sp.getString("screen_gender", "");
                    int age = Integer.parseInt(sp.getString("enterAge", ""));
                    int weight = Integer.parseInt(sp.getString("enterWeight", ""));
                    int height = Integer.parseInt(sp.getString("enterHeight", ""));
                    String purpose = sp.getString("list", "не выбрано");
                    NumberCaloryPreferences.setLimitCalory(context, calculateDailyLimit(gender, age, weight, height, purpose));
                    IntroDataSharedPreference.setUserGender(context,Integer.parseInt(gender));
                    IntroDataSharedPreference.setUserAge(context,age);
                    IntroDataSharedPreference.setUserWeight(context,weight);
                    IntroDataSharedPreference.setUserHeight(context,height);
                    IntroDataSharedPreference.setUserTarget(context,Integer.parseInt(purpose));
                }
            }

            if (message.equals("calculateByUser")) {

                int userCalory = 0;
                if (sp.getString("enterCaloryLimit", "").length() > 0) {
                    userCalory = Integer.parseInt(sp.getString("enterCaloryLimit", ""));
                }
                NumberCaloryPreferences.setLimitCalory(context, userCalory);
            }
            fragment.showLimit(NumberCaloryPreferences.getLimitCalory(context));
        }
        if(message == null){
            fragment.showLimit(NumberCaloryPreferences.getLimitCalory(context));
        }*/
    //}



    /*private int calculateDailyLimit(String gender,int age,int weight,int height,String purpose){

        int result = 0;
        int mensPercent = (int) (((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5) / 100 * 20);
        int womanPercent = (int) (((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161) / 100 * 20);

        if(purpose.equals("1")) {
            if (gender.equals("1")) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5 - mensPercent);
            }
            if (gender.equals("2")) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161 - womanPercent);
            }
        }

        if(purpose.equals("2")){
            if (gender.equals("1")) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5);
            }
            if (gender.equals("2")) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161);
            }
        }
        if(purpose.equals("3")){
            if (gender.equals("1")) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5 + mensPercent);
            }
            if (gender.equals("2")) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161 + womanPercent);
            }
        }

        return result;
    }*/
//}