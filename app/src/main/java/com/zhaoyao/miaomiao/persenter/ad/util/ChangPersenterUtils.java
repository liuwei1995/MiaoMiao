package com.zhaoyao.miaomiao.persenter.ad.util;

import android.app.Activity;
import android.view.ViewGroup;

import com.chance.ads.AdRequest;
import com.chance.ads.AdView;
import com.chance.exception.PBException;
import com.chance.listener.AdListener;
import com.zhaoyao.miaomiao.util.LogUtils;
import com.zhaoyao.miaomiao.util.ToastUtil;

/**
 * 畅思
 * Created by liuwei on 2017/10/19 10:58
 */

public class ChangPersenterUtils {

    private Activity mActivity;
    private String placementID = "878620156oxqprz";

    public ChangPersenterUtils(Activity mActivity, String placementID) {
        this.mActivity = mActivity;
        this.placementID = placementID;
    }

    public ChangPersenterUtils(Activity mActivity) {
        this(mActivity,"878620156oxqprz");
    }

    private AdView adView;

    public void Chang(ViewGroup mViewGroup){
        // 创建 adView, 如果不传入 placementID，可以用另一个构造函数AdView(context)
        adView = new AdView(mActivity, placementID);
        mViewGroup.removeAllViews();
        // 在其中添加 adView
        mViewGroup.addView(adView);
//    addContentView(adView, params);
        // 启动一般性请求并在其中加载广告
        adView.loadAd(new AdRequest());
        adView.setAdListener(new AdListener() {
            @Override
            public void onReceiveAd() {
                LogUtils.d("com.chance.ads.AdView   onReceiveAd");
                ToastUtil.toastSome(mActivity,"com.chance.ads.AdView   onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd(PBException e) {
                LogUtils.d("com.chance.ads.AdView   onFailedToReceiveAd\n"+e.toString());
                ToastUtil.toastSome(mActivity,"com.chance.ads.AdView   onFailedToReceiveAd\n"+e.toString());
            }

            @Override
            public void onPresentScreen() {
                LogUtils.d("com.chance.ads.AdView   onPresentScreen");
                ToastUtil.toastSome(mActivity,"com.chance.ads.AdView   onPresentScreen");
            }

            @Override
            public void onDismissScreen() {
                LogUtils.d("com.chance.ads.AdView   onDismissScreen");
                ToastUtil.toastSome(mActivity,"com.chance.ads.AdView   onDismissScreen");
            }
        });
    }

    public void onDestroy() {
        //        TODO  畅思
        if (adView != null)adView.destroy();
    }


}
