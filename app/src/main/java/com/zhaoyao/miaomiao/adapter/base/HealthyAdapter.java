package com.zhaoyao.miaomiao.adapter.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liuwei on 2016/7/19
 */
public abstract class HealthyAdapter<T> extends RecyclerView.Adapter<HealthyViewHolder> {


    protected List<T> mDatas;

    private final int mItemLayoutId;

    public HealthyAdapter(List<T> mDatas, @LayoutRes int itemLayoutId) {
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public HealthyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        final HealthyViewHolder holder = new HealthyViewHolder(view, viewType);
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

    @Override
    public void onBindViewHolder(final HealthyViewHolder holder,int position) {
        convert(holder,mDatas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public  abstract void convert(HealthyViewHolder holder, T item, int position);

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
