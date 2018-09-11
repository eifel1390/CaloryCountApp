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

public class DailyStatisticsFragment extends Fragment implements CategoryView,OnRecyclerObjectClickListener<Entity> {

    private DailyStatisticsPresenter presenter;
    private RecyclerView recyclerView;
    private DailyStatisticsAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private MainActivity mainActivity;
    private CoordinatorLayout coordinatorLayout;

    public DailyStatisticsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static DailyStatisticsFragment newInstance(int position) {
        DailyStatisticsFragment f = new DailyStatisticsFragment();
        Bundle b = new Bundle();
        return f;
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
        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.myCoordinatorLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.list_of_consumed_and_accrued);
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
