package com.example.calorycountapp.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private String textMinuteOrGramm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entity_property2);
        Intent intent = getIntent();
        entityName = intent.getStringExtra(ENTITY_NAME);
        entityType = intent.getStringExtra(ENTITY_TYPE);

        if(entityType.equals(EntityIdent.IS_PRODUCT)){
            textMinuteOrGramm = getString(R.string.gramm);
        }
        if(entityType.equals(EntityIdent.IS_ACTIVE)){
            textMinuteOrGramm = getString(R.string.minute);
        }
        if(savedInstanceState!=null){
            consumption = savedInstanceState.getInt(CONSUMPTION_VALUE);
        }
        initView();
        initPresenter();
    }

    @Override
    public void initView() {
        product =  findViewById(R.id.productName);
        spendsContains =  findViewById(R.id.spendsContains);
        infoText =  findViewById(R.id.infoText);
        productCaloryInHungred =  findViewById(R.id.numberCaloryInProduct);
        consumptionCountGr =  findViewById(R.id.completeCaloryNubmer);
        plusTwenty =  findViewById(R.id.plusTwenty);
        plusTwenty.setOnClickListener(this);
        minusTwenty = findViewById(R.id.minusTwenty);
        minusTwenty.setOnClickListener(this);
        plusHungred =  findViewById(R.id.plusHungred);
        plusHungred.setOnClickListener(this);
        minusHungred = findViewById(R.id.minusHungred);
        minusHungred.setOnClickListener(this);
        saveinSharedPreference =  findViewById(R.id.saveToSharedPreference);
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
        productCaloryInHungred.setText(String.valueOf(value)+" "+getString(R.string.calories));
        caloryInHungred = value;
    }

    public void showInfoText(int consumption,int caloryInHungred){
        if(entityType.equals(EntityIdent.IS_PRODUCT)) {
            int value = (int) ((caloryInHungred * consumption) / 100.0f);
            consumptionCountGr.setText(String.valueOf(value));
        }
        if(entityType.equals(EntityIdent.IS_ACTIVE)) {
            int value = (int) ((caloryInHungred * consumption) / 60.0f);
            consumptionCountGr.setText(String.valueOf(value));
        }

    }


    @Override
    public void onClick(View v) {
        if(entityType.equals(EntityIdent.IS_PRODUCT)) {
            switch (v.getId()) {
                case R.id.plusTwenty:
                    consumption += 20;
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.plusHungred:
                    consumption += 100;
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusTwenty:
                    if(consumption>0&&consumption>=20) {
                        consumption -= 20;
                    }
                    else if(consumption<=0){
                        consumption = 0;
                    }
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusHungred:
                    if(consumption>0&&consumption>=100){
                        consumption -= 100;
                    }
                    else if(consumption<=0) {
                        consumption = 0;
                    }
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
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
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.plusHungred:
                    consumption += 30;
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusTwenty:
                    if(consumption>0&&consumption>=5) {
                        consumption -= 5;
                    }
                    else if(consumption<=0){
                        consumption = 0;
                    }
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
                    showInfoText(consumption,caloryInHungred);
                    break;
                case R.id.minusHungred:
                    if(consumption>0&&consumption>=30) {
                        consumption -= 30;
                    }
                    else if(consumption<=0){
                        consumption = 0;
                    }
                    infoText.setText(getString(R.string.choosen)+" "+String.valueOf(consumption)+" "+textMinuteOrGramm);
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


    public void calculateValue(String productName,int consumption,int caloryInHungred,String entityType){

        if(entityType.equals(EntityIdent.IS_PRODUCT)) {
            int value = (int) ((caloryInHungred * consumption) / 100.0f);
            presenter.getValueToPreference(value,entityType);
            presenter.addEntityToTemporaryTable(productName,value,entityType,consumption);
        }

        else if(entityType.equals(EntityIdent.IS_ACTIVE)) {
            int value = (int) ((caloryInHungred * consumption) / 60.0f);
            presenter.getValueToPreference(value,entityType);
            presenter.addEntityToTemporaryTable(productName,value,entityType,consumption);
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