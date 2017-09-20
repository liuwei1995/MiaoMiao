package com.zhaoyao.miaomiao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.zhaoyao.miaomiao.R;

public class GoogleAdActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_ad);
        MobileAds.initialize(this, "ca-app-pub-2850046637182646~7046734019");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        LinearLayout viewById = (LinearLayout) findViewById(R.id.ll);

        if (viewById != null && viewById.getChildCount() > 0) {
            for (int i = 0; i < viewById.getChildCount(); i++) {
                View childAt1 = viewById.getChildAt(i);
                if (childAt1 instanceof AdView){
                    AdView mAdView = (AdView) childAt1;
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }
            }
        }

//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-2850046637182646/4838025532");


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2850046637182646/7096700823");
        mInterstitialAd.setAdListener(new MyAdListener());
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public class MyAdListener extends AdListener {
        @Override
        public void onAdLoaded() {
            // Code to be executed when an ad finishes loading.
            Log.i("Ads", "onAdLoaded");
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            // Code to be executed when an ad request fails.
            Log.i("Ads", "onAdFailedToLoad");
        }

        @Override
        public void onAdOpened() {
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
            Log.i("Ads", "onAdOpened");
        }

        @Override
        public void onAdLeftApplication() {
            // Code to be executed when the user has left the app.
            Log.i("Ads", "onAdLeftApplication");
        }

        @Override
        public void onAdClosed() {
            // Code to be executed when when the user is about to return
            // to the app after tapping on an ad.
            Log.i("Ads", "onAdClosed");
        }
    }
}
