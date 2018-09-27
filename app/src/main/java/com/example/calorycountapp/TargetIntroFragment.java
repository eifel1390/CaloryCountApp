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


public class TargetIntroFragment extends Fragment {

    public TargetIntroFragment() {}

    public static TargetIntroFragment newInstance() {
        return new TargetIntroFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.target_slide, container, false);
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            RadioGroup radioGroup = view.findViewById(R.id.RGroup);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (checkedId == R.id.looseWeight) {
                        IntroDataSharedPreference.setUserTarget(getContext(), 1);
                    }
                    if (checkedId == R.id.saveWeight) {
                        IntroDataSharedPreference.setUserTarget(getContext(), 2);
                    }
                    if (checkedId == R.id.takeWeight) {
                        IntroDataSharedPreference.setUserTarget(getContext(), 2);
                    }
                }
            });
        }
    }

}

