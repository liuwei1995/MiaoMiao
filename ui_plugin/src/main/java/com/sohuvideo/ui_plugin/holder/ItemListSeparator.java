package com.sohuvideo.ui_plugin.holder;

import android.content.Context;
import android.view.View;

import com.sohuvideo.ui_plugin.listener.OnLoadMoreClickListener;
import com.sohuvideo.ui_plugin.model.ItemModel;
import com.sohuvideo.ui_plugin.R;


public class ItemListSeparator extends ItemModelHolder {

    @Override
    public View initView(Context mContext, BaseViewHolder mBaseViewHolder, OnLoadMoreClickListener listener) {
        final ViewHolder holder = (ViewHolder) mBaseViewHolder;
        View convertView = View.inflate(mContext, R.layout.layout_item_separator, null);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void initData(Context mContext, ItemModel model, BaseViewHolder mBaseViewHolder) {

    }

    @Override
    public BaseViewHolder getHolder() {
        return new ViewHolder(this);
    }

    class ViewHolder extends BaseViewHolder {
        public ViewHolder(ItemModelHolder mBaseHolderManager) {
            super(mBaseHolderManager);
        }
    }
}
