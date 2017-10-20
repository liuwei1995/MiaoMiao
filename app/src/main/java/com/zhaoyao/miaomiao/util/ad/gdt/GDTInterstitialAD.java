package com.zhaoyao.miaomiao.util.ad.gdt;

import android.app.Activity;
import android.util.Log;

import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.ads.interstitial.InterstitialADListener;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.util.Constants;

/**
 * Created by liuwei on 2017/10/20 17:07
 */

public class GDTInterstitialAD implements InterstitialADListener {


    public interface GDTInterstitialADListener{

        void CallBack(GDTInterstitialAD gDTInterstitialAD,ADState aDState,String interteristalPosID);//回调

    }


    private Activity mActivity;

    private String interteristalPosID = Constants.InterteristalPosID;

    private GDTInterstitialADListener mGDTInterstitialADListener;

    public GDTInterstitialAD(Activity mActivity, String interteristalPosID) {
        this(mActivity,interteristalPosID,null);
    }

    public GDTInterstitialAD(Activity mActivity, String interteristalPosID,GDTInterstitialADListener mGDTInterstitialADListener) {
        this.mActivity = mActivity;
        this.interteristalPosID = interteristalPosID;
        this.mGDTInterstitialADListener = mGDTInterstitialADListener;
        iad = getIAD();
    }


    private InterstitialAD iad;

    private synchronized InterstitialAD getIAD() {
        if (iad == null) {
            synchronized (this) {
                if (iad == null) {
                    iad = new InterstitialAD(mActivity, Constants.APPID, interteristalPosID);
                    iad.setADListener(this);
                }
            }
        }
        return iad;
    }

    public void loadAD(){
        iad.loadAD();
    }

    public void show(){
        iad.show();
    }

    public void destroy(){
        iad.destroy();
    }

    public void setADListener(InterstitialADListener mInterstitialADListener){
        iad.setADListener(mInterstitialADListener);
    }

    public void closeAsPopup() {
        if (iad != null) {
            iad.closePopupWindow();
            iad.destroy();
        }
    }


    enum ADState{
        onADReceive,onNoAD,onADClosed
    }

    private ADState mADState = ADState.onNoAD;

    public synchronized ADState getADState() {
        return mADState;
    }

    public synchronized void setADState(ADState mADState) {
        if (this.mADState == mADState)return;
        this.mADState = mADState;
    }


    public String getInterteristalPosID() {
        return interteristalPosID;
    }

    @Override
    public void onADReceive() {
        Log.i("AD_DEMO", "onADReceive");
        setADState(ADState.onADReceive);
        if (mGDTInterstitialADListener != null)
        mGDTInterstitialADListener.CallBack(this, ADState.onADReceive, interteristalPosID);
//        iad.show();
    }

    @Override
    public void onNoAD(AdError error) {
        setADState(ADState.onNoAD);
        if (mGDTInterstitialADListener != null)
        mGDTInterstitialADListener.CallBack(this, ADState.onNoAD, interteristalPosID);
    }

    @Override
    public void onADOpened() {

    }

    @Override
    public void onADExposure() {

    }

    @Override
    public void onADClicked() {

    }

    @Override
    public void onADLeftApplication() {

    }

    @Override
    public void onADClosed() {
        setADState(ADState.onADClosed);
        if (mGDTInterstitialADListener != null)
        mGDTInterstitialADListener.CallBack(this, ADState.onADClosed, interteristalPosID);
    }
}
