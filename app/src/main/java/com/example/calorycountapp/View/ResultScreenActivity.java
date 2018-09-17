package com.example.calorycountapp.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.calorycountapp.IntroActivity;
import com.example.calorycountapp.PrefManager;
import com.example.calorycountapp.Presenter.ResultScreenPresenter;
import com.example.calorycountapp.R;


public class ResultScreenActivity extends AppCompatActivity implements MvpView {

    private Button okButton;
    private TextView limit;
    private PrefManager prefManager;
    private ResultScreenPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.result_slide);
        prefManager = new PrefManager(this);
        initView();
        initPresenter();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initPresenter() {
        presenter = new ResultScreenPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady("");
    }

    @Override
    public void initView() {
        okButton =  findViewById(R.id.okButton);
        limit =  findViewById(R.id.caloryUserLimit);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setFirstTimeLaunch(false);
                Intent intent = new Intent(ResultScreenActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showDailyLimit(int calory){
        limit.setText(String.valueOf(calory));
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
    public void initToolbar() {

    }

    @Override
    public void initToolbar(View v) {

    }
}
