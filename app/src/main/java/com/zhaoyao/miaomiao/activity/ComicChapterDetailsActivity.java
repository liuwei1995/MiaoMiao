package com.zhaoyao.miaomiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.zhaoyao.miaomiao.IView.activity.ComicChapterDetailsActivityView;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.adapter.ComicChapterDetailsActivityAdapter;
import com.zhaoyao.miaomiao.adapter.base.HealthyMultipleAdapter;
import com.zhaoyao.miaomiao.entity.ComicChapterDetailsEntity;
import com.zhaoyao.miaomiao.persenter.activity.ComicChapterDetailsActivityPersenter;
import com.zhaoyao.miaomiao.persenter.activity.impl.ComicChapterDetailsActivityPersenterImpl;
import com.zhaoyao.miaomiao.util.ToastUtil;

import java.util.List;

/**
 * 漫画章节详情
 * Created by liuwei on 2017/8/28 16:27
 */

public class ComicChapterDetailsActivity extends BaseNewActivity implements
        ComicChapterDetailsActivityView,HealthyMultipleAdapter.OnItemClickListener<ComicChapterDetailsEntity.ComicChapterBean>{


    public static final String COMIC_ID_KEY = "COMIC_ID_KEY";

    public static void startActivity(Context context,String comic_id) {
        Intent intent = new Intent(context, ComicChapterDetailsActivity.class);
        intent.putExtra(COMIC_ID_KEY,comic_id);
        context.startActivity(intent);
    }

    private ComicChapterDetailsActivityPersenter mComicChapterDetailsActivityPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_chapter_details);
        String comic_id = getIntent().getStringExtra(COMIC_ID_KEY);
        if (TextUtils.isEmpty(comic_id)){
            finish();
            return;
        }
        initView();
        mComicChapterDetailsActivityPersenter = new ComicChapterDetailsActivityPersenterImpl(this);
        mComicChapterDetailsActivityPersenter.getComicInfo(comic_id);
    }

    private RecyclerView mRecyclerView;
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_activity_cartoon_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setComicChapterDetailsEntity(ComicChapterDetailsEntity mComicChapterDetailsEntity) {
        if (mComicChapterDetailsEntity == null)return;
        List<ComicChapterDetailsEntity.ComicChapterBean> comic_chapter = mComicChapterDetailsEntity.getComic_chapter();
        ComicChapterDetailsActivityAdapter comicChapterDetailsActivityAdapter = new ComicChapterDetailsActivityAdapter(comic_chapter);
//        comicChapterDetailsActivityAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(comicChapterDetailsActivityAdapter);
    }

    @Override
    public void onItemClick(View view, ComicChapterDetailsEntity.ComicChapterBean item, int position) {
        ToastUtil.toastSome(this,item.getChapter_type());
    }

    @Override
    public void startRequest() {

    }

    @Override
    public void endRequest() {

    }

    @Override
    public void connectionFailed(String failedMessge) {
        ToastUtil.toastSome(this,failedMessge);
    }

    @Override
    public void resultError(int code, String errorMessge) {
        ToastUtil.toastSome(this,errorMessge);
    }
}
