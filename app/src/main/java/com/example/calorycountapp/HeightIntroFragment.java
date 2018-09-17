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


public class HeightIntroFragment extends Fragment {

    private EditText heightInput;

    public HeightIntroFragment() {}

    public static HeightIntroFragment newInstance() {
        HeightIntroFragment f = new HeightIntroFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.height_slide, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        heightInput = (EditText) getView().findViewById(R.id.editTextHeight);

        heightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    if(Integer.parseInt(charSequence.toString())!=0) {
                        IntroDataSharedPreference.setUserHeight(getContext(), Integer.parseInt(charSequence.toString()));
                    }
                    else Toast.makeText(getContext(),"Рост не может быть 0!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if(Integer.parseInt(editable.toString()) == 0)
                        heightInput.setText("");
                }
                catch(NumberFormatException nfe){}
            }
        });
    }
}

