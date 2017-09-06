package com.zhaoyao.miaomiao.model;

import java.util.Map;

/**
 * Created by liuwei on 2017/8/28 11:47
 */

public interface CartoonRecommendFragmentModel {

    void getRecommend(Map<String, String> map, ModelHttpCallback modelHttpCallback);

    void onDestroy();

}
