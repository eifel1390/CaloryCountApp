package com.example.calorycountapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.astuetz.PagerSlidingTabStrip;
import com.example.calorycountapp.Adapter.TabsAdapter;
import com.example.calorycountapp.LocaleHelper;
import com.example.calorycountapp.Presenter.MainActivityPresenter;
import com.example.calorycountapp.R;
import com.example.calorycountapp.View.MainFragment;

public class MainActivity extends AppCompatActivity implements MvpView, DailyStatisticsFragment.passCaloryNumber {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MainActivityPresenter presenter;
    SharedPreferences sp;

    public MainActivity() {}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        initView();
        initToolbar();
        initPresenter();
    }

    protected void onResume() {
        String listValue = sp.getString("languages_list", "язык не выбран");
        if(listValue.equals("1")){
            Log.d("languageTest",listValue);
            LocaleHelper.setLocale(getApplication(), "en");
        }
        if(listValue.equals("2")){
            Log.d("languageTest",listValue);
            LocaleHelper.setLocale(getApplication(), "ru");
        }
        super.onResume();
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

    /**инициализируем ViewPager и Tabs */
    @Override
    public void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new TabsAdapter(getSupportFragmentManager(),this));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }

    /**инициализируем тулбар */
    @Override
    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    /**инициализируем презентер */
    @Override
    public void initPresenter() {
        presenter = new MainActivityPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady("");
    }

    /**создание меню */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**обработка нажатий на меню */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                presenter.displayAnotherScreen("","");
                return true;

            case  R.id.test:
                presenter.testMethod();
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
