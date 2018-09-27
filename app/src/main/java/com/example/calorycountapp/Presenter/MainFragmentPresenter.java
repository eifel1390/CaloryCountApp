package com.example.calorycountapp.Presenter;


import android.content.Intent;

import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.View.Category;
import com.example.calorycountapp.View.MainFragment;
import com.example.calorycountapp.View.MvpView;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFragmentPresenter extends PresenterBase {

    private MainFragment fragment;

    public MainFragmentPresenter(MvpView mvpView) {
        this.fragment = (MainFragment)mvpView;
    }

    @Override
    public void viewIsReady(String string) {}


    @Override
    public void displayAnotherScreen(String nameOfScreen,String entityIdent) {
        Intent intent;
        if(nameOfScreen.equals(EntityIdent.IS_PRODUCT)){
            intent = new Intent(fragment.getActivity(),Category.class);
            intent.putExtra(Category.ENTITY_IDENT,EntityIdent.IS_PRODUCT);
            fragment.startActivity(intent);
        }
        if(nameOfScreen.equals(EntityIdent.IS_ACTIVE)){
            intent = new Intent(fragment.getActivity(),Category.class);
            intent.putExtra(Category.ENTITY_IDENT,EntityIdent.IS_ACTIVE);
            fragment.startActivity(intent);
        }
    }
}

