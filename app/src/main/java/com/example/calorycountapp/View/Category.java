package com.example.calorycountapp.View;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Category extends AppCompatActivity implements CategoryView,OnRecyclerObjectClickListener<Entity> {

    public static final String ENTITY_IDENT = "entity_ident";

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private CategoryPresenter presenter;
    private String ident;

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
}


