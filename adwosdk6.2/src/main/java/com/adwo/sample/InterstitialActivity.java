package com.adwo.sample;

 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sixth.adwoad.ErrorCode;
import com.sixth.adwoad.InterstitialAd;
import com.sixth.adwoad.InterstitialAdListener;

/**
 * @author ADWO 此实例为adwo全屏广告展示。
 *         adwo全屏为了能使广告更快，更好的展示，采用了外部存储卡存储机制。所有的广告资源都存放在手机外部存储卡的adwo目录下。
 *         adwo全屏广告从请求到销毁完全按照Android程序构架机制设计
 *         。有初始化，设置参数，请求，加载，请求加载成功失败回调接口，展示和销毁等接口。
 */
public class InterstitialActivity extends Activity implements InterstitialAdListener {
	
	public static String url = null;
	public static byte FS_DESIRE_AD_FORM = 0;
	private String LOG_TAG = "Adwo full-screen ad";

	private RelativeLayout layout;
	
	public static TextView adCount ;
	public static TextView adBeaconCount;
	
	private InterstitialAd ad;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
		layout = (RelativeLayout) findViewById(R.id.layout);
		adCount = (TextView) findViewById(R.id.adCount);
		adBeaconCount = (TextView) findViewById(R.id.adBeaconCount);
		adCount.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
		}});
		
		Intent intent = this.getIntent();
		url = intent.getStringExtra("url");

		//全屏广告实例   2ff473948b2d4dca80b845d368789e53
		ad = new InterstitialAd(this,"7600d58ef5e449cc81e269d685d6c94f",false,this);
		// 设置全屏格式
		ad.setDesireAdForm(FS_DESIRE_AD_FORM);
		// 设置请求广告类型 可选。
		ad.setDesireAdType((byte) 0);
		// 开始请求全屏广告
		ad.prepareAd();
		Log.e(LOG_TAG, "onCreate");

	}
	
	@Override
	public void onReceiveAd() {
		Log.e(LOG_TAG, "onReceiveAd");
	}

	@Override
	public void onLoadAdComplete() {
		Log.e(LOG_TAG, "onLoadAdComplete");
		// 成功完成下载后，展示广告
		ad.displayAd();
	}

	@Override
	public void onFailedToReceiveAd(ErrorCode errorCode) {
		Log.e(LOG_TAG, "onFailedToReceiveAd");
	}

	@Override
	public void onAdDismiss() {
		Log.e(LOG_TAG, "onAdDismiss");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(LOG_TAG, "onDestroy");
		// 请在这里释放全屏广告资源
		if (ad != null) {
			ad.dismiss();
			ad = null;
		}
	}
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		// 开始请求广告
		Log.e(LOG_TAG, "onAttachedToWindow");

	}

	@Override
	public void OnShow() {
		// TODO Auto-generated method stub
		
	}

	
}
