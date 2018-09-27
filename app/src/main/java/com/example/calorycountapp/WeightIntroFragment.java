package com.example.calorycountapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calorycountapp.Database.IntroDataSharedPreference;

public class WeightIntroFragment extends Fragment {

    private EditText weightInput;

    public WeightIntroFragment() {}

    public static WeightIntroFragment newInstance() {
        return new WeightIntroFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weight_slide, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weightInput =  getView().findViewById(R.id.editTextWeight);

        weightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    if(Integer.parseInt(charSequence.toString())!=0) {
                        IntroDataSharedPreference.setUserWeight(getContext(), Integer.parseInt(charSequence.toString()));
                    }
                    else Toast.makeText(getContext(),R.string.correct_weight, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if(Integer.parseInt(editable.toString()) == 0)
                        weightInput.setText("");
                }
                catch(NumberFormatException nfe){
                    nfe.printStackTrace();
                }
            }
        });
    }
}

