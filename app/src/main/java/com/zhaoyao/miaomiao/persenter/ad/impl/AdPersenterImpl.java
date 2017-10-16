package com.zhaoyao.miaomiao.persenter.ad.impl;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.view.ViewGroup;

import com.zhaoyao.miaomiao.persenter.ad.AdPersenter;

/**
 * Created by liuwei on 2017/10/16 11:24
 */

public abstract class AdPersenterImpl<C extends Activity,V extends ViewGroup> implements AdPersenter<V> {

    protected C mActivity;

    public AdPersenterImpl(C mActivity) {
        this.mActivity = mActivity;
    }


    protected V mViewGroup;

    @CallSuper
    @Override
    public void initAd(V linearLayout) {
        mViewGroup = linearLayout;
    }

    @Override
    public void showAd() {

    }
}
