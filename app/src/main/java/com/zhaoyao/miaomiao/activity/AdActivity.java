package com.zhaoyao.miaomiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.handler.TaskHandler;
import com.zhaoyao.miaomiao.handler.TaskHandlerImpl;
import com.zhaoyao.miaomiao.listener.GoogleAdListener;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;
import com.zhaoyao.miaomiao.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AdActivity extends AppCompatActivity implements TaskHandler<AdActivity>, View.OnClickListener, AdapterView.OnItemSelectedListener {

    /**
     * 开
     */
    private TextView mTvBrushInterstitialSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        initView();
//        showAD();
        showAsPopup();
    }

    private void initView() {
        list.clear();
        listAdView.clear();
        LinearLayout  ll_gdt = (LinearLayout) findViewById(R.id.ll_gdt);
        int childCount = ll_gdt.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = ll_gdt.getChildAt(i);
            if (childAt instanceof LinearLayout) {
                LinearLayout view = (LinearLayout) childAt;
                BannerView bannerView = null;
                if (i == 0) {
                    bannerView = initBanner(Constants.BannerPosID);
                } else if (i == 1) {
                    bannerView = initBanner(Constants.BannerPosID2);
                } else if (i == 2) {
                    bannerView = initBanner(Constants.BannerPosID3);
                } else if (i == 3) {
                    bannerView = initBanner(Constants.BannerPosID4);
                } else if (i == 4) {
                    bannerView = initBanner(Constants.BannerPosID5);
                } else if (i == 5) {
                    bannerView = initBanner(Constants.BannerPosID6);
                } else if (i == 6) {
                    bannerView = initBanner(Constants.BannerPosID7);
                } else if (i == 7) {
                    bannerView = initBanner(Constants.BannerPosID8);
                } else if (i == 8) {
                    bannerView = initBanner(Constants.BannerPosID9);
                } else if (i == 9) {
                    bannerView = initBanner(Constants.BannerPosID10);
                }
                if (bannerView != null) {
                    view.removeAllViews();
                    view.addView(bannerView);
                    list.add(bannerView);
                }
            } else if (childAt instanceof AdView) {
                listAdView.add((AdView) childAt);
            }
        }
        for (BannerView bannerView : list) {
            bannerView.loadAD();
        }
        MobileAds.initialize(this.getApplicationContext(), "ca-app-pub-2850046637182646~7046734019");

        LinearLayout ll_google = (LinearLayout) findViewById(R.id.ll_google);
        for (int i = 0; i < ll_google.getChildCount(); i++) {
            View childAt = ll_google.getChildAt(i);
            if (childAt instanceof AdView) {
                listAdView.add((AdView) childAt);
            }
        }
        for (AdView adView : listAdView) {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdListener(new GoogleAdListener(adView.getAdUnitId()));
            adView.loadAd(adRequest);
        }
        mTvBrushInterstitialSwitch = (TextView) findViewById(R.id.tvBrushInterstitialSwitch);
        mTvBrushInterstitialSwitch.setOnClickListener(this);
        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.spinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);

    }

    private List<BannerView> list = new ArrayList<>();
    private List<AdView> listAdView = new ArrayList<>();

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
            if (parent instanceof LinearLayout) {
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
        if (iad != null) {
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
                Log.i("AD_DEMO", String.format("LoadInterstitialAd Fail, error code: %d, error msg: %s",
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
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 50 * 1000);
            }

            @Override
            public void onADClosed() {
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
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

    public void removeCloseShow(){
        mHandler.removeMessages(CLOSE_IAD);
        mHandler.removeMessages(SHOW_IAD);
    }

    public void removeStartActivity(){
        mHandler.removeMessages(START_ACTIVITY);
    }

    public static final int ZERO = 0;

    public static final int CLOSE_IAD = 1;
    public static final int SHOW_IAD = 2;

    public static final int START_ACTIVITY = 3;

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
        if (msg.what == CLOSE_IAD) {
            closeAsPopup();
        } else if (msg.what == SHOW_IAD) {
            if (isOnPause) {
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
            } else {
                showAsPopup();
            }
        }else if (START_ACTIVITY == msg.what){
            if (isOpenSwitch){
                if (isOnPause){
                    removeStartActivity();
                    mHandler.sendEmptyMessageDelayed(START_ACTIVITY, 10 * 1000);
                }else {
                    removeStartActivity();
                    startActivity(new Intent(this,BrushInterstitialActivity.class));
                }
            }else {
                removeStartActivity();
            }
        }
    }

    private boolean isOpenSwitch = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBrushInterstitialSwitch:
                if (v instanceof TextView){
                    synchronized (this){
                        TextView tv = (TextView) v;
                        String trim = tv.getText().toString().trim();
                        removeStartActivity();
                        if (trim.equals("开")){
                            isOpenSwitch = true;
                            mHandler.sendEmptyMessageDelayed(START_ACTIVITY,1000);
                            tv.setText("关");
                        }else if (trim.equals("关")){
                            isOpenSwitch = false;
                            tv.setText("开");
                        }else {
                            startActivity(new Intent(this,BrushInterstitialActivity.class));
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isOpenSwitch && requestCode == 100){
            removeStartActivity();
            mHandler.sendEmptyMessageDelayed(START_ACTIVITY,60*1000);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent instanceof AppCompatSpinner){
            FrameLayout fl_ad = (FrameLayout) findViewById(R.id.fl_ad);
            View[] views = new View[3];
            for (int i = 0; i < fl_ad.getChildCount(); i++) {
                View childAt = fl_ad.getChildAt(i);
                if (childAt.getId() == R.id.ll_gdt){
                    views[0] = childAt;
                }else if (childAt.getId() == R.id.ll_google){
                    views[1] = childAt;
                }else if (childAt.getId() == R.id.ll_360){
                    views[2] = childAt;
                }
            }

            String item = (String) parent.getItemAtPosition(position);
            if ("广点通".equals(item)){
                fl_ad.removeAllViews();
                fl_ad.addView(views[2]);
                fl_ad.addView(views[1]);
                fl_ad.addView(views[0]);
            }else if("AdMob".equals(item)){
                fl_ad.removeAllViews();
                fl_ad.addView(views[0]);
                fl_ad.addView(views[2]);
                fl_ad.addView(views[1]);
            }else if("360".equals(item)){
                fl_ad.removeAllViews();
                fl_ad.addView(views[0]);
                fl_ad.addView(views[1]);
                fl_ad.addView(views[2]);
            }else
                ToastUtil.toastSome(this,"onItemSelected:\t"+position+"\t"+id+"\t"+item);
            fl_ad.invalidate();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ToastUtil.toastSome(this,"onNothingSelected");
    }
}
