package com.zhaoyao.miaomiao.persenter.ad.impl;

import android.app.Activity;
import android.widget.LinearLayout;

import com.chance.ads.AdRequest;
import com.chance.ads.AdView;
import com.chance.exception.PBException;
import com.chance.listener.AdListener;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;
import com.zhaoyao.miaomiao.util.ToastUtil;

import static com.zhaoyao.miaomiao.persenter.ad.impl.ComicChapterDetailsActivityAdPersenterImpl.AdType.GDT;


/**
 * Created by liuwei on 2017/10/16 11:29
 */

public class ComicChapterDetailsActivityAdPersenterImpl extends AdPersenterImpl<Activity,LinearLayout> {

    public enum AdType{

        GDT,A360,chance

    }

    private AdType mAdType = GDT;

    public ComicChapterDetailsActivityAdPersenterImpl(Activity mActivity) {
        super(mActivity);
        this.mAdType = GDT;
    }

    public ComicChapterDetailsActivityAdPersenterImpl(Activity mActivity,AdType mAdType) {
        super(mActivity);
        this.mAdType = mAdType;
    }


    @Override
    public void onDestroy() {
        //        TODO  畅思
        if (AdType.chance == mAdType){
            if (adView != null)adView.destroy();
        }else if (mAdType == AdType.GDT){
            doCloseBanner();
        }
    }

    @Override
    public void initAd(LinearLayout linearLayout) {
        super.initAd(linearLayout);
    }

    private AdView adView;
    private void Chang(){
        // 创建 adView, 如果不传入placementID，可以用另一个构造函数AdView(context)
        adView = new AdView(mActivity, "878620156oxqprz");
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

    private BannerView mBannerView;

    private void doRefreshBanner() {
        if (mBannerView == null) {
            initBanner();
        }
        mBannerView.loadAD();
    }

    private void initBanner() {
        mBannerView = new BannerView(mActivity, ADSize.BANNER, Constants.APPID, Constants.BannerPosID);
        mBannerView.setRefresh(30);
        mViewGroup.removeAllViews();
        mBannerView.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {
                LogUtils.a(adError);
            }

            @Override
            public void onADReceiv() {
                LogUtils.a("onADReceiv");
            }
        });
        mViewGroup.addView(mBannerView);
    }


    private void doCloseBanner() {
        mViewGroup.removeAllViews();
        if (mBannerView != null) {
            mBannerView.destroy();
            mBannerView = null;
        }
    }
    @Override
    public void showAd() {
        if (AdType.chance == mAdType){
            Chang();
        }else if (mAdType == AdType.GDT){
            doRefreshBanner();
        }
    }
}
