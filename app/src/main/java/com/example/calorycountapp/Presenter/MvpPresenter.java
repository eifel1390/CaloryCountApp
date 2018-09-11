package com.example.calorycountapp.Presenter;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void viewIsReady(String ident);

    void detachView();
}

