package com.baidu.integrationsdk.lib;

import android.app.Activity;
import android.view.ViewGroup;

import com.baidu.appx.BDBannerAd;

/**
 * 2.横幅广告
 * Created by liuwei on 2017/9/7 14:13
 */

public class BDBanner implements BDBannerAd.BannerAdListener{

    public interface BannerAdListener {

        void onAdvertisementDataDidLoadSuccess();

        void onAdvertisementDataDidLoadFailure();

        void onAdvertisementViewDidShow();

        void onAdvertisementViewDidClick();

        void onAdvertisementViewWillStartNewIntent();
    }

    private Activity yourActivity; String SDK_APP_KEY; String SDK_BANNER_AD_ID;

    public BDBanner(Activity yourActivity,String SDK_BANNER_AD_ID) {
        this(yourActivity,Constant.SDK_APP_KEY,SDK_BANNER_AD_ID);
    }

    public BDBanner(Activity yourActivity, String SDK_APP_KEY, String SDK_BANNER_AD_ID) {
        this.yourActivity = yourActivity;
        this.SDK_APP_KEY = SDK_APP_KEY;
        this.SDK_BANNER_AD_ID = SDK_BANNER_AD_ID;
    }

    private  BDBannerAd bannerview;
    private BannerAdListener adListener;

    public synchronized void createBanner(ViewGroup container){
        createBanner(container,null);
    }
    /**
     * 横幅广告
     *创建并展示广告
     * @param container 装广告的容器
     */
    public synchronized void createBanner(ViewGroup container,BannerAdListener adListener){
//        String version = BaiduAppX.version();
        if (bannerview != null){
            destroy();
        }
        bannerview = new BDBannerAd(yourActivity, SDK_APP_KEY,SDK_BANNER_AD_ID);
        bannerview.setAdSize(BDBannerAd.SIZE_FLEXIBLE); //选择模式
        this.adListener = adListener;
        bannerview.setAdListener(this); // 设置监听回调
        container.addView(bannerview);
    }

    /**
     * 销毁广告对象
     */
    public synchronized void destroy(){
        if (bannerview != null){
            ViewGroup parent = (ViewGroup) bannerview.getParent();
            if (parent != null){
                parent.removeView(bannerview);
                bannerview.destroy();
                bannerview = null;
            }
        }
    }

    @Override
    public void onAdvertisementDataDidLoadSuccess() {
        if (adListener != null)adListener.onAdvertisementDataDidLoadSuccess();
    }

    @Override
    public void onAdvertisementDataDidLoadFailure() {
        if (adListener != null)adListener.onAdvertisementDataDidLoadFailure();
    }

    @Override
    public void onAdvertisementViewDidShow() {
        if (adListener != null)adListener.onAdvertisementViewDidShow();
    }

    @Override
    public void onAdvertisementViewDidClick() {
        if (adListener != null)adListener.onAdvertisementViewDidClick();
    }

    @Override
    public void onAdvertisementViewWillStartNewIntent() {
        if (adListener != null)adListener.onAdvertisementViewWillStartNewIntent();
    }
}
