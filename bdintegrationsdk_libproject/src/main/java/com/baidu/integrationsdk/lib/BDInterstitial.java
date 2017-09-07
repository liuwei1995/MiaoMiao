package com.baidu.integrationsdk.lib;

import android.app.Activity;

import com.baidu.appx.BDInterstitialAd;

/**
 * 3.插屏广告
 * Created by liuwei on 2017/9/7 14:13
 */

public class BDInterstitial implements BDInterstitialAd.InterstitialAdListener{

    @Override
    public void onAdvertisementViewDidHide() {
        if (mInterstitialAdListener != null)mInterstitialAdListener.onAdvertisementViewDidHide();
    }

    public interface InterstitialAdListener {

        void onAdvertisementDataDidLoadSuccess();

        void onAdvertisementDataDidLoadFailure();

        void onAdvertisementViewDidShow();

        void onAdvertisementViewDidClick();

        void onAdvertisementViewWillStartNewIntent();

        void onAdvertisementViewDidHide();

    }

    private Activity yourActivity; String SDK_APP_KEY; String SDK_INTERSTITIAL_AD_ID;

    public BDInterstitial(Activity yourActivity,String SDK_INTERSTITIAL_AD_ID) {
        this(yourActivity,Constant.SDK_APP_KEY,SDK_INTERSTITIAL_AD_ID);
    }
    public BDInterstitial(Activity yourActivity, String SDK_APP_KEY, String SDK_INTERSTITIAL_AD_ID) {
        this.yourActivity = yourActivity;
        this.SDK_APP_KEY = SDK_APP_KEY;
        this.SDK_INTERSTITIAL_AD_ID = SDK_INTERSTITIAL_AD_ID;
    }

    private  BDInterstitialAd interstitialAd;
    private InterstitialAdListener mInterstitialAdListener;
    public synchronized void loadAdInterstitial(){
        loadAdInterstitial(null);
    }
    /**
     * 创建插屏广告
     *创建并展示广告
     */
    public synchronized void loadAdInterstitial(InterstitialAdListener adListener){
        this.mInterstitialAdListener = adListener;
        if (interstitialAd == null){
            interstitialAd = new BDInterstitialAd(yourActivity, SDK_APP_KEY,SDK_INTERSTITIAL_AD_ID);
            interstitialAd.setAdListener(this); //设置监听回调
        }
//下载广告，等待展示
        if (!interstitialAd.isLoaded()) {
            interstitialAd.loadAd();
        }
    }

    /**
     * 展示插屏广告
     */
    public synchronized void showAd(){
        if (interstitialAd.isLoaded()) {
            interstitialAd.showAd();
        }
    }

    /**
     * 销毁广告对象
     */
    public synchronized void destroy(){
        if (interstitialAd != null){
            interstitialAd.destroy();
            interstitialAd = null;
        }
    }

    @Override
    public void onAdvertisementDataDidLoadSuccess() {
        if (mInterstitialAdListener != null)mInterstitialAdListener.onAdvertisementDataDidLoadSuccess();
    }

    @Override
    public void onAdvertisementDataDidLoadFailure() {
        if (mInterstitialAdListener != null)mInterstitialAdListener.onAdvertisementDataDidLoadFailure();
    }

    @Override
    public void onAdvertisementViewDidShow() {
        if (mInterstitialAdListener != null)mInterstitialAdListener.onAdvertisementViewDidShow();
    }

    @Override
    public void onAdvertisementViewDidClick() {
        if (mInterstitialAdListener != null)mInterstitialAdListener.onAdvertisementViewDidClick();
    }

    @Override
    public void onAdvertisementViewWillStartNewIntent() {
        if (mInterstitialAdListener != null)mInterstitialAdListener.onAdvertisementViewWillStartNewIntent();
    }
}
