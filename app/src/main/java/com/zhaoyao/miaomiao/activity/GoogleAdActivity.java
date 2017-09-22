package com.zhaoyao.miaomiao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.listener.GoogleAdListener;

public class GoogleAdActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_ad);
        MobileAds.initialize(this.getApplicationContext(), "ca-app-pub-2850046637182646~7046734019");
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
        mInterstitialAd.setAdListener(new GoogleAdListener(mInterstitialAd.getAdUnitId()));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

}
