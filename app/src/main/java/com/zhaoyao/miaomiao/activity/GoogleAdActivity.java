package com.zhaoyao.miaomiao.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.listener.GoogleAdListener;

import java.util.ArrayList;
import java.util.List;

public class GoogleAdActivity extends AppCompatActivity implements GoogleAdListener.OnAdListener, RadioGroup.OnCheckedChangeListener {

    private InterstitialAd mInterstitialAd;

    private List<AdView> listAdViewGoogle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_ad);
        MobileAds.initialize(this.getApplicationContext(), "ca-app-pub-2850046637182646~7046734019");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

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
        RadioGroup rg_ = (RadioGroup) findViewById(R.id.rg);
        rg_.setOnCheckedChangeListener(this);

//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-2850046637182646/4838025532");


//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-2850046637182646/7096700823");
//        mInterstitialAd.setAdListener(new GoogleAdListener(mInterstitialAd.getAdUnitId(),this));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
//        IMvInterstitialAd mediavAdView = Mvad.showInterstitial(this, "", true);
//
//        mediavAdView.showAds(this);
    }

    @Override
    public void onAdLoaded() {
        if (mInterstitialAd != null)
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
    }

    @Override
    protected void onDestroy() {
        for (AdView adView : listAdViewGoogle) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int position = 0;
        if (checkedId == R.id.rbAdMob1) {
            position = 0;
        } else if (checkedId == R.id.rbAdMob2) {
            position = 1;
        }
        FrameLayout fl_ad = (FrameLayout) findViewById(R.id.fl_ad);
        View[] views = new View[2];
        for (int i = 0; i < fl_ad.getChildCount(); i++) {
            View childAt = fl_ad.getChildAt(i);
            if (childAt.getId() == R.id.ll_google) {
                views[0] = childAt;
            } else if (childAt.getId() == R.id.ll_google2) {
                views[1] = childAt;
            }
        }
        fl_ad.removeViewInLayout(views[position]);
        fl_ad.addView(views[position]);
        fl_ad.invalidate();
    }
}
