package com.zhaoyao.miaomiao.adapter;

import android.support.annotation.LayoutRes;

import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.adapter.base.HealthyAdapter;
import com.zhaoyao.miaomiao.adapter.base.HealthyViewHolder;
import com.zhaoyao.miaomiao.entity.KanDongManEntity;

import java.util.List;

/**
 * Created by liuwei on 2017/8/28 14:33
 */

public class CartoonRecommendChildFragmentAdapter extends HealthyAdapter<KanDongManEntity.DataBean> {

    public CartoonRecommendChildFragmentAdapter(List<KanDongManEntity.DataBean> mDatas, @LayoutRes int itemLayoutId) {
        super(mDatas, itemLayoutId);
    }

    @Override
    public void convert(HealthyViewHolder holder, KanDongManEntity.DataBean item, int position) {
        holder.setImageByUrl(R.id.iv,"http://image.samanlehua.com/mh/"+item.getComic_id()+".jpg");
////        http://image.samanlehua.com/mh/
        holder.setText(R.id.tv_description,item.getLast_chapter_name());
        holder.setText(R.id.tv_title,item.getComic_name());
    }
}
