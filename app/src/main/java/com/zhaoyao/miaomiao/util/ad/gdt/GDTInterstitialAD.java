package com.zhaoyao.miaomiao.util.ad.gdt;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.ads.interstitial.InterstitialADListener;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.handler.TaskHandler;
import com.zhaoyao.miaomiao.handler.TaskHandlerImpl;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.LogUtils;
import com.zhaoyao.miaomiao.util.ToastUtil;

import java.lang.ref.WeakReference;

/**
 * Created by liuwei on 2017/10/20 17:07
 */

public class GDTInterstitialAD implements InterstitialADListener ,TaskHandler {



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

    public synchronized void loadAD(){
        if (isDestroy)return;
        if (mADState == ADState.onADReceive){
            ToastUtil.toastSome(mActivity,"不用重复  loadAD:\t"+getInterteristalPosID());
            return;
        }
        ToastUtil.toastSome(mActivity,"loadAD:\t"+getInterteristalPosID());
        iad.loadAD();
//        if (mADState != ADState.show && mADState != ADState.loadAD && mADState != ADState.onADReceive){
//            ToastUtil.toastSome(mActivity,"loadAD:\t"+getInterteristalPosID());
//            iad.loadAD();
//        }else {
//            ToastUtil.toastSome(mActivity,"ADState:\t"+mADState);
//        }
    }

    public void showAsPopupWindow(){
        setADState(ADState.show);
        iad.showAsPopupWindow();
    }

    private void destroy(){
        iad.destroy();
    }

    public void setADListener(InterstitialADListener mInterstitialADListener){
        iad.setADListener(mInterstitialADListener);
    }

    public void closeAsPopup() {
        if (iad != null) {
            iad.closePopupWindow();
        }
    }

    public Handler mHandler = new TaskHandlerImpl(this);

    public static final int CLOSE_IAD = 1;
    public static final int SHOW_IAD = 2;

    public static final int LOAD_AD_IAD = 3;

    public static final int ON_AD_RECEIVE = 4;

    public static final int ON_NO_AD = 5;

    public static final int ON_AD_CLOSED = 6;

    public void sendEmptyMessageDelayed(int what, long delayMillis) {
        mHandler.removeMessages(what);
        mHandler.sendEmptyMessageDelayed(what,delayMillis);
    }

    @Override
    public void handleMessage(WeakReference weakReference, Message msg) {
        if (isDestroy)
        {
            mHandler.removeCallbacksAndMessages(null);
            return;
        }
        if (LOAD_AD_IAD == msg.what){
            loadAD();
        }else if (ON_AD_RECEIVE == msg.what){
            if (mGDTInterstitialADListener != null)
                mGDTInterstitialADListener.CallBack(this, ADState.onADReceive, interteristalPosID);
        }else if (ON_NO_AD == msg.what){
            if (mGDTInterstitialADListener != null)
                mGDTInterstitialADListener.CallBack(this, ADState.onNoAD, interteristalPosID);
            if(!isDestroy)
                sendEmptyMessageDelayed(LOAD_AD_IAD, 5 * 1000);
        }else if (ON_AD_CLOSED == msg.what){
            if(!isDestroy)
                sendEmptyMessageDelayed(LOAD_AD_IAD, 2000);

//            if (mGDTInterstitialADListener != null)
//                mGDTInterstitialADListener.CallBack(this, ADState.onADClosed, interteristalPosID);
        }
    }


    private boolean isDestroy = false;

    public synchronized void onDestroy() {
        isDestroy = true;
        mHandler.removeCallbacksAndMessages(null);
        mGDTInterstitialADListener = null;
        closeAsPopup();
        destroy();
    }

   public enum ADState{
        onADReceive,onNoAD,onADClosed,loadAD,show
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
        setADState(ADState.onADReceive);
        if (isDestroy)return;
        mHandler.removeMessages(ON_AD_RECEIVE);
        mHandler.sendEmptyMessageDelayed(ON_AD_RECEIVE,1000);
    }

    @Override
    public void onNoAD(AdError error) {
        setADState(ADState.onNoAD);
        if (isDestroy)return;
        mHandler.removeMessages(ON_NO_AD);
        mHandler.sendEmptyMessageDelayed(ON_NO_AD,1000);
    }

    @Override
    public void onADOpened() {
        LogUtils.d("onADOpened");
    }

    @Override
    public void onADExposure() {
        LogUtils.d("onADExposure");
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
        if (isDestroy)return;
        mHandler.removeMessages(ON_AD_CLOSED);
        mHandler.sendEmptyMessageDelayed(ON_AD_CLOSED,1000);
    }
}
