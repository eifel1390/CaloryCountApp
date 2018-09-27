package com.example.calorycountapp.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.View.MainActivity;
import com.example.calorycountapp.View.MvpView;
import com.example.calorycountapp.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivityPresenter extends PresenterBase {

    private MainActivity mainActivity;
    private Context context;


    public MainActivityPresenter(MvpView mainActivity) {
        this.mainActivity = (MainActivity) mainActivity;
        context = this.mainActivity;
    }

    @Override
    public void viewIsReady(String ident) {
        setScheduledExecutorService();
    }

    @Override
    public void displayAnotherScreen(String nameOfScreen, String entityIdent) {
        Intent i = new Intent(mainActivity, SettingsActivity.class);
        mainActivity.startActivity(i);
    }



    private void setScheduledExecutorService(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int delayInHour = hour < 13 ? 13 - hour : 24 - (hour - 13);

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {

                        int caloryOfDay = NumberCaloryPreferences.getStoredCalory(context);

                        int constantCalory = NumberCaloryPreferences.getConstantCalory(context);

                        if(caloryOfDay<0){
                            caloryOfDay = 0;
                        }
                        String timeStamp = new SimpleDateFormat("d MMM",new Locale("ru")).format(Calendar.getInstance().getTime());

                        SetDataToHistoryDatabase taskHistory = new SetDataToHistoryDatabase(timeStamp,caloryOfDay,context);
                        taskHistory.execute();

                        constantCalory += caloryOfDay;

                        NumberCaloryPreferences.setConstantCalory(context,constantCalory);

                        NumberCaloryPreferences.setStoredCalory(context,0);

                        mainActivity.recreateFragment();
                    }
                }, delayInHour, 24, TimeUnit.HOURS);
    }


    private class SetDataToHistoryDatabase extends AsyncTask<Void,Void,Void> {

        private int caloryNumberPerDay;
        private String currentDate;
        private DB db;

        public SetDataToHistoryDatabase(String currentDate,int caloryNumber,Context context) {
            this.caloryNumberPerDay = caloryNumber;
            this.currentDate = currentDate;
            db = new DB(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db.open();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.addEntityToHistoryDatabase(currentDate,caloryNumberPerDay);
            db.deleteAllFromTemporaryDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            db.close();
        }

    }
}




