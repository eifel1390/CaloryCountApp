package com.example.calorycountapp.Presenter;

import com.example.calorycountapp.View.MvpView;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void viewIsReady(String ident);

    void detachView();
}

