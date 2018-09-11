package com.example.calorycountapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HistoryFragment extends Fragment implements MvpView {


    HistoryFragmentPresenter presenter;
    private StackedBarChart mStackedBarChart;
    private TextView message;
    private LinearLayout layout;
    private TextView mediumCalory;
    private TextView userLimit;
    private int limit;

    @Override
    public void initPresenter() {
        presenter = new HistoryFragmentPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady("");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initView(View v) {
        mStackedBarChart = (StackedBarChart) v.findViewById(R.id.stackedbarchart);
        mStackedBarChart.setShowSeparators(false);
        mStackedBarChart.setScrollEnabled(true);
        mediumCalory = (TextView) v.findViewById(R.id.medium_calory);
        userLimit = (TextView) v.findViewById(R.id.limit);
        layout = (LinearLayout) v.findViewById(R.id.charts);
        layout.setVisibility(View.INVISIBLE);
    }

    public void showData(Map<String, Integer> map) {
        layout.setVisibility(View.VISIBLE);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            StackedBarModel s1 = new StackedBarModel(entry.getKey());
            limit = NumberCaloryPreferences.getLimitCalory(getContext());
            if (entry.getValue() != 0) {
                if (entry.getValue() <= limit) {
                    s1.addBar(new BarModel(entry.getValue(), 0xFF1E90FF));
                    s1.addBar(new BarModel(limit, 0xFF32CD32));
                }

                if (entry.getValue() > limit) {
                    s1.addBar(new BarModel(limit, 0xFF32CD32));
                    s1.addBar(new BarModel(entry.getValue(), 0xFFFF0000));
                }
            } else s1.addBar(new BarModel(0, 0xFFfcefc7));
            mStackedBarChart.addBar(s1);
        }
        mStackedBarChart.setShowValues(true);
        mStackedBarChart.startAnimation();
        presenter.countingMediumValue();
    }

    public void showMediumValue(int value) {
        mediumCalory.setText(String.valueOf(value));
    }


    @Override
    public void initToolbar() {

    }

    @Override
    public void initToolbar(View v) {

    }

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(int position) {
        HistoryFragment f = new HistoryFragment();
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View testView = inflater.inflate(R.layout.history_fragment, container, false);

        initView(testView);
        initPresenter();

        return testView;
    }

    //public void showEmptyMessage() {
    //    message.setVisibility(View.VISIBLE);
    //    message.setText("Здесь пока пусто!");
    //}


    public void showLimit(int value) {
        userLimit.setText(String.valueOf(value));
    }


}
