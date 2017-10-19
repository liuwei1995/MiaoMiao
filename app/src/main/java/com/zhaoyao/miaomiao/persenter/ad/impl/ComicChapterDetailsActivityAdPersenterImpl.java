package com.zhaoyao.miaomiao.persenter.ad.impl;

import android.app.Activity;
import android.widget.LinearLayout;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.persenter.ad.util.ChangPersenterUtils;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;

import java.io.Serializable;

import static com.zhaoyao.miaomiao.persenter.ad.impl.ComicChapterDetailsActivityAdPersenterImpl.AdType.GDT;


/**
 * Created by liuwei on 2017/10/16 11:29
 */

public class ComicChapterDetailsActivityAdPersenterImpl extends AdPersenterImpl<Activity,LinearLayout> implements Serializable{

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

    private ChangPersenterUtils mChangPersenter;

    @Override
    public void onDestroy() {
        //        TODO  畅思
        if (AdType.chance == mAdType){
            if (mChangPersenter != null)mChangPersenter.onDestroy();
        }else if (mAdType == AdType.GDT){
            doCloseBanner();
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
        }
    }
}
