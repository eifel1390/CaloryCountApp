package com.example.calorycountapp.Database;

import android.content.Context;
import android.preference.PreferenceManager;

public class NumberCaloryPreferences {

    private static final String STORED_CALORY = "stored_calory";
    private static final String CONSTANT_CALORY = "constant_calory";
    private static final String LIMIT_CALORY = "limit_calory";
    private static final String CATEGORY_NAME = "category_name";


    public static int getStoredCalory(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(STORED_CALORY,0);
    }


    public static void setStoredCalory(Context context,int value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(STORED_CALORY,value)
                .apply();
    }

    public static int getConstantCalory(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(CONSTANT_CALORY,0);
    }


    public static void setConstantCalory(Context context,int value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(CONSTANT_CALORY,value)
                .apply();
    }


    public static int getLimitCalory(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(LIMIT_CALORY,0);
    }



    public static void setLimitCalory(Context context,int value) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(LIMIT_CALORY,value)
                .apply();
    }


    public static String getCategoryName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(CATEGORY_NAME,null);
    }



    public static void setCategoryName(Context context,String categoryName) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(CATEGORY_NAME,categoryName)
                .apply();
    }
}