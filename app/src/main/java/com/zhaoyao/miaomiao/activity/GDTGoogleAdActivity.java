package com.zhaoyao.miaomiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mediav.ads.sdk.adcore.Mvad;
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
import com.zhaoyao.miaomiao.service.AdService;
import com.zhaoyao.miaomiao.util.AdActivitySharedPreferences;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;
import com.zhaoyao.miaomiao.util.ToastUtil;
import com.zhaoyao.miaomiao.util.ad.gdt.GDTInterstitialAD;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GDTGoogleAdActivity extends AppCompatActivity implements
        TaskHandler, View.OnClickListener,
        AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener ,
        GDTInterstitialAD.GDTInterstitialADListener {

    /**
     * 启
     */
    private CheckBox mCbOpen10;
    private CheckBox mCbOpen5;
    private CheckBox mCbMore;
    private CheckBox mCbBrush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdt_google_ad);
        sendBroadcast(new Intent(AdService.ACTION_ON_CREATE));
        mCbOpen10 = (CheckBox) findViewById(R.id.cb_open10);
        mCbOpen5 = (CheckBox) findViewById(R.id.cb_open5);

        mCbMore = (CheckBox) findViewById(R.id.cb_more);
        CheckBox  mCb360 = (CheckBox) findViewById(R.id.cb_360);
        CheckBox  mCbGoogle = (CheckBox) findViewById(R.id.cb_google);
        CheckBox  mCbPause = (CheckBox) findViewById(R.id.cb_pause);
        mCbBrush = (CheckBox) findViewById(R.id.cb_brush);

        isMore = getIntent().getBooleanExtra(IS_isMore_KEY,false);
        mCbMore.setChecked(isMore);

        initView();


        RadioGroup rg_ = (RadioGroup) findViewById(R.id.rg);
        isBrush = getIntent().getBooleanExtra(IS_BRUSH_KEY, false);
        if (isBrush) {
            rg_.check(R.id.rbOpen);
        }

        String stringExtra = getIntent().getStringExtra(IS_Restart_KEY);
        ToastUtil.toastSome(this,TextUtils.isEmpty(stringExtra) || "null".equals(stringExtra) ? "新界面" : stringExtra);

        Intent intent = getIntent();
        if (intent.getBooleanExtra(IS_START_ACTIVITY_5_KEY, false)){
            mCbOpen5.setChecked(true);
            mCbOpen10.setChecked(false);
        }else {
            if (intent.getBooleanExtra(IS_START_ACTIVITY_10_KEY, false)){
                mCbOpen5.setChecked(false);
                mCbOpen10.setChecked(true);
            }else {
                mCbOpen5.setChecked(false);
                mCbOpen10.setChecked(false);
            }
        }
       boolean isStartActivity = mCbOpen5.isChecked() || mCbOpen10.isChecked();

        if (isStartActivity){
            synchronized (this) {
                if (!mHandler.hasMessages(START_AD_ACTIVITY)) {
                    mHandler.removeMessages(START_AD_ACTIVITY);
                    mHandler.sendEmptyMessageDelayed(START_AD_ACTIVITY, mCbOpen5.isChecked() ? START_AD_ACTIVITY_TIME_5 : START_AD_ACTIVITY_TIME_10);
                }
            }
        }
        mCbOpen5.setOnCheckedChangeListener(this);
        mCbOpen10.setOnCheckedChangeListener(this);
        mCbMore.setOnCheckedChangeListener(this);
        mCb360.setOnCheckedChangeListener(this);
        mCbGoogle.setOnCheckedChangeListener(this);
        mCbPause.setOnCheckedChangeListener(this);
        mCbBrush.setOnCheckedChangeListener(this);
        rg_.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        sendBroadcast(new Intent(AdService.ACTION_ON_CREATE));
        ToastUtil.toastSome(this, "onNewIntent");
    }

    private void initView() {
        listAdViewGoogle.clear();

        addGdtBannerView();

        MobileAds.initialize(this, "ca-app-pub-2850046637182646~7046734019");

        LinearLayout ll_google = (LinearLayout) findViewById(R.id.ll_google);
        for (int i = 0; i < ll_google.getChildCount(); i++) {
            View childAt = ll_google.getChildAt(i);
            if (childAt instanceof AdView) {
                listAdViewGoogle.add((AdView) childAt);
            }
        }
        LinearLayout ll_google2 = (LinearLayout) findViewById(R.id.ll_google2);
        for (int i = 0; i < ll_google2.getChildCount(); i++) {
            View childAt = ll_google2.getChildAt(i);
            if (childAt instanceof AdView) {
                listAdViewGoogle.add((AdView) childAt);
            }
        }

        for (AdView adView : listAdViewGoogle) {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdListener(new GoogleAdListener(adView.getAdUnitId()));
            adView.loadAd(adRequest);
        }

//        Add360Ad1();
//        for (IMvBannerAd iMvBannerAd : listIMvBannerAd360) {
//            iMvBannerAd.setAdEventListener(new IMvAdEventListener() {
//                @Override
//                public void onAdviewGotAdSucceed() {
//                    LogUtils.d("IMvBannerAd\tonAdviewGotAdSucceed");
//                }
//
//                @Override
//                public void onAdviewGotAdFail() {
//                    LogUtils.d("IMvBannerAd\tonAdviewGotAdFail");
//                }
//
//                @Override
//                public void onAdviewIntoLandpage() {
//                    LogUtils.d("IMvBannerAd\tonAdviewIntoLandpage");
//                }
//
//                @Override
//                public void onAdviewDismissedLandpage() {
//                    LogUtils.d("IMvBannerAd\tonAdviewDismissedLandpage");
//                }
//
//                @Override
//                public void onAdviewClicked() {
//                    LogUtils.d("IMvBannerAd\tonAdviewClicked");
//                }
//
//                @Override
//                public void onAdviewClosed() {
//                    LogUtils.d("IMvBannerAd\tonAdviewClosed");
//                }
//
//                @Override
//                public void onAdviewDestroyed() {
//                    LogUtils.d("IMvBannerAd\tonAdviewDestroyed");
//                }
//            });
//            iMvBannerAd.showAds(this);
//        }


        TextView  mTvBrushInterstitialSwitch = (TextView) findViewById(R.id.tvBrushInterstitialSwitch);
        mTvBrushInterstitialSwitch.setOnClickListener(this);
        TextView mTvRestart = (TextView) findViewById(R.id.tvRestart);
        mTvRestart.setOnClickListener(this);

        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.spinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);

        if (!mCbMore.isChecked()){
            showAsPopup();
        }else{
            ToastUtil.toastSome(this,"3 秒后多插屏开始");
            mHandler.removeMessages(GDTInterstitialAD_LOADAD_IAD);
            mHandler.sendEmptyMessageDelayed(GDTInterstitialAD_LOADAD_IAD,3 * 1000);
        }

    }

    private synchronized void addGdtBannerView() {
        doCloseBanner();
        listAdGdt.clear();
        addAdView1();
        addAdView2();
        for (BannerView bannerView : listAdGdt) {
            bannerView.loadAD();
        }
        mCbBrush.setChecked(false);
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
                    listAdGdt.add(bannerView);
                }
            } else if (childAt instanceof AdView) {
                listAdViewGoogle.add((AdView) childAt);
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
                    listAdGdt.add(bannerView);
                }
            } else if (childAt instanceof AdView) {
                listAdViewGoogle.add((AdView) childAt);
            }
        }
    }

