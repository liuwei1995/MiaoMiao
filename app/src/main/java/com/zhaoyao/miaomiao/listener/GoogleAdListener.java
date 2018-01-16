package com.zhaoyao.miaomiao.listener;

import com.google.android.gms.ads.AdListener;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;

/**
 * Created by liuwei on 2017/9/22 09:39
 */

public class GoogleAdListener extends AdListener {

    public interface OnAdListener{
        void onAdLoaded();
    }

    private String adUnitId;

    public GoogleAdListener(String adUnitId) {
        this.adUnitId = adUnitId;
    }
    private OnAdListener mOnAdListener;

    public GoogleAdListener(String adUnitId,OnAdListener mOnAdListener) {
        this.adUnitId = adUnitId;
        this.mOnAdListener = mOnAdListener;
    }

    @Override
    public void onAdLoaded() {
        // Code to be executed when an ad finishes loading.
        if ("ca-app-pub-2850046637182646/8523467219".equals(adUnitId)){
            LogUtils.i("Ads", "onAdLoaded:\t"+adUnitId);
        }else
        LogUtils.i("Ads", "onAdLoaded:\t"+adUnitId);
        if (mOnAdListener != null)mOnAdListener.onAdLoaded();
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        // Code to be executed when an ad request fails.
        LogUtils.i("Ads", "onAdFailedToLoad:	"+adUnitId);
    }

    @Override
    public void onAdOpened() {
        // Code to be executed when an ad opens an overlay that
        // covers the screen.
        LogUtils.i("Ads", "onAdOpened:	"+adUnitId);
    }

    @Override
    public void onAdLeftApplication() {
        // Code to be executed when the user has left the app.
        LogUtils.i("Ads", "onAdLeftApplication:	"+adUnitId);
    }

    @Override
    public void onAdClosed() {
        // Code to be executed when when the user is about to return
        // to the app after tapping on an ad.
        LogUtils.i("Ads", "onAdClosed:	"+adUnitId);
    }
}
