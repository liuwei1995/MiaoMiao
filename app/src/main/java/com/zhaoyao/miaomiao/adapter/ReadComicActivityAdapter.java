package com.zhaoyao.miaomiao.adapter;

import android.support.annotation.LayoutRes;

import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.adapter.base.HealthyAdapter;
import com.zhaoyao.miaomiao.adapter.base.HealthyViewHolder;

import java.util.List;

/**
 * Created by liuwei on 2017/8/29 11:19
 */

public class ReadComicActivityAdapter extends HealthyAdapter<String> {

    public ReadComicActivityAdapter(List<String> mDatas, @LayoutRes int itemLayoutId) {
        super(mDatas, itemLayoutId);
    }

    @Override
    public void convert(HealthyViewHolder holder, String item, int position) {
//        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
//        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        holder.setImageDrawable(R.id.iv_comic,holder.itemView.getContext().getResources().getDrawable(R.mipmap.app_logo));
//        holder.itemView.setTag(item);
        holder.setImageByUrl(R.id.iv_comic,item);
    }
}
