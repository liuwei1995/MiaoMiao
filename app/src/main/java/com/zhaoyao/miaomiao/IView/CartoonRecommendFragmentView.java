package com.zhaoyao.miaomiao.IView;


import com.zhaoyao.miaomiao.entity.KanDongManEntity;

import java.util.List;

/**
 * Created by liuwei on 2017/8/28 11:27
 */

public interface CartoonRecommendFragmentView {

    void setTabFragmentAdapter(List<KanDongManEntity> listKanDongManEntity);

    void getRecommendStartRequest();

    void getRecommendEndRequest();

    void getRecommendConnectionFailed(String failedMessge);

    void getRecommendResultError(int code, String errorMessge);

}
