package com.example.slab.loader.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class SlabAdapter<T> extends RecyclerView.Adapter {
    /**
     *
     * @param <VH> the type of the view holder
     * @param <T> the type on Adapter's item model
     */
    public interface ArrayAdapterDelegate<VH extends ViewHolder, T> {
        VH onCreateViewHolder(SlabAdapter<T> adapter, ViewGroup parent, int viewType);
        void onBindViewHolder(SlabAdapter<T> adapter, ViewHolder holder, int position);
    }
    private static final String TAG = "SlabAdapter";
    List<T> mList;
    ArrayAdapterDelegate<? extends ViewHolder, T> delegate;


    public SlabAdapter(ArrayAdapterDelegate<? extends ViewHolder, T> delegate) {
        this.delegate = delegate;
        this.mList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegate.onCreateViewHolder(this, parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        delegate.onBindViewHolder(this, holder, position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public final void setDataSet(List<? extends T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public final void clearDataSet() {
        notifyItemRangeRemoved(0, mList.size());
        mList.clear();
    }

    public final T getItem(int position) {
        return mList.get(position);
    }

    public final void addItem(T object) {
        mList.add(object);
        notifyItemInserted(mList.size() - 1);
    }

    public final void addItem(T object, int position) {
        mList.add(position, object);
        notifyItemInserted(position);
    }

    public final void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }


}