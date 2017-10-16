package com.zhaoyao.miaomiao.persenter.ad;

import android.view.ViewGroup;

/**
 * Created by liuwei on 2017/10/16 11:22
 */

public interface AdPersenter<V extends ViewGroup> {


     void initAd(V linearLayout);

     void showAd();

     void onDestroy();

}
