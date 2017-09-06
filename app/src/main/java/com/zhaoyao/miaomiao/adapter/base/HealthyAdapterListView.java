package com.zhaoyao.miaomiao.adapter.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 
 * @author liuWei
 *
 * @param <T>
 */
public  abstract class HealthyAdapterListView<T> extends BaseAdapter
{
	private List<T> mDatas;
	private final int mItemLayoutId;

	protected HealthyAdapterListView(List<T> mDatas, @LayoutRes int itemLayoutId)
	{
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount()
	{
		return mDatas==null?0:mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final HealthyViewHolderListView viewHolder = getViewHolder(position, convertView,
				parent);
		convert(viewHolder, getItem(position),position);
		return viewHolder.getConvertView();
	}

	public abstract  void convert(HealthyViewHolderListView holder, T item, int position) ;

	private HealthyViewHolderListView getViewHolder(int position, View convertView,
													ViewGroup parent)
	{
		return HealthyViewHolderListView.get(parent.getContext(), convertView, parent, mItemLayoutId,
				position);
	}

}