//    private void Add360Ad1() {
//        LinearLayout ll_360 = (LinearLayout) findViewById(R.id.ll_360);
//        for (int i = 0; i < ll_360.getChildCount(); i++) {
//            View childAt = ll_360.getChildAt(i);
//            if (childAt instanceof ViewGroup) {
//                IMvBannerAd bannerad = null;
//                if (i == 0) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID, false);
//                } else if (i == 1) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID2, false);
//                } else if (i == 2) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID3, false);
//                } else if (i == 3) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID4, false);
//                } else if (i == 4) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID5, false);
//                } else if (i == 5) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID6, false);
//                } else if (i == 6) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID7, false);
//                } else if (i == 7) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID8, false);
//                } else if (i == 8) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID9, false);
//                } else if (i == 9) {
//                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID10, false);
//                }
//                if (bannerad != null)
//                    listIMvBannerAd360.add(bannerad);
//            }
//        }
//    }

    private List<BannerView> listAdGdt = new ArrayList<>();
    private List<AdView> listAdViewGoogle = new ArrayList<>();
//    private List<IMvBannerAd> listIMvBannerAd360 = new ArrayList<>();

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
        ToastUtil.toastSome(this,"正在关闭广点通");
        for (BannerView bannerView : listAdGdt) {
            ViewParent parent = bannerView.getParent();
            if (parent instanceof LinearLayout) {
                LinearLayout view = (LinearLayout) parent;
                view.removeAllViews();
            }
            bannerView.destroy();
        }
    }

