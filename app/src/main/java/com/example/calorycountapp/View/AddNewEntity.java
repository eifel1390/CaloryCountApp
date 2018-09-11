package com.example.calorycountapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calorycountapp.R;


public class AddNewEntity extends AppCompatActivity implements MvpView {

    public static final String ENTITY_CATEGORY = "category";
    public static final String ENTITY_TYPE = "entityType";

    private EditText addEntityName, addEntityCaloricity;
    private Button addNewEntity, cancel;
    private AddNewEntityPresenter presenter;
    private String entityCategory, entityType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_new_entity);
        Intent intent = getIntent();
        entityCategory = intent.getStringExtra(ENTITY_CATEGORY);
        entityType = intent.getStringExtra(ENTITY_TYPE);
        initView();
        initPresenter();
    }

    @Override
    public void initPresenter() {
        presenter = new AddNewEntityPresenter(this);
        presenter.attachView(this);
    }

    //возврат на CategoryDetail
    public void returnToPreviouslyActivity(){
        Intent intent = new Intent(this,CategoryDetail.class);
        intent.putExtra(CategoryDetail.CATEGORY_DETAIL,entityCategory);
        intent.putExtra(CategoryDetail.ENTITY_IDENT,entityType);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        addEntityName = (EditText) findViewById(R.id.addNameProduct);
        addEntityCaloricity = (EditText) findViewById(R.id.addNameCaloricity);
        addNewEntity = (Button) findViewById(R.id.saveProduct);
        cancel = (Button) findViewById(R.id.cancelProduct);
        addNewEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addEntityName.getText().toString().equals("")
                        ||addEntityCaloricity.getText().toString().equals("")){
                    showErrorMessage();
                }
                else {
                    presenter.saveEntityToDatabase(entityCategory,addEntityName.getText().toString()
                            , addEntityCaloricity.getText().toString(),entityType);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPreviouslyActivity();
            }
        });
    }

    public void showErrorMessage(){
        Toast.makeText(this,R.string.fill_all_fields_message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void initToolbar() {}
}


