package com.zhaoyao.miaomiao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoyao.miaomiao.IView.CartoonRecommendChildFragmentView;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.activity.ComicChapterDetailsActivity;
import com.zhaoyao.miaomiao.adapter.CartoonRecommendChildFragmentAdapter;
import com.zhaoyao.miaomiao.adapter.base.HealthyAdapter;
import com.zhaoyao.miaomiao.entity.KanDongManEntity;
import com.zhaoyao.miaomiao.persenter.fragment.CartoonRecommendChildFragmentPersenter;
import com.zhaoyao.miaomiao.persenter.fragment.impl.CartoonRecommendChildFragmentPersenterImpl;
import com.zhaoyao.miaomiao.util.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 漫画推荐
 * Created by liuwei on 2017/8/28 11:08
 */

public class CartoonRecommendChildFragment extends BaseNewFragment implements CartoonRecommendChildFragmentView,HealthyAdapter.OnItemClickListener<KanDongManEntity.DataBean>{

    public static final String DATA_KEY = "DATA_KEY";


    public static CartoonRecommendChildFragment newInstance(List<KanDongManEntity.DataBean> data, Boolean is_prepared) {
        Bundle args = new Bundle();
        args.putBoolean(IS_PREPARED, is_prepared);
        if (data instanceof ArrayList) {
            args.putParcelableArrayList(DATA_KEY, (ArrayList<? extends Parcelable>) data);
        } else {
            ArrayList<KanDongManEntity.DataBean> list = new ArrayList<>();
            for (KanDongManEntity.DataBean dataBean : data) {
                list.add(dataBean);
            }
            args.putParcelableArrayList(DATA_KEY, list);
        }
        CartoonRecommendChildFragment fragment = new CartoonRecommendChildFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private List<KanDongManEntity.DataBean> list;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null){
            list = arguments.getParcelableArrayList(DATA_KEY);
        }
    }

    private View mView;
    private CartoonRecommendChildFragmentPersenter mCartoonRecommendChildFragmentPersenter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_cartoon_recommend_child, null);
        mCartoonRecommendChildFragmentPersenter = new CartoonRecommendChildFragmentPersenterImpl(this);
        initView(mView);
        return mView;
    }

    private CartoonRecommendChildFragmentAdapter mCartoonRecommendChildFragmentAdapter;

    @Override
    protected void initData() {
        if (list != null && list.size() > 0){
            mCartoonRecommendChildFragmentAdapter = new CartoonRecommendChildFragmentAdapter(list, R.layout.item_fragment_cartoon_recommend_child);
            mCartoonRecommendChildFragmentAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mCartoonRecommendChildFragmentAdapter);
        }
    }

    private void initView(View mView) {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rlv_cartoon_recommend_child);
        int spanCount = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,spanCount));
        int spacing = 30;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
    }

    @Override
    public void onItemClick(View view, KanDongManEntity.DataBean item, int position) {
        ComicChapterDetailsActivity.startActivity(mContext,item.getComic_id()+"");
    }
}
