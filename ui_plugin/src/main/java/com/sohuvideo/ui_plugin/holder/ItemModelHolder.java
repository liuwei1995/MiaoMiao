package com.sohuvideo.ui_plugin.holder;

import android.content.Context;
import android.view.View;

import com.sohuvideo.ui_plugin.listener.OnLoadMoreClickListener;
import com.sohuvideo.ui_plugin.model.ItemModel;


public abstract class ItemModelHolder {
    public abstract View initView(Context mContext, BaseViewHolder mBaseViewHolder,OnLoadMoreClickListener listener);

    public abstract void initData(Context mContext, ItemModel model, BaseViewHolder mBaseViewHolder);


    public abstract BaseViewHolder getHolder();

    public abstract class BaseViewHolder {
        public int viewType;

        private ItemModelHolder mHomeListItemHolder = null;

        public BaseViewHolder(ItemModelHolder homeListItemHolder) {
            this.mHomeListItemHolder = homeListItemHolder;
        }

        public ItemModelHolder getItemHolder() {
            return mHomeListItemHolder;
        }

    }
}
