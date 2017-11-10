package com.zhaoyao.miaomiao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mediav.ads.sdk.adcore.Mvad;
import com.mediav.ads.sdk.interfaces.IMvAdEventListener;
import com.mediav.ads.sdk.interfaces.IMvBannerAd;
import com.mediav.ads.sdk.interfaces.IMvInterstitialAd;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class Ad360Activity extends AppCompatActivity {

    private List<IMvBannerAd> listIMvBannerAd360 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad360);

        Add360Ad1();
        for (IMvBannerAd iMvBannerAd : listIMvBannerAd360) {
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


    }


    private void Add360Ad1() {
        listIMvBannerAd360.clear();
        LinearLayout ll_360 = (LinearLayout) findViewById(R.id.ll_360);
        for (int i = 0; i < ll_360.getChildCount(); i++) {
            View childAt = ll_360.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                IMvBannerAd bannerad = null;
                if (i == 0) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID, false);
                } else if (i == 1) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID2, false);
                } else if (i == 2) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID3, false);
                } else if (i == 3) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID4, false);
                } else if (i == 4) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID5, false);
                } else if (i == 5) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID6, false);
                } else if (i == 6) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID7, false);
                } else if (i == 7) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID8, false);
                } else if (i == 8) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID9, false);
                } else if (i == 9) {
                    bannerad = Mvad.showBanner((ViewGroup) childAt, this, Constants.Banner360ID10, false);
                }
                if (bannerad != null)
                    listIMvBannerAd360.add(bannerad);
            }
        }
    }

    public void InterstitialAd(){
        //插屏下
        IMvInterstitialAd mediavAdView = Mvad.showInterstitial(this, "", true);
        mediavAdView.setAdEventListener(new IMvAdEventListener() {
            //获取广告成功
            public void onAdviewGotAdSucceed(){

            }
            //获取广告失败
            public void onAdviewGotAdFail(){

            }
            //进入落地页
            public void onAdviewIntoLandpage(){

            }
            //离开落地页
            public void onAdviewDismissedLandpage(){

            }
            //广告被点击
            public void onAdviewClicked(){

            }
            //广告被关闭
            public void onAdviewClosed(){

            }
            //当广告实例被销毁
            public void onAdviewDestroyed(){

            }
        });
    }

    /**
     * 360横幅关闭
     */
    private void doCloseIMvBannerAdBanner() {
        for (IMvBannerAd bannerView : listIMvBannerAd360) {
            bannerView.closeAds();
        }
    }




    @Override
    protected void onDestroy() {
        doCloseIMvBannerAdBanner();
        Mvad.activityDestroy(this);
        super.onDestroy();
    }
}
