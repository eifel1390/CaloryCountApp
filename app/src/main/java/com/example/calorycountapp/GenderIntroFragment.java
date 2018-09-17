package com.example.calorycountapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.calorycountapp.Database.IntroDataSharedPreference;


public class GenderIntroFragment extends Fragment {


    public GenderIntroFragment() {}

    public static GenderIntroFragment newInstance() {
        GenderIntroFragment f = new GenderIntroFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gender_slide, container, false);
        return v;
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.RGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.radioButtonM) {
                    IntroDataSharedPreference.setUserGender(getContext(),1);
                }
                if(checkedId == R.id.radioButtonW) {
                    IntroDataSharedPreference.setUserGender(getContext(),2);
                }

            }

        });
    }
}
