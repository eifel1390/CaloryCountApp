package com.example.calorycountapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WelcomeIntroFragment extends Fragment {


    public WelcomeIntroFragment() {}

    public static WelcomeIntroFragment newInstance() {
        WelcomeIntroFragment f = new WelcomeIntroFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_slide, container, false);
        return v;
    }


}
