package com.example.calorycountapp.Presenter;


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

    //обрабатывает нажатие на кнопки + и -
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

