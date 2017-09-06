package com.zhaoyao.miaomiao.persenter.activity;


import com.zhaoyao.miaomiao.entity.ComicChapterDetailsEntity;

/**
 * Created by liuwei on 2017/8/28 18:00
 */

public interface ComicChapterDetailsActivityPersenter {

    void getComicInfo(String comic_id);

    void setComicChapterDetailsEntity(ComicChapterDetailsEntity mComicChapterDetailsEntity);

}
