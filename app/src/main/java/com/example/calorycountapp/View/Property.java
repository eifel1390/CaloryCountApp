package com.example.calorycountapp.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.Presenter.PropertyPresenter;
import com.example.calorycountapp.R;


public class Property extends AppCompatActivity implements MvpView,View.OnClickListener {

    public static final String ENTITY_NAME = "product_name";
    public static final String ENTITY_TYPE = "entity_type";
    public static final String CONSUMPTION_VALUE = "consumption_value";

    public String entityName, entityType;
    private TextView product,spendsContains,productCaloryInHungred,consumptionCountGr,infoText;
    private Button plusTwenty,plusHungred,minusTwenty,minusHungred,saveinSharedPreference;
    private PropertyPresenter presenter;
    private int consumption, caloryInHungred;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entity_property);
        Intent intent = getIntent();
        entityName = intent.getStringExtra(ENTITY_NAME);
        entityType = intent.getStringExtra(ENTITY_TYPE);
        if(savedInstanceState!=null){
            consumption = savedInstanceState.getInt(CONSUMPTION_VALUE);
        }
        initView();
        initPresenter();
    }

    @Override
    public void initView() {
        product = (TextView) findViewById(R.id.productName);
        spendsContains = (TextView) findViewById(R.id.spendsContains);
        infoText = (TextView) findViewById(R.id.infoText);
        productCaloryInHungred = (TextView) findViewById(R.id.numberCaloryInProduct);
        consumptionCountGr = (TextView) findViewById(R.id.completeCaloryNubmer);
        plusTwenty = (Button) findViewById(R.id.plusTwenty);
        plusTwenty.setOnClickListener(this);
        minusTwenty = (Button)findViewById(R.id.minusTwenty);
        minusTwenty.setOnClickListener(this);
        plusHungred = (Button) findViewById(R.id.plusHungred);
        plusHungred.setOnClickListener(this);
        minusHungred =(Button) findViewById(R.id.minusHungred);
        minusHungred.setOnClickListener(this);
        saveinSharedPreference = (Button) findViewById(R.id.saveToSharedPreference);
        saveinSharedPreference.setOnClickListener(this);
        product.setText(entityName);
        consumptionCountGr.setText(String.valueOf(consumption));
        if(entityType.equals(EntityIdent.IS_PRODUCT)){
            spendsContains.setText(R.string.containsInOneHungred);
            plusTwenty.setText(R.string.plusTwentyGramm);
            plusHungred.setText(R.string.plusOneHungredGramm);
            minusTwenty.setText(R.string.minusTwentyGramm);
            minusHungred.setText(R.string.minusOneHungredGramm);
        }
        else if(entityType.equals(EntityIdent.IS_ACTIVE)){
            spendsContains.setText(R.string.costInSexteenMinute);
            plusTwenty.setText(R.string.plusFiveMinute);
            plusHungred.setText(R.string.plusHalfHour);
            minusTwenty.setText(R.string.minusFiveMinute);
            minusHungred.setText(R.string.minusHalfHour);
        }
    }

    @Override
    public void initPresenter() {
        presenter = new PropertyPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady(entityType);
    }

    public void setProductCalory(int value){
        productCaloryInHungred.setText(String.valueOf(value));
        caloryInHungred = value;
    }

    public void showInfoText(int consumption,int caloryInHungred){
        if(entityType.equals(EntityIdent.IS_PRODUCT)) {
            int value = (int) ((caloryInHungred * consumption) / 100.0f);
            infoText.setText("Будет начислено "+value+" калорий");
        }
        if(entityType.equals(EntityIdent.IS_ACTIVE)) {
            int value = (int) ((caloryInHungred * consumption) / 60.0f);
            infoText.setText("Будет списано "+value+" калорий");
        }

    }

    //TODO нельзя уходить в минус при выборе грамм продукта или минут активности.То есть нельзя чтобы юзер выбрал помидор -20 грамм.
    @Override
    public void onClick(View v) {
        if(entityType.equals(EntityIdent.IS_PRODUCT)) {
            switch (v.getId()) {
                case R.id.plusTwenty:
                    consumption += 20;
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.plusHungred:
                    consumption += 100;
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusTwenty:
                    if(consumption>0) {
                        consumption -= 20;
                    }
                    else if(consumption<=0){
                        consumption = 0;
                    }
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusHungred:
                    if(consumption>0){
                        consumption -= 100;
                    }
                    else if(consumption<=0) {
                        consumption = 0;
                    }
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.saveToSharedPreference:
                    if(consumption>0) {
                        calculateValue(entityName, consumption, caloryInHungred, entityType);
                    }
                    else returnToPreviouslyActivity();
            }
        }
        else if(entityType.equals(EntityIdent.IS_ACTIVE)){
            switch (v.getId()) {
                case R.id.plusTwenty:
                    consumption += 5;
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.plusHungred:
                    consumption += 30;
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusTwenty:
                    if(consumption>0) {
                        consumption -= 5;
                    }
                    else if(consumption<=0){
                        consumption = 0;
                    }
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusHungred:
                    if(consumption>0) {
                        consumption -= 30;
                    }
                    else if(consumption<=0){
                        consumption = 0;
                    }
                    consumptionCountGr.setText(String.valueOf(consumption));
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.saveToSharedPreference:
                    if(consumption>0) {
                        calculateValue(entityName, consumption, caloryInHungred, entityType);
                    }
                    else returnToPreviouslyActivity();
            }
        }
    }

    private int notBelowZero(int consumption){
        int result = 0;
        if(consumption<0){
            result = 0;
        }
        else result = consumption;
        return result;
    }

    public void calculateValue(String productName,int consumption,int caloryInHungred,String entityType){

        if(entityType.equals(EntityIdent.IS_PRODUCT)) {
            int value = (int) ((caloryInHungred * consumption) / 100.0f);
            presenter.getValueToPreference(value,entityType);
            presenter.addEntityToTemporaryTable(productName,value,entityType);
        }

        else if(entityType.equals(EntityIdent.IS_ACTIVE)) {
            int value = (int) ((caloryInHungred * consumption) / 60.0f);
            presenter.getValueToPreference(value,entityType);
            presenter.addEntityToTemporaryTable(productName,value,entityType);
        }
    }

    public void returnToPreviouslyActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CONSUMPTION_VALUE,consumption);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void initView(View v) {

    }

    @Override
    public void initToolbar(View v) {

    }

    @Override
    public void initToolbar() {}
}