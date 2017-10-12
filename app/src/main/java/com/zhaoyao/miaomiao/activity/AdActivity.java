package com.zhaoyao.miaomiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mediav.ads.sdk.adcore.Mvad;
import com.mediav.ads.sdk.interfaces.IMvAdEventListener;
import com.mediav.ads.sdk.interfaces.IMvBannerAd;
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

public class AdActivity extends AppCompatActivity implements TaskHandler<AdActivity>, View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    /**
     * 开
     */
    private TextView mTvBrushInterstitialSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        initView();
//        showAD();
        showAsPopup();
        RadioGroup rg_ = (RadioGroup)findViewById(R.id.rg);

        isBrush = getIntent().getBooleanExtra(START_AD_ACTIVITY_KEY, false);
        if (isBrush){
            rg_.check(R.id.rbOpen);
            synchronized (this){
                if (isBrush && !mHandler.hasMessages(START_AD_ACTIVITY)){
                    mHandler.removeMessages(START_AD_ACTIVITY);
                    mHandler.sendEmptyMessageDelayed(START_AD_ACTIVITY, START_AD_ACTIVITY_TIME);
                }
            }
        }
        rg_.setOnCheckedChangeListener(this);
    }

    private void initView() {
        list.clear();
        listAdView.clear();


        addAdView1();
        addAdView2();
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
        LinearLayout ll_google2 = (LinearLayout) findViewById(R.id.ll_google2);
        for (int i = 0; i < ll_google2.getChildCount(); i++) {
            View childAt = ll_google2.getChildAt(i);
            if (childAt instanceof AdView) {
                listAdView.add((AdView) childAt);
            }
        }

        for (AdView adView : listAdView) {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdListener(new GoogleAdListener(adView.getAdUnitId()));
            adView.loadAd(adRequest);
        }

        Add360Ad1();
        for (IMvBannerAd iMvBannerAd : listIMvBannerAd) {
            iMvBannerAd.setAdEventListener(new IMvAdEventListener() {
                @Override
                public void onAdviewGotAdSucceed() {
                    LogUtils.d("IMvBannerAd\tonAdviewGotAdSucceed");
                }

                @Override
                public void onAdviewGotAdFail() {
                    LogUtils.d("IMvBannerAd\tonAdviewGotAdFail");
                }

                @Override
                public void onAdviewIntoLandpage() {
                    LogUtils.d("IMvBannerAd\tonAdviewIntoLandpage");
                }

                @Override
                public void onAdviewDismissedLandpage() {
                    LogUtils.d("IMvBannerAd\tonAdviewDismissedLandpage");
                }

                @Override
                public void onAdviewClicked() {
                    LogUtils.d("IMvBannerAd\tonAdviewClicked");
                }

                @Override
                public void onAdviewClosed() {
                    LogUtils.d("IMvBannerAd\tonAdviewClosed");
                }

                @Override
                public void onAdviewDestroyed() {
                    LogUtils.d("IMvBannerAd\tonAdviewDestroyed");
                }
            });
            iMvBannerAd.showAds(this);
        }


        mTvBrushInterstitialSwitch = (TextView) findViewById(R.id.tvBrushInterstitialSwitch);
        mTvBrushInterstitialSwitch.setOnClickListener(this);
        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.spinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);

    }

    private void addAdView1() {
        LinearLayout ll_gdt = (LinearLayout) findViewById(R.id.ll_gdt);
        for (int i = 0; i < ll_gdt.getChildCount(); i++) {
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
    }
    private void addAdView2() {
        LinearLayout ll_gdt2 = (LinearLayout) findViewById(R.id.ll_gdt2);
        for (int i = 0; i < ll_gdt2.getChildCount(); i++) {
            View childAt = ll_gdt2.getChildAt(i);
            if (childAt instanceof LinearLayout) {
                LinearLayout view = (LinearLayout) childAt;
                BannerView bannerView = null;
                if (i == 0) {
                    bannerView = initBanner(Constants.BannerPosID11);
                } else if (i == 1) {
                    bannerView = initBanner(Constants.BannerPosID12);
                } else if (i == 2) {
                    bannerView = initBanner(Constants.BannerPosID13);
                } else if (i == 3) {
                    bannerView = initBanner(Constants.BannerPosID14);
                } else if (i == 4) {
                    bannerView = initBanner(Constants.BannerPosID15);
                } else if (i == 5) {
                    bannerView = initBanner(Constants.BannerPosID16);
                } else if (i == 6) {
                    bannerView = initBanner(Constants.BannerPosID17);
                } else if (i == 7) {
                    bannerView = initBanner(Constants.BannerPosID18);
                } else if (i == 8) {
                    bannerView = initBanner(Constants.BannerPosID19);
                } else if (i == 9) {
                    bannerView = initBanner(Constants.BannerPosID20);
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
    }

    private void Add360Ad1() {
        LinearLayout ll_360 = (LinearLayout) findViewById(R.id.ll_360);
        for (int i = 0; i < ll_360.getChildCount(); i++) {
            View childAt = ll_360.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                IMvBannerAd bannerad = null;
                if (i == 0){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID, false);
                }
                else if (i == 1){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID2, false);
                }
                else if (i == 2){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID3, false);
                }
                else if (i == 3){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID4, false);
                }
                else if (i == 4){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID5, false);
                }
                else if (i == 5){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID6, false);
                }
                else if (i == 6){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID7, false);
                }
                else if (i == 7){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID8, false);
                }
                else if (i == 8){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID9, false);
                }
                else if (i == 9){
                    bannerad = Mvad.showBanner((ViewGroup) childAt,this, Constants.Banner360ID10, false);
                }
                if (bannerad != null)
                listIMvBannerAd.add(bannerad);
            }
        }
    }

    private List<BannerView> list = new ArrayList<>();
    private List<AdView> listAdView = new ArrayList<>();
    private List<IMvBannerAd> listIMvBannerAd = new ArrayList<>();

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

    /**
     * 360横幅关闭
     */
    private void doCloseIMvBannerAdBanner() {
        for (IMvBannerAd bannerView : listIMvBannerAd) {
            bannerView.closeAds();
        }
    }

    @Override
    protected void onDestroy() {
        doCloseBanner();
        doCloseIMvBannerAdBanner();
        list.clear();
        closeAsPopup();
        mHandler.removeCallbacksAndMessages(null);
        Mvad.activityDestroy(this);
        super.onDestroy();
    }

    private InterstitialAD iad;

    private synchronized InterstitialAD getIAD() {
        if (iad == null) {
            synchronized (this){
                if (iad == null) {
                    iad = new InterstitialAD(this, Constants.APPID, Constants.InterteristalPosID);
                }
            }
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
                Log.i("AD_DEMO",String.format("LoadInterstitialAd Fail, error code: %d, error msg: %s",
                                error.getErrorCode(), error.getErrorMsg()));
                removeCloseShow();
                if (!isBrush){
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD, 30 * 1000);
                }else {
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
                }
            }

            @Override
            public void onADReceive() {
                iad.showAsPopupWindow();
                removeCloseShow();
                if (!isBrush){
                    mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 50 * 1000);
                }else {
                    mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 10 * 1000);
                }
            }

            @Override
            public void onADClosed() {
                removeCloseShow();
                if (!isBrush){
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
                }else {
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
                }
            }
        });
        iad.loadAD();
    }

    private void closeAsPopup() {
        if (iad != null) {
            iad.closePopupWindow();
            iad.destroy();
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


    public static final int CLOSE_IAD = 1;
    public static final int SHOW_IAD = 2;


    public static final int START_ACTIVITY = 3;

    public static final int START_AD_ACTIVITY = 4;

//    public static final int START_AD_ACTIVITY_TIME = 60 * 1000;
    public static final int START_AD_ACTIVITY_TIME = 10 * 60 * 1000;

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
        }else if (START_AD_ACTIVITY == msg.what){
            if (isOnPause){
                mHandler.removeMessages(START_AD_ACTIVITY);
                mHandler.sendEmptyMessageDelayed(START_AD_ACTIVITY, 10 * 1000);
            }else {
//                Intent intent = new Intent(this, AdActivity.class);
//                intent.putExtra(START_AD_ACTIVITY_KEY,true);
//                startActivity(intent);
                setResult(100);
                onBackPressed();
            }
        }
    }
    public static final String START_AD_ACTIVITY_KEY = "START_AD_ACTIVITY_KEY";

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
            String[] stringArray = getResources().getStringArray(R.array.ad_spinner);
            if (stringArray.length == 0)return;
            View[] views = new View[stringArray.length];
            for (int i = 0; i < fl_ad.getChildCount(); i++) {
                View childAt = fl_ad.getChildAt(i);
                if (childAt.getId() == R.id.ll_gdt){
                    views[0] = childAt;
                }else if (childAt.getId() == R.id.ll_gdt2){
                    views[1] = childAt;
                }else if (childAt.getId() == R.id.ll_google){
                    views[2] = childAt;
                }else if (childAt.getId() == R.id.ll_google2){
                    views[3] = childAt;
                }else if (childAt.getId() == R.id.ll_360){
                    views[4] = childAt;
                }
            }
            fl_ad.removeViewInLayout(views[position]);
            fl_ad.addView(views[position]);
            fl_ad.invalidate();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ToastUtil.toastSome(this,"onNothingSelected");
    }

    private boolean isBrush = false;
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rbOpen){
            isBrush = true;
            ToastUtil.toastSome(this,"刷插屏缩短至10秒");
            if (mHandler.hasMessages(SHOW_IAD)){
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
            }
            if (!mHandler.hasMessages(START_AD_ACTIVITY)){
                synchronized (this){
                    if (!mHandler.hasMessages(START_AD_ACTIVITY)){
                        mHandler.removeMessages(START_AD_ACTIVITY);
                        mHandler.sendEmptyMessageDelayed(START_AD_ACTIVITY, START_AD_ACTIVITY_TIME);
                    }
                }
            }
        }else if (checkedId == R.id.rbClose){
            ToastUtil.toastSome(this,"刷插屏关闭");
            isBrush = false;
            if (mHandler.hasMessages(CLOSE_IAD)){
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 50 * 1000);
            }
            mHandler.removeMessages(START_AD_ACTIVITY);
        }
    }
}
