package com.example.calorycountapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.astuetz.PagerSlidingTabStrip;
import com.example.calorycountapp.Adapter.TabsAdapter;
import com.example.calorycountapp.Presenter.MainActivityPresenter;
import com.example.calorycountapp.R;

public class MainActivity extends AppCompatActivity implements MvpView, DailyStatisticsFragment.passCaloryNumber {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MainActivityPresenter presenter;
    SharedPreferences sp;

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        initView();
        initToolbar();
        initPresenter();
    }

    @Override
    public void sendCalory(int calory) {
        String tag = "android:switcher:" + R.id.pager + ":" + 0;
        MainFragment f = (MainFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(calory);
    }


    public void recreateFragment(){
        String tag = "android:switcher:" + R.id.pager + ":" + 1;
        DailyStatisticsFragment f = (DailyStatisticsFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.restartTemporaryList();
    }

    @Override
    public void initView() {
        pager =  findViewById(R.id.pager);
        pager.setAdapter(new TabsAdapter(getSupportFragmentManager(),this));
        pager.setCurrentItem(0);
        tabs =  findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }


    @Override
    public void initToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
    }


    @Override
    public void initPresenter() {
        presenter = new MainActivityPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                presenter.displayAnotherScreen("","");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void initToolbar(View v) {}

    @Override
    public void initView(View v) {}
}
