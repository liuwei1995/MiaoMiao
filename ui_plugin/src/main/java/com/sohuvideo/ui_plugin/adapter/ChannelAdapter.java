package com.sohuvideo.ui_plugin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sohuvideo.ui_plugin.holder.ItemColumnListModelHolder;
import com.sohuvideo.ui_plugin.holder.ItemListSeparator;
import com.sohuvideo.ui_plugin.holder.ItemModelHolder;
import com.sohuvideo.ui_plugin.holder.ItemTitleModelHoler;
import com.sohuvideo.ui_plugin.listener.OnLoadMoreClickListener;
import com.sohuvideo.ui_plugin.model.ItemModel;

import java.util.ArrayList;
import java.util.List;


public class ChannelAdapter extends BaseAdapter {

    private static final String TAG = ChannelAdapter.class.getSimpleName();

    private Context mContext;
    private List<ItemModel> mItemModelList;
    private OnLoadMoreClickListener moreClickListener;
    public ChannelAdapter(Context context, OnLoadMoreClickListener moreClickListener) {
        this.mContext = context;
        this.moreClickListener = moreClickListener;
        mItemModelList = new ArrayList<ItemModel>();
    }

    @Override
    public int getCount() {
        return mItemModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        ItemModelHolder holder = null;
        ItemModelHolder.BaseViewHolder mBaseViewHolder = null;
        if (convertView == null) {
            switch (viewType) {
                case ItemModel.ITEM_TYPE_NORMAL_TITLE:
                    holder = new ItemTitleModelHoler();
                    break;
                case ItemModel.ITEM_TYPE_NORMAL_VIDEO_LIST:
                    holder = new ItemColumnListModelHolder();
                    break;
                case ItemModel.ITEM_TYPE_VIDEO_LIST_SEPARATOR:
                    holder = new ItemListSeparator();
                    break;
            }
            mBaseViewHolder = holder.getHolder();
            mBaseViewHolder.viewType = viewType;
            convertView = holder.initView(mContext, mBaseViewHolder,moreClickListener);
        } else {
            mBaseViewHolder = (ItemModelHolder.BaseViewHolder) convertView.getTag();
            holder = mBaseViewHolder.getItemHolder();
        }

        if (mItemModelList.size() > position) {
            ItemModel model = mItemModelList.get(position);
            if (holder != null && model != null) {
                holder.initData(mContext, model, mBaseViewHolder);
            }
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return ItemModel.VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = ItemModel.ITEM_TYPE_NORMAL_TITLE;
        if (position < mItemModelList.size()) {
            ItemModel model = mItemModelList.get(position);
            viewType = model.getItemType();
        }
        return viewType;
    }

    /**
     * 刷新数据
     *
     * @param mItemModelList
     */
    public void updateList(List<ItemModel> mItemModelList) {
        if (this.mItemModelList != null) {
            this.mItemModelList.clear();
            this.mItemModelList.addAll(mItemModelList);
            notifyDataSetChanged();
        }
    }
}