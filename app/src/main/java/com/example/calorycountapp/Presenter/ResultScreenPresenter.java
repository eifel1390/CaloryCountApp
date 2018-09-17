package com.example.calorycountapp.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.calorycountapp.Database.IntroDataSharedPreference;
import com.example.calorycountapp.View.MvpView;
import com.example.calorycountapp.View.ResultScreenActivity;


public class ResultScreenPresenter extends PresenterBase {

    private ResultScreenActivity activity;
    private Context context;

    public ResultScreenPresenter(MvpView mvpView) {
        this.activity = (ResultScreenActivity) mvpView;
        this.context = activity;
    }

    @Override
    public void displayAnotherScreen(String nameOfScreen, String entityIdent) {

    }

    @Override
    public void viewIsReady(String ident) {
        int gender = IntroDataSharedPreference.getUserGender(context);
        int age = IntroDataSharedPreference.getUserAge(context);
        int weight = IntroDataSharedPreference.getUserWeight(context);
        int height = IntroDataSharedPreference.getUserHeight(context);
        int purpose = IntroDataSharedPreference.getUserTarget(context);

        int dailyLimit = calculateDailyLimit(gender,age,weight,height,purpose);
        activity.showDailyLimit(dailyLimit);
    }



    private int calculateDailyLimit(int gender,int age,int weight,int height,int purpose){

        int result = 0;
        int mensPercent = (int) (((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5) / 100 * 20);
        int womanPercent = (int) (((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161) / 100 * 20);

        if(purpose==1) {
            if (gender==1) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5 - mensPercent);
            }
            if (gender==2) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161 - womanPercent);
            }
        }

        if(purpose==2){
            if (gender==1) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5);
            }
            if (gender==2) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161);
            }
        }
        if(purpose==3){
            if (gender==1) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) + 5 + mensPercent);
            }
            if (gender==2) {
                result = (int) ((9.99 * weight) + (6.25 * height) - (4.92 * age) - 161 + womanPercent);
            }
        }

        return result;
    }
}
