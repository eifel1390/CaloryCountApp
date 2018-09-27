package com.example.calorycountapp.Database;


import android.content.Context;
import android.preference.PreferenceManager;

public class IntroDataSharedPreference {


    private static final String USER_WEIGHT = "user_weight";
    private static final String USER_HEIGHT = "user_height";
    private static final String USER_TARGET = "user_target";
    private static final String USER_GENDER = "user_gender";
    private static final String USER_AGE = "user_age";


    public static int getUserWeight(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(USER_WEIGHT,0);
    }


    public static void setUserWeight(Context context,int weight) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(USER_WEIGHT,weight)
                .apply();
    }


    public static int getUserHeight(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(USER_HEIGHT,0);
    }


    public static void setUserHeight(Context context,int height) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(USER_HEIGHT,height)
                .apply();
    }


    public static int getUserTarget(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(USER_TARGET,0);
    }


    public static void setUserTarget(Context context,int target) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(USER_TARGET,target)
                .apply();
    }


    public static int getUserAge(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(USER_AGE,0);
    }


    public static void setUserAge(Context context,int age) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(USER_AGE,age)
                .apply();
    }


    public static int getUserGender(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(USER_GENDER,0);
    }


    public static void setUserGender(Context context,int gender) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(USER_GENDER,gender)
                .apply();
    }



}
