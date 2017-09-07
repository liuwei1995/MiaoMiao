package com.baidu.integrationsdk.lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BDSplashAdActivity extends AppCompatActivity {

    BDSplash bdSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdsplash_ad);
        bdSplash = new BDSplash(this, "");
        bdSplash.loadAd();
        boolean b = bdSplash.showAd();
        if (!b){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        bdSplash.destroy();
        super.onDestroy();
    }
}
