package com.adwo.sample;


import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;


 
import com.sixth.adwoad.ErrorCode;
import com.sixth.adwoad.NativeAdListener;
import com.sixth.adwoad.NativeAdView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * @author ADWO 此实例为adwoNative Feeds（信息流）广告展示。
 *         adwo 为了能使广告更快，更好的展示，采用了外部存储卡存储机制。所有的广告资源都存放在手机外部存储卡的adwo目录下。
 *         adwo native广告从请求到销毁完全按照Android程序构架机制设计
 *         。有初始化，设置参数，请求，加载，请求加载成功失败回调接口，展示和销毁等接口。
 *         
 *         

 *    信息流广告嵌入要点：
 *    1）   NativeAdView是所有的安沃原生广告载体，无论是信息流还是焦点图等其他形式，所以第一步是创建NativeAdView对象feedAd
 *    2）在需要展示广告的地方进行广告数据的请求，即 prepareAd。开发者按照自己定义信息流样式来创建广告展示的基本容器，如feedlayout
 *    3）在广告数据的回调接口中，对安沃原生广告的接口数据adInfo进行解析,得到信息流广告的展示需要的基本元素，如icon、title、desc
 adinfo json示例     {"icon":"http://static.adwo.com/upload/20150514/24413/icon75x75.png","iconwidth":75,"iconheight":75,
 *        "title":"测试","summary":"测试广告","desc":"测试广告详情","img":"http://static.adwo.com/upload/20150514/24413/image644x280.png","imgwidth":644,"imgheight":280,
 *        "ext":{"button":"http://static.adwo.com/template/banner/button.png","star":4,"size":"3.4M","downloadcount":60}}
 *    4）开发者将adinfo中的数据 赋值给    feedlayout 中各个组件做好展示准备
 *    5）开发者将feedlayout  和feedAd 依次添加进入父视图adParentlayout，需要注意的是原生广告对象feedAd需要获得点击焦点，否则将不可点击计数。
 *    同时要保证 feedlayout 和 feedAd 视图区域大小相等，所以建议adParentlayout的布局为FrameLayout
 *    
 *     

 */
public class FeedsActivity extends Activity implements NativeAdListener {
	
	public static String url = null;
	public static byte FS_DESIRE_AD_FORM = 0;
	private String LOG_TAG = "Adwo Feeds ad";

	private FrameLayout adParentlayout;
 
	private NativeAdView feedAd;
	FrameLayout feedlayout ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.feeds);
		
		adParentlayout = (FrameLayout) findViewById(R.id.layout);
		
		// isTest=true 时，有测试广告，正式发布需要修改为 false
		feedAd = new NativeAdView(this,"2b8dbd92edd74a97b3ba6b0189bef125",false,this);
		 
		// 开始请求广告
		feedAd.prepareAd();
		
		LayoutInflater inflater = getLayoutInflater();  
	    feedlayout =  (FrameLayout)inflater.inflate(R.layout.feedsitem, null);  
 
//		ad.setBackgroundColor(Color.GREEN);//调试广告view大小
		//开发者可以选择在回调里面去添加父容器进rootview
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		feedAd.setLayoutParams(params);
	 	//feedlayout.setBackgroundColor(Color.RED);
	    adParentlayout.addView(feedlayout);		
	    //在父布局上先添加开发者编写的信息流元素的广告布局feedlayout，再添加feedAd,好处是feedAd层上动态效果不会被遮盖。
	 	adParentlayout.addView(feedAd  );
		adParentlayout.setBackgroundColor(Color.GRAY);//原生广告的父容器view背景色，调试完可以去掉


	    
	    
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onDestroy() {
		super.onDestroy();

	 
		//Log.e(LOG_TAG,feedAd.getX()+" X Y:"+feedAd.getY()+  "ad.getWidth:"+feedAd.getWidth()+"  ad.getHeight:"+feedAd.getHeight());
		
		
		Log.e(LOG_TAG, "onDestroy");
		// 请在这里释放广告资源
		if (feedAd != null) {
			feedAd = null;
			
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
//			int imgwidth =50;
//			int imgheight=50;
			String  title="";
			String  info="";//广告详细描述
			String  summ="";//开发者根据需要选择
			String imgurl = "";//信息流中的小icon的url
			JSONObject json = null;
			try{
		     json = new JSONObject(adInfo);
 
			  title=json.getString("title");
			//  imgwidth = json.getInt("iconwidth");//这个字段可能没有值
			//  imgheight = json.getInt("iconheight");//icon的大小开发者可以参考，直接在布局里面进行大小控制

			}catch(JSONException ex){
				//如果某些json对象解析出现异常，可以忽略，继续展示广告
				//一般的，原生广告的title是必须有的
			}
			
			try{
 
				  info=json.getString("desc");//广告的"描述"字段如果没有我们继续尝试"详情"字段
 
				}catch(JSONException ex){
					//如果某些json对象解析出现异常，可以忽略，继续展示广告
					try{
					summ=json.getString("summary");	
					} catch(JSONException ex2){
						
					}
				}
			
			try{
					//  优先尝试广告图片的字段，再尝试icon的字段有没有数据
					  imgurl = json.getString("img");
				}catch(JSONException ex){
					//如果某些json对象解析出现异常，可以忽略，继续展示广告
					try{
						imgurl = json.getString("icon");//这里icon可能为空
					} catch(JSONException ex2){
						
					}
				}
			
			
			ImageView imageView=(ImageView)feedlayout.findViewById(R.id.iv_img);
			
			//use LayoutParams in xml
            if(imgurl.startsWith("http")){
			DownloadImageTask task = new DownloadImageTask(imageView);
			
			int ln = imgurl.lastIndexOf("/");
			String filename = imgurl.substring(ln + 1);
			String[] urlContect = imgurl.split("/");
			String adid = urlContect[urlContect.length-2];
//			为了避免广告图片相互覆盖建议在存储图片名加上广告id。
			task.execute(Environment.getExternalStorageDirectory()+"/"+adid+filename,imgurl );
            }
			
			TextView titleView=(TextView)feedlayout.findViewById(R.id.tv_title);
			titleView.setText(title);
			
			TextView infoView=(TextView)feedlayout.findViewById(R.id.tv_info);
			infoView.setText(info);
			 
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}