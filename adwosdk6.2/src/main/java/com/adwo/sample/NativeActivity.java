package com.adwo.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.sixth.adwoad.ErrorCode;
import com.sixth.adwoad.NativeAdListener;
import com.sixth.adwoad.NativeAdView;

import org.json.JSONObject;

/**
 * @author ADWO 此实例为adwoNative广告展示。
 *         adwo全屏为了能使广告更快，更好的展示，采用了外部存储卡存储机制。所有的广告资源都存放在手机外部存储卡的adwo目录下。
 *         adwo全屏广告从请求到销毁完全按照Android程序构架机制设计
 *         有初始化，设置参数，请求，加载，请求加载成功失败回调接口，展示和销毁等接口。
 *         
 *         这种形式的广告的填充率比较有限，建议开发者嵌入信息流广告
 */
public class NativeActivity extends Activity implements NativeAdListener {
	
	public static String url = null;
	public static byte FS_DESIRE_AD_FORM = 0;
	private String LOG_TAG = "Adwo Native ad";

	private RelativeLayout layout;
	
	ImageView imageView ;
	private NativeAdView ad;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.embed);
		
		layout = (RelativeLayout) findViewById(R.id.layout);
		
		// isTest=true 时，有测试广告，正式发布需要修改为 false
		ad = new NativeAdView(this,"7600d58ef5e449cc81e269d685d6c94f",true,this);
		 
		// 开始请求广告
		ad.prepareAd();
		
		imageView = new ImageView(this);
		
		layout.addView(imageView);
//		ad.setBackgroundColor(Color.TRANSPARENT);
//		imageView.setBackgroundColor(Color.GREEN);
		layout.addView(ad);

		
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onDestroy() {
		super.onDestroy();

		
		Log.e(LOG_TAG,imageView.getX()+" X onDestroy Y:"+imageView.getY()+ "imageView.getWidth:"+imageView.getWidth()+"  imageView.getHeight:"+imageView.getHeight());
		
		Log.e(LOG_TAG,ad.getX()+" X Y:"+ad.getY()+  "ad.getWidth:"+ad.getWidth()+"  ad.getHeight:"+ad.getHeight());
		
		
		Log.e(LOG_TAG, "onDestroy");
		// 请在这里释放全屏广告资源
		if (ad != null) {
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
	public void onDismissScreen() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFailedToReceiveAd(View arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPresentScreen() {
		// TODO Auto-generated method stub
		
	}
	@SuppressLint("NewApi")
	@Override
	public void onReceiveAd(String adInfo) {
		try {
			Log.e(LOG_TAG,  " onReceiveAd:"+adInfo);
			JSONObject json = new JSONObject(adInfo);
			DisplayMetrics metrics = getResources().getDisplayMetrics();
			int imgwidth = json.getInt("imgwidth");
			int imgheight = json.getInt("imgheight");
			LayoutParams params = new LayoutParams(imgwidth,imgheight);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			
			imageView.setLayoutParams(params);
			ad.setLayoutParams(params);
			
			DownloadImageTask task = new DownloadImageTask(imageView);
			String imgurl = json.getString("img");
			int ln = imgurl.lastIndexOf("/");
			String filename = imgurl.substring(ln + 1);
			String[] urlContect = imgurl.split("/");
			String adid = urlContect[urlContect.length-2];
//			为了避免广告图片相互覆盖建议在存储图片名加上广告id。
			task.execute(Environment.getExternalStorageDirectory()+"/"+adid+filename,imgurl );
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
