package com.example.calorycountapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.calorycountapp.View.DailyStatisticsFragment;
import com.example.calorycountapp.View.HistoryFragment;
import com.example.calorycountapp.View.MainFragment;


public class TabsAdapter extends FragmentPagerAdapter {

    private Context context;

    String main;

    private String[] titles = { "Главная",
            "Сегодня","Моя история"  };

    public TabsAdapter(FragmentManager fm,Context c) {
        super(fm);
        context = c;
    }

    //возвращает фрагмент, связанный с указанной позицией.
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:{
                return MainFragment.newInstance(position);
            }
            case 1:{
                return DailyStatisticsFragment.newInstance(position);
            }
            case 2:{
                return HistoryFragment.newInstance(position);
            }
        }
        return null;
    }


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
