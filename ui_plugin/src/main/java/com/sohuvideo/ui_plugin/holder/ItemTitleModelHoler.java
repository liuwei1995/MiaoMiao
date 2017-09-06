package com.sohuvideo.ui_plugin.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sohuvideo.ui_plugin.listener.OnLoadMoreClickListener;
import com.sohuvideo.ui_plugin.model.ItemModel;
import com.sohuvideo.ui_plugin.R;


public class ItemTitleModelHoler extends ItemModelHolder {

    private OnLoadMoreClickListener mListener;

    @Override
    public View initView(Context mContext, BaseViewHolder mBaseViewHolder, OnLoadMoreClickListener listener) {
        this.mListener = listener;
        final ViewHolder holder = (ViewHolder) mBaseViewHolder;
        View convertView = View.inflate(mContext, R.layout.layout_item_title, null);
        holder.mTitle = (TextView) convertView.findViewById(R.id.id_item_title_tv);
        holder.mMore = (TextView) convertView.findViewById(R.id.id_item_more_tv);
        convertView.setTag(holder);
        return convertView;
    }


    @Override
    public void initData(final Context mContext, final ItemModel model, BaseViewHolder mBaseViewHolder) {
        final ViewHolder holder = (ViewHolder) mBaseViewHolder;
        holder.mTitle.setText(model.getTitle());
        if (model.getSize() < 6) { //小于6个视频不显示"更多"按钮
            holder.mMore.setVisibility(View.GONE);
            return;
        } else {
            holder.mMore.setVisibility(View.VISIBLE);
            holder.mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onMoreClick(model.getTitle(), model.getCategory_code());
                }
            });
        }

    }

    @Override
    public BaseViewHolder getHolder() {
        return new ViewHolder(this);
    }

    class ViewHolder extends BaseViewHolder {
        public ViewHolder(ItemModelHolder mBaseHolderManager) {
            super(mBaseHolderManager);
        }

        private TextView mTitle;
        private TextView mMore;
    }

}
