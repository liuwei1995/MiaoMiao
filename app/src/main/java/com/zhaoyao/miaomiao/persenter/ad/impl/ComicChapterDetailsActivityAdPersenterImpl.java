package com.zhaoyao.miaomiao.persenter.ad.impl;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.persenter.ad.util.ChangPersenterUtils;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;

import net.youmi.android.nm.bn.BannerManager;
import net.youmi.android.nm.bn.BannerViewListener;

import java.io.Serializable;

import static com.zhaoyao.miaomiao.persenter.ad.impl.ComicChapterDetailsActivityAdPersenterImpl.AdType.GDT;


/**
 * Created by liuwei on 2017/10/16 11:29
 */

public class ComicChapterDetailsActivityAdPersenterImpl extends AdPersenterImpl<Activity,LinearLayout> implements Serializable{

    public enum AdType{

        GDT,A360,chance,YouMi

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

    private ChangPersenterUtils mChangPersenter;

    @Override
    public void onDestroy() {
        //        TODO  畅思
        if (AdType.chance == mAdType){
            if (mChangPersenter != null)mChangPersenter.onDestroy();
        }else if (mAdType == AdType.GDT){
            doCloseBanner();
        }else if (mAdType == AdType.YouMi){
            // 展示广告条窗口的 onDestroy() 回调方法中调用
            BannerManager.getInstance(mActivity).onDestroy();
        }
    }

    @Override
    public void initAd(LinearLayout linearLayout) {
        super.initAd(linearLayout);
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
            mChangPersenter = new ChangPersenterUtils(mActivity,"878620156oxqprz");
            mChangPersenter.Chang(mViewGroup);
        }else if (mAdType == AdType.GDT){
            doRefreshBanner();
        }else if (mAdType == AdType.YouMi){
// 获取广告条
            View bannerView = BannerManager.getInstance(mActivity)
                    .getBannerView(mActivity, new BannerViewListener() {
                        @Override
                        public void onRequestSuccess() {

                        }

                        @Override
                        public void onSwitchBanner() {

                        }

                        @Override
                        public void onRequestFailed() {

                        }
                    });
// 将广告条加入到布局中
            mViewGroup.addView(bannerView);
        }
    }
}