//    /**
//     * 360横幅关闭
//     */
//    private void doCloseIMvBannerAdBanner() {
//        for (IMvBannerAd bannerView : listIMvBannerAd360) {
//            bannerView.closeAds();
//        }
//    }

    private long mKeyDownTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long time = System.currentTimeMillis() - mKeyDownTime;
            mKeyDownTime = System.currentTimeMillis();
            if (time > 2 * 1000){
                ToastUtil.toastSome(this, "再次返回退出");
                return true;
            }else {
              sendBroadcast(new Intent(AdService.ACTION_ON_KEY_DOWN));
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void restartActivity() {
        mHandler.removeCallbacksAndMessages(null);
        Intent intent = new Intent();
        intent.putExtra(IS_BRUSH_KEY, isBrush);
        intent.putExtra(IS_START_ACTIVITY_5_KEY,mCbOpen5.isChecked());
        intent.putExtra(IS_START_ACTIVITY_10_KEY,mCbOpen10.isChecked());

        intent.putExtra(IS_Restart_KEY,"重启的");
        intent.putExtra(IS_isMore_KEY,isMore);
        AdService.saveData(intent.getExtras());
        setResult(100, intent);
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isOpenSwitch && requestCode == 100) {
            removeStartActivity();
            mHandler.sendEmptyMessageDelayed(START_ACTIVITY, 60 * 1000);
        }
    }

    @Override
    protected void onDestroy() {
        if (mapGDTInterstitialAD != null){
            for (String s : mapGDTInterstitialAD.keySet()) {
                GDTInterstitialAD gdtInterstitialAD = mapGDTInterstitialAD.get(s);
                gdtInterstitialAD.onDestroy();
            }
        }
        doCloseBanner();
//        doCloseIMvBannerAdBanner();
        listAdGdt.clear();
        closeAsPopup();
        mHandler.removeCallbacksAndMessages(null);
        Mvad.activityDestroy(this);
        sendBroadcast(new Intent(AdService.ACTION_ON_DESTROY));
        super.onDestroy();
        System.exit(0);
    }

    private InterstitialAD iad;

    private synchronized InterstitialAD getIAD() {
        if (iad != null) {
            synchronized (this) {
                if (iad != null) {
                    closeAsPopup();
                }
            }
        }
        return iad = new InterstitialAD(this, Constants.APPID, Constants.InterteristalPosID);
    }


    private Map<String,GDTInterstitialAD> mapGDTInterstitialAD = null;
    private GDTInterstitialAD mGDTInterstitialAD;

    private List<String> listString = null;

    private synchronized void createGDTInterstitialADAsPopup() {
        if (mapGDTInterstitialAD == null){
            mapGDTInterstitialAD = new HashMap<>();
            listString = new ArrayList<>();

            putGDTInterstitialAD();
        }else {
            listString.clear();
            for (String s : mapGDTInterstitialAD.keySet()) {
                GDTInterstitialAD gdtInterstitialAD = mapGDTInterstitialAD.get(s);
                gdtInterstitialAD.onDestroy();
            }

            putGDTInterstitialAD();
        }

        for (String s : mapGDTInterstitialAD.keySet()) {
            GDTInterstitialAD gdtInterstitialAD = mapGDTInterstitialAD.get(s);
            gdtInterstitialAD.loadAD();
        }
        closeAsPopup();
        timerShow();
    }

    private void putGDTInterstitialAD() {

        mapGDTInterstitialAD.clear();

        GDTInterstitialAD gdtInterstitialAD = new GDTInterstitialAD(this, Constants.InterteristalPosID,this);
        mapGDTInterstitialAD.put(gdtInterstitialAD.getInterteristalPosID(),gdtInterstitialAD);

        GDTInterstitialAD gdtInterstitialAD1 = new GDTInterstitialAD(this, Constants.InterteristalPosID1,this);
        mapGDTInterstitialAD.put(gdtInterstitialAD1.getInterteristalPosID(),gdtInterstitialAD1);

        GDTInterstitialAD gdtInterstitialAD2 = new GDTInterstitialAD(this, Constants.InterteristalPosID2,this);
        mapGDTInterstitialAD.put(gdtInterstitialAD2.getInterteristalPosID(),gdtInterstitialAD2);

        GDTInterstitialAD gdtInterstitialAD3 = new GDTInterstitialAD(this, Constants.InterteristalPosID3,this);
        mapGDTInterstitialAD.put(gdtInterstitialAD3.getInterteristalPosID(),gdtInterstitialAD3);
    }


    /**
     * 定时
     */
    private synchronized void timerShow(){
        mHandler.removeMessages(TIMER_CLOSE);
        mHandler.removeMessages(TIMER_SHOW);
        mHandler.sendEmptyMessageDelayed(TIMER_SHOW,5*1000);
    }
    private synchronized void timerClose(){
        mHandler.removeMessages(TIMER_SHOW);
        mHandler.removeMessages(TIMER_CLOSE);
        mHandler.sendEmptyMessageDelayed(TIMER_CLOSE,5*1000);
    }

    private synchronized void showAsPopup() {

        getIAD().setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onNoAD(AdError error) {
                removeCloseShow();
                if (isMore)return;
                if (!isBrush) {
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
                } else {
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD,5*1000);
                }
            }

            @Override
            public void onADReceive() {
                iad.showAsPopupWindow();
                removeCloseShow();
                if (isMore)return;
                if (!isBrush) {
                    mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 50 * 1000);
                } else {
                    mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 5 * 1000);
                }
            }

            @Override
            public void onADClosed() {
                removeCloseShow();
                if (isMore)return;
                if (!isBrush) {
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
                } else {
                    mHandler.sendEmptyMessageDelayed(SHOW_IAD,5*1000);
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


    private Handler mHandler = new TaskHandlerImpl(this);

    public void removeCloseShow() {
        mHandler.removeMessages(CLOSE_IAD);
        mHandler.removeMessages(SHOW_IAD);
    }

    public void removeStartActivity() {
        mHandler.removeMessages(START_ACTIVITY);
    }




    @Override
    protected void onResume() {
        super.onResume();
        isOnPause = false;
        AdActivitySharedPreferences.newInstance().clear(this);
    }

    private boolean isOnPause = false;

    @Override
    protected void onPause() {
        isOnPause = true;
        super.onPause();
    }

    public static final int CLOSE_IAD = 1;
    public static final int SHOW_IAD = 2;

    public static final int GDTInterstitialAD_CLOSE_IAD = 11;

    public static final int GDTInterstitialAD_LOADAD_IAD = 44;
    public static final int TIMER_SHOW = 33;
    public static final int TIMER_CLOSE = 55;

    public static final int ADD_GDT_BANNER_VIEW = 66;


    public static final int START_ACTIVITY = 3;

    public static final int START_AD_ACTIVITY = 4;

    public static final int START_AD_ACTIVITY_TIME_5 = 5 * 60 * 1000;
    public static final int START_AD_ACTIVITY_TIME_10 = 10 * 60 * 1000;

    @Override
    public void handleMessage(WeakReference weakReference, Message msg) {
        if (msg.what == CLOSE_IAD) {
            if (isMorePause){
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 5 * 1000);
                return;
            }
            closeAsPopup();
        } else if (msg.what == SHOW_IAD) {
            if (isOnPause) {
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(SHOW_IAD, 5 * 1000);
            } else {
                showAsPopup();
            }
        } else if (START_ACTIVITY == msg.what) {
            if (isOpenSwitch) {
                if (isOnPause) {
                    removeStartActivity();
                    mHandler.sendEmptyMessageDelayed(START_ACTIVITY, 10 * 1000);
                } else {
                    removeStartActivity();
                    startActivity(new Intent(this, BrushInterstitialActivity.class));
                }
            } else {
                removeStartActivity();
            }
        } else if (START_AD_ACTIVITY == msg.what) {
            if (isOnPause || isMorePause) {
                mHandler.removeMessages(START_AD_ACTIVITY);
                mHandler.sendEmptyMessageDelayed(START_AD_ACTIVITY, 10 * 1000);
            } else {
                restartActivity();
            }
        }else if(GDTInterstitialAD_LOADAD_IAD == msg.what){
            createGDTInterstitialADAsPopup();
        }else if(GDTInterstitialAD_CLOSE_IAD == msg.what){
            for (String s : mapGDTInterstitialAD.keySet()) {
                GDTInterstitialAD gdtInterstitialAD = mapGDTInterstitialAD.get(s);
                gdtInterstitialAD.onDestroy();
            }
        }else if(TIMER_SHOW == msg.what){
            if (listString != null && listString.size() != 0){
                if (isOnPause){
                    mHandler.removeMessages(TIMER_CLOSE);
                    mHandler.removeMessages(TIMER_SHOW);
                    mHandler.sendEmptyMessageDelayed(TIMER_SHOW, 5 * 1000);
                    return;
                }
                String s = removeString0();
                if (!TextUtils.isEmpty(s)){
                    GDTInterstitialAD gdtInterstitialAD = mapGDTInterstitialAD.get(s);
                    mGDTInterstitialAD =  gdtInterstitialAD;
                    gdtInterstitialAD.showAsPopupWindow();
                    String interteristalPosID = gdtInterstitialAD.getInterteristalPosID();
                    ToastUtil.toastSome(this,"显示：\t"+interteristalPosID);
                }
            }
            timerClose();
        }else if(TIMER_CLOSE == msg.what){
            if (isMorePause){
                timerClose();
                return;
            }

            if (mGDTInterstitialAD != null)mGDTInterstitialAD.closeAsPopup();
            if (listString != null && listString.size() != 0){
                mHandler.removeMessages(TIMER_CLOSE);
                mHandler.removeMessages(TIMER_SHOW);
                mHandler.sendEmptyMessageDelayed(TIMER_SHOW,1000);
            }else{
                timerShow();
            }
        }else if(ADD_GDT_BANNER_VIEW == msg.what){
            ToastUtil.toastSome(this,"开始刷新广点通广告条");
            addGdtBannerView();
        }
    }

    public static final String IS_START_ACTIVITY_10_KEY = "IS_START_ACTIVITY_10_KEY";
    public static final String IS_START_ACTIVITY_5_KEY = "IS_START_ACTIVITY_5_KEY";

//    public static final String Is_MorePause_KEY = "Is_MorePause_KEY";

    public static final String IS_isMore_KEY = "IS_isMore_KEY";

    public static final String IS_Restart_KEY = "IS_Restart_KEY";

    public static final String IS_BRUSH_KEY = "IS_BRUSH_KEY";

    private boolean isOpenSwitch = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBrushInterstitialSwitch:
                if (v instanceof TextView) {
                    synchronized (this) {
                        TextView tv = (TextView) v;
                        String trim = tv.getText().toString().trim();
                        removeStartActivity();
                        if (trim.equals("开")) {
                            isOpenSwitch = true;
                            mHandler.sendEmptyMessageDelayed(START_ACTIVITY, 1000);
                            tv.setText("关");
                        } else if (trim.equals("关")) {
                            isOpenSwitch = false;
                            tv.setText("开");
                        } else {
                            startActivity(new Intent(this, BrushInterstitialActivity.class));
                        }
                    }
                }
                break;
            case R.id.tvRestart:
                if (v instanceof TextView) {
                    synchronized (this) {
                        restartActivity();
                    }
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent instanceof AppCompatSpinner) {

            FrameLayout fl_ad = (FrameLayout) findViewById(R.id.fl_ad);
            String[] stringArray = getResources().getStringArray(R.array.ad_gdt_google_spinner);
            if (stringArray.length == 0) return;
            View[] views = new View[stringArray.length];
            for (int i = 0; i < fl_ad.getChildCount(); i++) {
                View childAt = fl_ad.getChildAt(i);
                if (childAt.getId() == R.id.ll_gdt) {
                    views[0] = childAt;
                } else if (childAt.getId() == R.id.ll_gdt2) {
                    views[1] = childAt;
                } else if (childAt.getId() == R.id.ll_google) {
                    views[2] = childAt;
                } else if (childAt.getId() == R.id.ll_google2) {
                    views[3] = childAt;
                } else if (childAt.getId() == R.id.ll_360) {
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
        ToastUtil.toastSome(this, "onNothingSelected");
    }

    private boolean isBrush = false;

    private boolean isMore = false;

    private boolean isMorePause = false;

    private boolean isShow360 = false;

    private boolean isShowGoogle = false;

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rbOpen) {
            isBrush = true;
            ToastUtil.toastSome(this, "刷插屏缩短至10秒");
            if (mHandler.hasMessages(SHOW_IAD)) {
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(SHOW_IAD, 10 * 1000);
            }

        } else if (checkedId == R.id.rbClose) {
            ToastUtil.toastSome(this, "刷插屏关闭");
            isBrush = false;
            if (mHandler.hasMessages(CLOSE_IAD)) {
                removeCloseShow();
                mHandler.sendEmptyMessageDelayed(CLOSE_IAD, 50 * 1000);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.cb_open10 || buttonView.getId() == R.id.cb_open5){
            if (isChecked){
                if (mCbOpen5.isChecked() && mCbOpen10.isChecked()){
                    buttonView.setChecked(false);
                    ToastUtil.toastSome(this, "定时启动只能选择\t5s\t或\t10s");
                    return;
                }
                if (buttonView.getId() == R.id.cb_open10 && mCbOpen5.isChecked()){
                    buttonView.setChecked(false);
                    return;
                }
                if (buttonView.getId() == R.id.cb_open5 && mCbOpen10.isChecked()){
                    buttonView.setChecked(false);
                    return;
                }

                ToastUtil.toastSome(this, ((mCbOpen5.isChecked() ? START_AD_ACTIVITY_TIME_5 : START_AD_ACTIVITY_TIME_10)/1000)+"秒后重新启动");
                synchronized (this) {
                    if (!mHandler.hasMessages(START_AD_ACTIVITY)) {
                        mHandler.removeMessages(START_AD_ACTIVITY);
                        mHandler.sendEmptyMessageDelayed(START_AD_ACTIVITY, mCbOpen5.isChecked() ? START_AD_ACTIVITY_TIME_5 : START_AD_ACTIVITY_TIME_10);
                    }
                }
            }else {
                if (mCbOpen5.isChecked() || mCbOpen10.isChecked()){
                    return;
                }
                ToastUtil.toastSome(this, "关闭定时启动");
                mHandler.removeMessages(START_AD_ACTIVITY);
            }
        }else if (buttonView.getId() == R.id.cb_more){
            isMore = isChecked;
            if (isChecked){
                closeAsPopup();
                removeCloseShow();
                ToastUtil.toastSome(this,"3 秒后多插屏开始");
                mHandler.removeMessages(GDTInterstitialAD_LOADAD_IAD);
                mHandler.sendEmptyMessageDelayed(GDTInterstitialAD_LOADAD_IAD,3 * 1000);
            }else {
                mHandler.removeMessages(TIMER_CLOSE);
                mHandler.removeMessages(TIMER_SHOW);
                mHandler.removeMessages(GDTInterstitialAD_LOADAD_IAD);
                mHandler.removeMessages(GDTInterstitialAD_CLOSE_IAD);
                removeCloseShow();
                ToastUtil.toastSome(this,"5 秒后开始显示");
                mHandler.sendEmptyMessageDelayed(GDTInterstitialAD_CLOSE_IAD,500);

                mHandler.removeMessages(CLOSE_IAD);
                mHandler.removeMessages(SHOW_IAD);
                mHandler.sendEmptyMessageDelayed(SHOW_IAD,5 * 1000);
            }
        }else if (buttonView.getId() == R.id.cb_360){
            isShow360 = isChecked;
            if (isChecked){

            }else {

            }
        }else if (buttonView.getId() == R.id.cb_google){
            isShowGoogle = isChecked;
            if (isChecked){

            }else {

            }
        }else if (buttonView.getId() == R.id.cb_pause){
            isMorePause = isChecked;
            if (isChecked){
                ToastUtil.toastSome(this,"暂停消失插屏");
            }else {
                ToastUtil.toastSome(this,"插屏取消暂停");
            }
        }else if(buttonView.getId() == R.id.cb_brush){
            if (isChecked){
                if (!mHandler.hasMessages(ADD_GDT_BANNER_VIEW)){
                    ToastUtil.toastSome(this,"5 秒后刷新广点通广告条");
                    mHandler.removeMessages(ADD_GDT_BANNER_VIEW);
                    mHandler.sendEmptyMessageDelayed(ADD_GDT_BANNER_VIEW,5 * 1000);
                }else {
                    buttonView.setChecked(false);
                }
            }else {
                ToastUtil.toastSome(this,"刷新广点通广告条\t完成");
                mHandler.removeMessages(ADD_GDT_BANNER_VIEW);
            }
        }
    }

    private void removeString(String gDTInterstitialAD){
        synchronized (listString){
            listString.remove(gDTInterstitialAD);
        }
    }
    private String removeString0(){
        synchronized (listString){
            if (listString.size() != 0){
               return listString.remove(0);
            }else return null;
        }
    }

    private void addString(String gDTInterstitialAD){
        synchronized (listString){
            listString.remove(gDTInterstitialAD);
            listString.add(gDTInterstitialAD);
        }
    }


    @Override
    public void CallBack(GDTInterstitialAD gDTInterstitialAD, GDTInterstitialAD.ADState aDState, String interteristalPosID) {
       if (aDState ==  GDTInterstitialAD.ADState.onADReceive){
           ToastUtil.toastSome(this,interteristalPosID+"\t  广告获取成功");
           addString(interteristalPosID);
       }else {
           ToastUtil.toastSome(this,interteristalPosID+"\t ====== "+aDState);
           removeString(interteristalPosID);
       }
    }
}
