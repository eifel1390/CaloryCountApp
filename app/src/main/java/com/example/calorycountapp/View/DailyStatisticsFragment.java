package com.example.calorycountapp.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calorycountapp.Adapter.DailyStatisticsAdapter;
import com.example.calorycountapp.Adapter.OnRecyclerObjectClickListener;
import com.example.calorycountapp.ItemTouchHelperClass;
import com.example.calorycountapp.Model.Entity;
import com.example.calorycountapp.Presenter.DailyStatisticsPresenter;
import com.example.calorycountapp.R;

import java.util.List;

public class DailyStatisticsFragment extends Fragment implements CategoryView,OnRecyclerObjectClickListener<Entity> {

    private DailyStatisticsPresenter presenter;
    private RecyclerView recyclerView;
    private DailyStatisticsAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private MainActivity mainActivity;
    private CoordinatorLayout coordinatorLayout;

    public DailyStatisticsFragment() {}


    public static DailyStatisticsFragment newInstance(int position) {
        return new DailyStatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dailystatistics_list, container, false);
        initView(v);
        initPresenter();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainActivity = (MainActivity) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Override
    public void initView(View v) {
        coordinatorLayout = v.findViewById(R.id.myCoordinatorLayout);
        recyclerView =  v.findViewById(R.id.list_of_consumed_and_accrued);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new DailyStatisticsAdapter(this.getContext(),this,coordinatorLayout);
        adapter.setListener(this);
        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initPresenter() {
        presenter = new DailyStatisticsPresenter(this);
        presenter.attachView(this);
        presenter.viewIsReady("");
    }

    public interface passCaloryNumber {
        void sendCalory(int calory);
    }

    public void restartTemporaryList(){
        presenter.showDataInView();
    }

    @Override
    public void showData(List<Entity> list) {
        adapter.setItems(list);
    }

    public void passValue(int calory){
        mainActivity.sendCalory(calory);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onItemClicked(Entity item) {}

    @Override
    public void initToolbar() {}

    @Override
    public void initToolbar(View v) {}

    @Override
    public void initView() {}
}
