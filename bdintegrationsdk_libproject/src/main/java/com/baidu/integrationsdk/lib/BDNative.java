package com.baidu.integrationsdk.lib;

import android.app.Activity;

import com.baidu.appx.BDNativeAd;

import java.util.ArrayList;

/**
 * 5.原生广告
 * Created by liuwei on 2017/9/7 15:03
 */

public class BDNative {

    private Activity yourActivity; String SDK_APP_KEY; String SDK_NATIVE_AD_ID;

    public BDNative(Activity yourActivity,String SDK_NATIVE_AD_ID) {
        this(yourActivity,Constant.SDK_APP_KEY,SDK_NATIVE_AD_ID);
    }

    public BDNative(Activity yourActivity, String SDK_APP_KEY, String SDK_NATIVE_AD_ID) {
        this.yourActivity = yourActivity;
        this.SDK_APP_KEY = SDK_APP_KEY;
        this.SDK_NATIVE_AD_ID = SDK_NATIVE_AD_ID;
    }

    private BDNativeAd mBDNativeAd;

    /**
     * //didShow() 和 didClick()
     //需要相应的UI响应逻辑中触发调用。
     * @return f
     */
    public synchronized ArrayList<BDNativeAd.AdInfo> loadAd(){
        //创建原生广告
        mBDNativeAd = new BDNativeAd(yourActivity, SDK_APP_KEY, SDK_NATIVE_AD_ID);
//下载广告
        mBDNativeAd.loadAd();
        //判断下载结果，并获取广告数据

        if (mBDNativeAd.isLoaded()) {
            ArrayList<BDNativeAd.AdInfo> adArray = mBDNativeAd.getAdInfos(); //... 自定义展示UI，其中BDNativeAd.AdInfo里的
//didShow() 和 didClick()
//需要相应的UI响应逻辑中触发调用。
            return adArray;
        }
        return new ArrayList<>();
    }

    /***
     * 销毁广告对象
     */
    public synchronized void destroy() {
        if (mBDNativeAd != null){
            mBDNativeAd.destroy();
            mBDNativeAd = null;
        }
    }

}
