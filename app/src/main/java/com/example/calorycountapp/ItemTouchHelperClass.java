package com.example.calorycountapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class ItemTouchHelperClass extends ItemTouchHelper.Callback {

    private static final String TAG = "item";

    private ItemTouchHelperAdapter adapter;

    public interface ItemTouchHelperAdapter{
        void onItemMoved(int fromPosition, int toPosition);
        void onItemRemoved(int position);
    }

    public ItemTouchHelperClass(ItemTouchHelperAdapter ad){
        adapter = ad;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int upFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(upFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG,String.valueOf(adapter==null));
        adapter.onItemRemoved(viewHolder.getAdapterPosition());

    }

}
