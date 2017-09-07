package com.baidu.integrationsdk.lib;

import android.app.Activity;

import com.baidu.appx.BDSplashAd;

/**
 * 4.开屏广告
 * Created by liuwei on 2017/9/7 14:47
 */

public class BDSplash implements BDSplashAd.SplashAdListener{

    public interface SplashAdListener {
        void onAdvertisementDataDidLoadSuccess();

        void onAdvertisementDataDidLoadFailure();

        void onAdvertisementViewDidShow();

        void onAdvertisementViewDidClick();

        void onAdvertisementViewWillStartNewIntent();

        void onAdvertisementViewDidHide();
    }


    private Activity yourActivity; String SDK_APP_KEY; String SDK_SPLASH_AD_ID;

    public BDSplash(Activity yourActivity,String SDK_SPLASH_AD_ID) {
        this(yourActivity,Constant.SDK_APP_KEY,SDK_SPLASH_AD_ID);
    }

    public BDSplash(Activity yourActivity, String SDK_APP_KEY, String SDK_SPLASH_AD_ID) {
        this.yourActivity = yourActivity;
        this.SDK_APP_KEY = SDK_APP_KEY;
        this.SDK_SPLASH_AD_ID = SDK_SPLASH_AD_ID;
    }

    private BDSplashAd mBDSplashAd;
    private SplashAdListener mSplashAdListener;

    public synchronized void loadAd(){
        loadAd(null);
    }
    public synchronized void loadAd(SplashAdListener splashAdListener){
        this.mSplashAdListener = splashAdListener;
        if (mBDSplashAd == null){
            mBDSplashAd = new BDSplashAd (yourActivity, SDK_APP_KEY,SDK_SPLASH_AD_ID);
            mBDSplashAd.setAdListener(this);//设置监听回调
//如果本地无广告可用，需要下载广告，待下次启动使用
            if (!mBDSplashAd.isLoaded()) {
                mBDSplashAd.loadAd();
            }
        }

    }

    /**
     * 展示开屏广告
     * @return r
     */
    public synchronized boolean showAd() {
        if (mBDSplashAd.isLoaded()) {
           return mBDSplashAd.showAd();
        }
        return false;
    }

    /***
     * 销毁广告对象
     */
    public synchronized void destroy() {
        if (mBDSplashAd != null){
            mBDSplashAd.destroy();
            mBDSplashAd = null;
        }
    }
    @Override
    public void onAdvertisementViewDidHide() {
        if (mSplashAdListener != null)mSplashAdListener.onAdvertisementViewDidHide();
    }

    @Override
    public void onAdvertisementDataDidLoadSuccess() {
        if (mSplashAdListener != null)mSplashAdListener.onAdvertisementDataDidLoadSuccess();
    }

    @Override
    public void onAdvertisementDataDidLoadFailure() {
        if (mSplashAdListener != null)mSplashAdListener.onAdvertisementDataDidLoadFailure();
    }

    @Override
    public void onAdvertisementViewDidShow() {
        if (mSplashAdListener != null)mSplashAdListener.onAdvertisementViewDidShow();
    }

    @Override
    public void onAdvertisementViewDidClick() {
        if (mSplashAdListener != null)mSplashAdListener.onAdvertisementViewDidClick();
    }

    @Override
    public void onAdvertisementViewWillStartNewIntent() {
        if (mSplashAdListener != null)mSplashAdListener.onAdvertisementViewWillStartNewIntent();
    }
}
