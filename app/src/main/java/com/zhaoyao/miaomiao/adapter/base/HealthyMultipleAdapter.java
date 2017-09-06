package com.zhaoyao.miaomiao.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多布局adapter
 * Created by liuwei on 2017/6/8 10:32
 */

public abstract class HealthyMultipleAdapter<T> extends RecyclerView.Adapter<HealthyMultipleViewHolder<T>> {

    public List<T> mDatas;

    public HealthyMultipleAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public HealthyMultipleViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        final HealthyMultipleViewHolder<T> holder = onCreateHolder(parent, viewType);
        if(mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v,mDatas.get(holder.getAdapterPosition()),holder.getAdapterPosition());
                }
            });
        }
        if(mItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.OnItemLongClick(v,mDatas.get(holder.getAdapterPosition()),holder.getAdapterPosition());
                    return false;
                }
            });
        }

        return holder;
    }

    public abstract HealthyMultipleViewHolder<T> onCreateHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(HealthyMultipleViewHolder holder, int position) {
        onBindHolder(holder,mDatas.get(position),position,getItemViewType(position));
    }

    public abstract void onBindHolder(HealthyMultipleViewHolder holder, T item, int position, int viewType);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
    public int getCount(){
        return getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mDatas.size())return -1;
        return getItemViewType(position,mDatas.get(position));
    }

    public int getItemViewType(int position,T item){
        return 0;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, T item, int position);
    }
    public interface OnItemLongClickListener<T> {
        void OnItemLongClick(View view, T item, int position);
    }

    private OnItemClickListener<T> mItemClickListener;
    /**
     * 设置Item点击监听
     * @param listener dd
     */
    public void setOnItemClickListener(OnItemClickListener<T> listener){
        this.mItemClickListener = listener;

    }

    private OnItemLongClickListener<T> mItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener){
        this.mItemLongClickListener = listener;
    }
}
