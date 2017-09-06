package com.zhaoyao.miaomiao.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.activity.ReadComicActivity;
import com.zhaoyao.miaomiao.adapter.base.HealthyAdapter;
import com.zhaoyao.miaomiao.adapter.base.HealthyMultipleAdapter;
import com.zhaoyao.miaomiao.adapter.base.HealthyMultipleViewHolder;
import com.zhaoyao.miaomiao.adapter.base.HealthyViewHolder;
import com.zhaoyao.miaomiao.entity.ComicChapterDetailsEntity;
import com.zhaoyao.miaomiao.util.GridSpacingItemDecoration;
import com.zhaoyao.miaomiao.util.LogUtils;

import java.util.List;

/**
 * 漫画章节详情
 * Created by liuwei on 2017/8/28 17:40
 */

public class ComicChapterDetailsActivityAdapter extends HealthyMultipleAdapter<ComicChapterDetailsEntity.ComicChapterBean> {

    public ComicChapterDetailsActivityAdapter(List<ComicChapterDetailsEntity.ComicChapterBean> mDatas) {
        super(mDatas);
    }

    @Override
    public HealthyMultipleViewHolder<ComicChapterDetailsEntity.ComicChapterBean> onCreateHolder(ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_comic_chapter_details_serial, null);
            return new HealthyMultipleViewHolder<>(view,viewType);
        }else if (viewType == 2){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_comic_chapter_details_fanwai, null);
            return new HealthyMultipleViewHolder<>(view,viewType);
        }
        else if (viewType == 3){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_comic_chapter_details_offprint, null);
            return new HealthyMultipleViewHolder<>(view,viewType);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_comic_chapter_details_other, null);
        return new HealthyMultipleViewHolder<>(view,viewType);
    }

    @Override
    public void onBindHolder(HealthyMultipleViewHolder holder, ComicChapterDetailsEntity.ComicChapterBean item, int position, int viewType) {
        if (viewType == 1){
            setChildAdapter(holder,item,R.id.rlv_item_activity_comic_chapter_details_serial);
        }else if (viewType == 2){
            setChildAdapter(holder,item,R.id.rlv_item_activity_comic_chapter_details_fanwai);
        }else if (viewType == 3){
            setChildAdapter(holder,item,R.id.rlv_item_activity_comic_chapter_details_offprint);
        }
    }

    private void setChildAdapter(HealthyMultipleViewHolder holder, ComicChapterDetailsEntity.ComicChapterBean item, @IdRes int id) {
        int spanCount = 4;
        int spacing = 30;
        RecyclerView mRecyclerView = (RecyclerView) holder.getView(id);
        mRecyclerView.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),spanCount));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
        List<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean> chapter_list = item.getChapter_list();
        HealthyAdapter<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean> itemActivityComicChapterDetailsFanwai = new HealthyAdapter<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean>(chapter_list,
                R.layout.item_item_activity_comic_chapter_details) {
            @Override
            public void convert(HealthyViewHolder holder, ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean item, int position) {
                if(item != null){
                    holder.setText(R.id.tv_title,item.getChapter_name());
                }else {
                    LogUtils.d("item====================null");
                }
            }
        };
        itemActivityComicChapterDetailsFanwai.setOnItemClickListener(new HealthyAdapter.OnItemClickListener<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean>() {
            @Override
            public void onItemClick(View view, ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean item, int position) {
                List<ComicChapterDetailsEntity.ComicChapterBean.ChapterListBean.ChapterSourceBean> chapter_source = item.getChapter_source();
                if (chapter_source == null || chapter_source.size() == 0)return;
                ReadComicActivity.startActivity(view.getContext(),chapter_source.get(0));
            }
        });
        mRecyclerView.setAdapter(itemActivityComicChapterDetailsFanwai);
    }

    @Override
    public int getItemViewType(int position, ComicChapterDetailsEntity.ComicChapterBean item) {
        String chapter_type = item.getChapter_type();
        if (TextUtils.isEmpty(chapter_type))return -1;
        if ("连载".equals(chapter_type)){
            return 1;
        }else if ("番外".equals(chapter_type)){
            return 2;
        }else if ("单行本".equals(chapter_type)){
            return 3;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
