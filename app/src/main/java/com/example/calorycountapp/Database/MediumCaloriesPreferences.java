package com.example.calorycountapp.Database;

import android.content.Context;
import android.preference.PreferenceManager;

public class MediumCaloriesPreferences {

    private static final String HISTORY_COUNT = "historyCount";




    public static int getHistorySize(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(HISTORY_COUNT,0);
    }


    public static void setHistorySize(Context context, int value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(HISTORY_COUNT,value)
                .apply();
    }


}