package com.zhaoyao.miaomiao.persenter.activity.impl;


import com.zhaoyao.miaomiao.IView.activity.ComicChapterDetailsActivityView;
import com.zhaoyao.miaomiao.entity.ComicChapterDetailsEntity;
import com.zhaoyao.miaomiao.model.ComicChapterDetailsActivityModel;
import com.zhaoyao.miaomiao.model.ModelHttpCallback;
import com.zhaoyao.miaomiao.model.impl.ComicChapterDetailsActivityModelImpl;
import com.zhaoyao.miaomiao.persenter.activity.ComicChapterDetailsActivityPersenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuwei on 2017/8/28 18:00
 */

public class ComicChapterDetailsActivityPersenterImpl implements ComicChapterDetailsActivityPersenter {

    private ComicChapterDetailsActivityView mComicChapterDetailsActivityView;
    private ComicChapterDetailsActivityModel mComicChapterDetailsActivityModel;

    public ComicChapterDetailsActivityPersenterImpl(ComicChapterDetailsActivityView mComicChapterDetailsActivityView) {
        this.mComicChapterDetailsActivityView = mComicChapterDetailsActivityView;
        mComicChapterDetailsActivityModel = new ComicChapterDetailsActivityModelImpl(this);
    }

    @Override
    public void getComicInfo(String comic_id) {
        Map<String, String> map = new HashMap<>();
        map.put("comic_id",comic_id);
        mComicChapterDetailsActivityModel.getComicInfo(map, new ModelHttpCallback() {
            @Override
            public void startRequest() {
                mComicChapterDetailsActivityView.startRequest();
            }

            @Override
            public void endRequest() {
                mComicChapterDetailsActivityView.endRequest();
            }

            @Override
            public void connectionFailed(String failedMessge) {
                mComicChapterDetailsActivityView.connectionFailed(failedMessge);
            }

            @Override
            public void resultError(int code, String errorMessge) {
                mComicChapterDetailsActivityView.resultError(code,errorMessge);
            }
        });
    }

    @Override
    public void setComicChapterDetailsEntity(ComicChapterDetailsEntity mComicChapterDetailsEntity) {
        mComicChapterDetailsActivityView.setComicChapterDetailsEntity(mComicChapterDetailsEntity);
    }
}
