package com.example.calorycountapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorycountapp.Database.IntroDataSharedPreference;
import com.example.calorycountapp.View.MainActivity;
import com.example.calorycountapp.View.ResultScreenActivity;

import java.util.ArrayList;
import java.util.List;


public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroActivity.MyFragmentPagerAdapter myViewPagerAdapter;
    private PrefManager prefManager;
    private Button btnNext;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private List<Fragment> fragmentsList;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.intro_activity);

        viewPager =  findViewById(R.id.view_pager);
        dotsLayout =  findViewById(R.id.layoutDots);
        btnNext =  findViewById(R.id.btn_next);
        manager = getSupportFragmentManager();


        fragmentsList = new ArrayList<>();
        populateFragmentsList(fragmentsList);

        addBottomDots(0);

        changeStatusBarColor();

        myViewPagerAdapter = new IntroActivity.MyFragmentPagerAdapter(manager);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = getItem(+1);
                if (current < fragmentsList.size()) {

                    viewPager.setCurrentItem(current);
                }

                else {
                    if(checkedField()) {
                        launchResultScreen();
                    }
                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[fragmentsList.size()];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void launchResultScreen(){
        Intent intent = new Intent(IntroActivity.this, ResultScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void populateFragmentsList(List<Fragment>fragmentsList){
        fragmentsList.add(WelcomeIntroFragment.newInstance());
        fragmentsList.add(GenderIntroFragment.newInstance());
        fragmentsList.add(AgeIntroFragment.newInstance());
        fragmentsList.add(WeightIntroFragment.newInstance());
        fragmentsList.add(HeightIntroFragment.newInstance());
        fragmentsList.add(TargetIntroFragment.newInstance());

    }

    private boolean checkedField(){
        boolean result = true;
        StringBuilder checkBuilder = new StringBuilder();
        if(IntroDataSharedPreference.getUserAge(IntroActivity.this)==0){
            checkBuilder.append("Вы забыли указать возраст!");
            checkBuilder.append("\n");
            result = false;
        }
        if(IntroDataSharedPreference.getUserWeight(IntroActivity.this)==0){
            checkBuilder.append("Вы забыли указать вес!");
            checkBuilder.append("\n");
            result = false;
        }
        if(IntroDataSharedPreference.getUserGender(IntroActivity.this)==0){
            checkBuilder.append("Вы забыли указать пол!");
            checkBuilder.append("\n");
            result = false;
        }
        if(IntroDataSharedPreference.getUserHeight(IntroActivity.this)==0){
            checkBuilder.append("Вы забыли указать рост!");
            checkBuilder.append("\n");
            result = false;
        }
        if(IntroDataSharedPreference.getUserTarget(IntroActivity.this)==0){
            checkBuilder.append("Вы забыли указать цель!");
            checkBuilder.append("\n");
            result = false;
        }
        if(checkBuilder.toString().length()>0) {
            Toast.makeText(IntroActivity.this, checkBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
        return result;
    }



    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == fragmentsList.size() - 1) {
                btnNext.setText(getString(R.string.start));
            } else {
                btnNext.setText(getString(R.string.next));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}

