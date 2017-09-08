package com.adwo.sample;


 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.sixth.adwoad.AdListener;
import com.sixth.adwoad.AdwoAdView;
import com.sixth.adwoad.ErrorCode;

/**
 *  ADWO 此实例是adwo广告条展示实例，adwo广告全部默认宽都是和设备宽相等，也可以自行设置广告条的宽和高。
 *  adwo广告条添加嵌入遵循代码嵌入简单，广告大小适宜，广告资源消耗小等原则。
 */
public class BannerActivity extends Activity implements AdListener{
	public static RelativeLayout layout;
	static AdwoAdView adView = null;
	String Adwo_PID = "7600d58ef5e449cc81e269d685d6c94f";
	LayoutParams params = null;
	private String LOG_TAG = "Adwo";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.banner);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		layout = (RelativeLayout) findViewById(R.id.layout);
		
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		当不设置广告条充满屏幕宽时建议放置在父容器中间
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		

		
		// 实例化广告对象
		adView = new AdwoAdView(BannerActivity.this, Adwo_PID,false, 20);

//		adwo广告条的宽高默认20*50乘以屏幕密度，默认宽是不充满屏宽，如果您想设置设置广告条宽充满屏幕宽您可以在实例化广告对象之前调用AdwoAdView.setBannerMatchScreenWidth(true)
//		设置广告条充满屏幕宽
//		adView.setBannerMatchScreenWidth(true);
//		设置单次请求
//		adView.setRequestInterval(0);
		//如果你有合作渠道，请设置合作渠道id，具体id值请联系安沃工作人员 。可选择设置
//		adView.setMarketId((byte) 9);
		// 设置广告监听回调
		adView.setListener(BannerActivity.this);
		// 把广告条加入界面布局
		layout.addView(adView, params);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(LOG_TAG, "onDestroy");
	}
	
	@Override
	public void onReceiveAd(Object arg0) {
		Log.e(LOG_TAG, "onReceiveAd");
	}
	
	@Override
	public void onFailedToReceiveAd(View adView, ErrorCode errorCode) {
		Log.e(LOG_TAG, "onFailedToReceiveAd");
	}


	@Override
	public void onDismissScreen() {
		Log.e(LOG_TAG, "onDismissScreen");
	}
	
	@Override
	public void onPresentScreen() {
		Log.e(LOG_TAG, "onPresentScreen");
	}

}
