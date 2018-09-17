package com.example.calorycountapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calorycountapp.Database.IntroDataSharedPreference;


public class AgeIntroFragment extends Fragment {

    private EditText ageInput;

    public AgeIntroFragment() {}

    public static AgeIntroFragment newInstance() {
        AgeIntroFragment f = new AgeIntroFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.age_slide, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ageInput = (EditText) getView().findViewById(R.id.editTextAge);

        ageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    if(Integer.parseInt(charSequence.toString())!=0) {
                        IntroDataSharedPreference.setUserAge(getContext(), Integer.parseInt(charSequence.toString()));
                    }
                    else Toast.makeText(getContext(),"Возраст не может быть 0!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if(Integer.parseInt(editable.toString()) == 0)
                        ageInput.setText("");
                }
                catch(NumberFormatException nfe){}
            }
        });
    }

}
