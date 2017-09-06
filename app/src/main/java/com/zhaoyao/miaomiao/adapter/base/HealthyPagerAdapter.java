package com.zhaoyao.miaomiao.adapter.base;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liuwei on 2017/6/15 17:54
 */

public abstract class HealthyPagerAdapter<T>  extends PagerAdapter {

    protected List<T> list;

    protected  int resource;

    public HealthyPagerAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View convert = convert(list.get(position), position);
        if (convert != null){
            convert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClick != null)
                        onItemClick.onClick(list.get(position),position);
                }
            });
            container.addView(convert);
        }
        return convert;
    }

    public abstract View convert(T item, int position);

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        View view = (View)object;
        ((ViewPager) container).removeView(view);
    }

    private OnItemClick<T> onItemClick;

    public OnItemClick<T> getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick<T> onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick<T>{
        void onClick(T item, int position);
    }

}
