package com.example.calorycountapp.View;

import android.view.View;

public interface MvpView {
    void initView();
    void initView(View v);
    void initToolbar();
    void initToolbar(View v);
    void initPresenter();
}
