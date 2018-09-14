package com.example.calorycountapp.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;


public class IntroActivityAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public IntroActivityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
