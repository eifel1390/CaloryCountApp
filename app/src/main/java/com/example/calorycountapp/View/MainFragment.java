package com.example.calorycountapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.Presenter.MainFragmentPresenter;
import com.example.calorycountapp.R;

public class MainFragment extends Fragment implements MvpView, View.OnClickListener {

    private Button toProduct, toActive;
    private TextView caloryNumberPerDay;
    private MainFragmentPresenter presenter;
    private int calory;

    public MainFragment() {}

    public static MainFragment newInstance(int position) {
        MainFragment f = new MainFragment();
        return f;
    }

    //в момент создания инициализируем презентер
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    //построение фрагмента,здесь вызываем initview
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        initView(v);
        return v;
    }

    //инициализация всех вьюшек во фрагменте,а такде проверка префа с набранными калориями
    //за день и отображение этих калорий
    @Override
    public void initView(View v) {
        if(NumberCaloryPreferences.getStoredCalory(getContext())!=0) {
            calory = NumberCaloryPreferences.getStoredCalory(getContext());
        }
        toProduct = (Button) v.findViewById(R.id.plus);
        toProduct.setOnClickListener(this);
        toActive = (Button) v.findViewById(R.id.minus);
        toActive.setOnClickListener(this);
        caloryNumberPerDay = (TextView) v.findViewById(R.id.number_of_calories);
        caloryNumberPerDay.setText(String.valueOf(calory));
    }

    //инициализация презентера
    @Override
    public void initPresenter() {
        presenter = new MainFragmentPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady("");
    }

    //отображение количества калорий
    @Override
    public void onResume() {
        super.onResume();
        if(NumberCaloryPreferences.getStoredCalory(getContext())!=0) {
            calory = NumberCaloryPreferences.getStoredCalory(getContext());
            caloryNumberPerDay.setText(String.valueOf(calory));
        }
        if(NumberCaloryPreferences.getStoredCalory(getContext())==0) {
            caloryNumberPerDay.setText(String.valueOf(0));
        }
    }

    //реакция на нажатие кнопок + и -
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus:
                presenter.displayAnotherScreen(EntityIdent.IS_PRODUCT,"");
                break;
            case R.id.minus:
                presenter.displayAnotherScreen(EntityIdent.IS_ACTIVE,"");
                break;
        }
    }

    //отсоединяем фрагмент
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    //этот метод обновляет отображение количества калорий.Вызывается при изменении
    //данных во фрагменте "Сегодня".Если там пользователь удаляет продукт или активность
    //то соответственно нужно или уменьшить или увеличить количество калорий
    protected void displayReceivedData(int calory)
    {
        caloryNumberPerDay.setText(String.valueOf(calory));
    }

    @Override
    public void initToolbar(View v) {}

    @Override
    public void initView() {}

    @Override
    public void initToolbar() {}
}