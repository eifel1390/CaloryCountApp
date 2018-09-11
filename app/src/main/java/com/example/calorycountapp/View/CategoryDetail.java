package com.example.calorycountapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.calorycountapp.Adapter.CategoryDetailAdapter;
import com.example.calorycountapp.Adapter.OnRecyclerObjectClickListener;
import com.example.calorycountapp.EntityIdent;
import com.example.calorycountapp.Model.Active;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Model.Product;
import com.example.calorycountapp.Presenter.CategoryDetailPresenter;
import com.example.calorycountapp.R;

import java.util.List;

public class CategoryDetail extends AppCompatActivity implements CategoryView,OnRecyclerObjectClickListener<Entity> {

    public static final String CATEGORY_DETAIL = "category_detail";
    public static final String ENTITY_IDENT = "entity_ident";

    private RecyclerView recyclerView;
    private CategoryDetailAdapter adapter;
    private CategoryDetailPresenter presenter;
    private String category, entityIdent;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_detail_activity);
        Intent intent = getIntent();
        category = intent.getStringExtra(CATEGORY_DETAIL);
        entityIdent= intent.getStringExtra(ENTITY_IDENT);
        initView();
        initToolbar();
        initPresenter();
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.category_detail_RecyclerView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        adapter = new CategoryDetailAdapter(this);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //hyde floatbutton when user scrolling recyclerview
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0 && floatingActionButton.getVisibility()== View.VISIBLE){
                    floatingActionButton.hide();
                }
                else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE){
                    floatingActionButton.show();
                }

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.displayAnotherScreen(category,entityIdent);
            }
        });
    }

    @Override
    public void initToolbar() {
        Toolbar productCategoryToolbar =
                (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(productCategoryToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null) ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {
        presenter = new CategoryDetailPresenter(this);
        presenter.attachView(this);
        presenter.loadNameByCategory(category,entityIdent);
    }

    @Override
    public void onItemClicked(Entity item) {
        if(item instanceof Product) {
            presenter.displayPropertyActivity(((Product) item).getName(),
                    EntityIdent.IS_PRODUCT);
        }

        if(item instanceof Active) {
            presenter.displayPropertyActivity(((Active) item).getNameActive(),
                    EntityIdent.IS_ACTIVE);
        }
    }

    @Override
    public void showData(List<Entity> list) {
        adapter.setItems(list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,Category.class);
                intent.putExtra(Category.ENTITY_IDENT,entityIdent);
                startActivity(intent);
                break;
        }
        return true;
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

