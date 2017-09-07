package com.baidu.integrationsdk.lib;

import android.app.Activity;

import com.baidu.appx.BDAppWallAd;

/**
 * 6.应用墙广告
 * Created by liuwei on 2017/9/7 14:57
 */

public class BDAppWall {

    private Activity yourActivity;
    String SDK_APP_KEY;
    String SDK_APPWALL_AD_ID ;

    public BDAppWall(Activity yourActivity,String SDK_APPWALL_AD_ID) {
        this(yourActivity,Constant.SDK_APP_KEY,SDK_APPWALL_AD_ID);
    }
    public BDAppWall(Activity yourActivity, String SDK_APP_KEY, String SDK_APPWALL_AD_ID) {
        this.yourActivity = yourActivity;
        this.SDK_APP_KEY = SDK_APP_KEY;
        this.SDK_APPWALL_AD_ID = SDK_APPWALL_AD_ID;
    }
    private BDAppWallAd mBDAppWallAd;

    /**
     * 加载开屏广告
     * @return f
     */
    public synchronized boolean loadAd(){
        //创建
        if (mBDAppWallAd == null)
        mBDAppWallAd = new BDAppWallAd(yourActivity, SDK_APP_KEY, SDK_APPWALL_AD_ID);
//如果本地无广告可用，需要下载广告，待下次启动使用
        if (!mBDAppWallAd.isLoaded()) {
            mBDAppWallAd.loadAd();
            return false;
        }
        return true;
    }

    /***
     * 展示开屏广告
     */
    public boolean doShowAppWall(){
        if (mBDAppWallAd.isLoaded()) {
            mBDAppWallAd.doShowAppWall();
            return true;
        }
        return false;
    }
    /***
     * 销毁广告对象
     */
    public synchronized void destroy() {
        if (mBDAppWallAd != null){
            mBDAppWallAd.destroy();
            mBDAppWallAd = null;
        }
    }
}
