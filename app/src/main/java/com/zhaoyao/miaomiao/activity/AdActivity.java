package com.zhaoyao.miaomiao.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.handler.TaskHandler;
import com.zhaoyao.miaomiao.handler.TaskHandlerImpl;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AdActivity extends AppCompatActivity implements TaskHandler<AdActivity> {

    private LinearLayout mLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
//        showAD();
        showAsPopup();
    }

    private void initView() {
        list.clear();
        mLl = (LinearLayout) findViewById(R.id.ll);
        int childCount = mLl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mLl.getChildAt(i);
            if (childAt instanceof LinearLayout){
                LinearLayout view = (LinearLayout) childAt;
                BannerView bannerView = null;
                if (i == 0){
                     bannerView = initBanner(Constants.BannerPosID3);
                }else if (i == 1){
                     bannerView = initBanner(Constants.BannerPosID4);
                }else if (i == 2){
                    bannerView = initBanner(Constants.BannerPosID2);
                }else if (i ==3){
                    bannerView = initBanner(Constants.BannerPosID);
                }
                if (bannerView != null){
                    view.removeAllViews();
                    view.addView(bannerView);
                    list.add(bannerView);
                }
            }
        }
        for (BannerView bannerView : list) {
            bannerView.loadAD();
        }
    }
    private List<BannerView> list = new ArrayList<>();

    private BannerView initBanner(String BannerPosID) {
        BannerView mBannerView = new BannerView(this, ADSize.BANNER, Constants.APPID, BannerPosID);
        mBannerView.setRefresh(30);
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
        return mBannerView;
    }


    private void doCloseBanner() {
        for (BannerView bannerView : list) {
            ViewParent parent = bannerView.getParent();
            if (parent instanceof LinearLayout){
                LinearLayout view = (LinearLayout) parent;
                view.removeAllViews();
            }
            bannerView.destroy();
        }
    }

    @Override
    protected void onDestroy() {
        doCloseBanner();
        list.clear();
        if (iad != null){
            iad.destroy();
            closeAsPopup();
        }
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    InterstitialAD iad;

    private synchronized InterstitialAD getIAD() {
        if (iad == null) {
            iad = new InterstitialAD(this, Constants.APPID, Constants.InterteristalPosID);
        }
        return iad;
    }

    private synchronized void showAD() {
        getIAD().setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onNoAD(AdError error) {
                Log.i(
                        "AD_DEMO",
                        String.format("LoadInterstitialAd Fail, error code: %d, error msg: %s",
                                error.getErrorCode(), error.getErrorMsg()));
            }

            @Override
            public void onADReceive() {
                Log.i("AD_DEMO", "onADReceive");
                iad.show();
            }
        });
        iad.loadAD();
    }

    private synchronized void showAsPopup() {
        getIAD().setADListener(new AbstractInterstitialADListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i("AD_DEMO",
                        String.format("LoadInterstitialAd Fail, error code: %d, error msg: %s",
                                error.getErrorCode(), error.getErrorMsg()));
            }

            @Override
            public void onADReceive() {
                iad.showAsPopupWindow();
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(CLOSE_IAD,60*1000);
            }

            @Override
            public void onADClosed() {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(SHOW_IAD,30*1000);
            }
        });
        iad.loadAD();
    }

    private void closeAsPopup() {
        if (iad != null) {
            iad.closePopupWindow();
        }
    }
    private Handler mHandler = new TaskHandlerImpl<>(this);

    public static final int ZERO = 0;

    public static final int CLOSE_IAD = 1;
    public static final int SHOW_IAD = 2;

    @Override
    protected void onResume() {
        super.onResume();
        isOnPause = false;
    }

    private boolean isOnPause = false;

    @Override
    protected void onPause() {
        isOnPause = true;
        super.onPause();
    }

    @Override
    public void handleMessage(WeakReference<AdActivity> weakReference, Message msg) {
        if (msg.what == CLOSE_IAD){
            closeAsPopup();
        }else if (msg.what == SHOW_IAD){
            if (isOnPause){
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(SHOW_IAD,10*1000);
            }else {
                showAsPopup();
            }
        }
    }
}
