package com.zhaoyao.miaomiao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoyao.miaomiao.IView.CartoonRecommendFragmentView;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.adapter.TabFragmentAdapter;
import com.zhaoyao.miaomiao.entity.KanDongManEntity;
import com.zhaoyao.miaomiao.persenter.fragment.CartoonRecommendFragmentPersenter;
import com.zhaoyao.miaomiao.persenter.fragment.impl.CartoonRecommendFragmentPersenterImpl;
import com.zhaoyao.miaomiao.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 漫画推荐
 * Created by liuwei on 2017/8/28 11:08
 */

public class CartoonRecommendFragment extends BaseNewFragment implements CartoonRecommendFragmentView {

    public static CartoonRecommendFragment newInstance() {
        Bundle args = new Bundle();
        args.putBoolean(IS_PREPARED, false);
        CartoonRecommendFragment fragment = new CartoonRecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View mView;
    private CartoonRecommendFragmentPersenter mCartoonRecommendFragmentPersenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_cartoon_recommend, null);
        mCartoonRecommendFragmentPersenter = new CartoonRecommendFragmentPersenterImpl(this);
        return mView;
    }

    private TabFragmentAdapter fragmentAdapter;
    private List<Fragment> fragments;
    private List<String> lists;
    private ViewPager mViewPager;
    private TabLayout tablayout;

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        lists = new ArrayList<>();
        mViewPager = $(mView,R.id.viewPager);
        tablayout = $(mView,R.id.tablayout);
        mCartoonRecommendFragmentPersenter.getRecommend();
    }

    @Override
    public void setTabFragmentAdapter(List<KanDongManEntity> listKanDongManEntity){
        if (listKanDongManEntity == null || listKanDongManEntity.size() == 0){
            ToastUtil.toastSome(mContext,"没有数据");
            return;
        }
        lists.clear();
        fragments.clear();
        for (int i = 0; i < listKanDongManEntity.size(); i++) {
            KanDongManEntity kanDongManEntity = listKanDongManEntity.get(i);
            String tab_title = kanDongManEntity.getTab_title();
            lists.add(tab_title);
            List<KanDongManEntity.DataBean> data = kanDongManEntity.getData();
            if (i == 0){
                fragments.add(CartoonRecommendChildFragment.newInstance(data,false));
            }else {
                fragments.add(CartoonRecommendChildFragment.newInstance(data,true));
            }
        }

        fragmentAdapter = new TabFragmentAdapter(fragments,lists, getChildFragmentManager(), mContext);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());
        // 初始化
        tablayout = (TabLayout) mView.findViewById(R.id.tablayout);
// 将ViewPager和TabLayout绑定
        tablayout.setupWithViewPager(mViewPager);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void getRecommendStartRequest() {
//        ToastUtil.toastSome(mContext,"开始请求");
    }

    @Override
    public void getRecommendEndRequest() {
    
    }

    @Override
    public void getRecommendConnectionFailed(String failedMessge) {
        ToastUtil.toastSome(mContext,failedMessge);
    }

    @Override
    public void getRecommendResultError(int code, String errorMessge) {
        ToastUtil.toastSome(mContext,errorMessge);
    }

    @Override
    public void onDestroy() {
        mCartoonRecommendFragmentPersenter.onDestroy();
        super.onDestroy();
    }
}
