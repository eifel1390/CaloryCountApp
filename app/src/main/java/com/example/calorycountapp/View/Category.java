package com.example.calorycountapp.View;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.calorycountapp.Adapter.CategoryAdapter;
import com.example.calorycountapp.Adapter.OnRecyclerObjectClickListener;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.Presenter.CategoryPresenter;
import com.example.calorycountapp.R;

import java.util.List;

public class Category extends AppCompatActivity implements CategoryView,OnRecyclerObjectClickListener<Entity> {

    public static final String ENTITY_IDENT = "entity_ident";
    private final String DIALOG_WEIGHT = "dialog_weight";

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private CategoryPresenter presenter;
    private String ident;
    private Dialog dialog;
    private Button okButton;
    private EditText weightEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        ident =getIntent().getStringExtra(ENTITY_IDENT);
        initView();
        initToolbar();
        initPresenter();
    }

    @Override
    public void initToolbar() {
        Toolbar productCategoryToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(productCategoryToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null) ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {
        presenter = new CategoryPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady(ident);
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.categoryRecyclerView);
        adapter = new CategoryAdapter(this);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClicked(Entity item) {
        if(item instanceof Product) {
            presenter.displayAnotherScreen(((Product) item).getCategoryProduct(), EntityIdent.IS_PRODUCT);
        }
        else if(item instanceof Active) {
            presenter.displayAnotherScreen(((Active) item).getCategoryActive(), EntityIdent.IS_ACTIVE);
        }
    }

    @Override
    public void showData(List<Entity> list) {
        adapter.setItems(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void initView(View v) {}

    @Override
    public void initToolbar(View v) {}

    public void prepareWeightDialog(){
        dialog = new Dialog(this);

        dialog.setContentView(R.layout.weight_dialog);
        dialog.setTitle("Укажите Ваш вес");

        Log.d("3490",String.valueOf(dialog==null));

        okButton = (Button) dialog.findViewById(R.id.setWeightButton);
        Log.d("3490","button "+String.valueOf(okButton==null));
        weightEditText = (EditText) dialog.findViewById(R.id.editTextWeight);
        Log.d("3490","edittext "+String.valueOf(weightEditText==null));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberCaloryPreferences.setUserWeight(Category.this,weightEditText.getText().toString());
            }
        });

        dialog.show();

    }


}


