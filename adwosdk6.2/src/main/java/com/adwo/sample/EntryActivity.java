package com.adwo.sample;


import java.io.File;

 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Adwo 广告展示入口界面
 */
public class EntryActivity extends Activity {
	private Button bannerButton;
	private Button intersitialAdButton;
	private Button entryAdButton;
	private Button nativeAdButton;
	private Button feedsAdButton;
	RelativeLayout layout;
	private native String getKey(long data);
	static String soFileName = null;
	static String defaultFilePath = null;
	
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.entry);
        bannerButton = (Button) findViewById(R.id.bannerButton);
        intersitialAdButton = (Button) findViewById(R.id.intersitialAdButton);
        entryAdButton = (Button) findViewById(R.id.entryAdButton);
        nativeAdButton = (Button) findViewById(R.id.nativeAdButton);
        feedsAdButton = (Button) findViewById(R.id.feedsAdButton);
        layout = (RelativeLayout) findViewById(R.id.layout);
//        banner广告跳转
        bannerButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(EntryActivity.this, BannerActivity.class);
				startActivity(intent);
		}});
//        插屏广告跳转
        intersitialAdButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EntryActivity.this, InterstitialActivity.class);
				InterstitialActivity.FS_DESIRE_AD_FORM = 0;
				intent.putExtra("form", 0);
				startActivity(intent);
		}});
//        开屏广告跳转
        entryAdButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EntryActivity.this, InterstitialActivity.class);
				InterstitialActivity.FS_DESIRE_AD_FORM = 1;
				intent.putExtra("form", 1);
				startActivity(intent);
		}});
//        原生广告跳转
        nativeAdButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EntryActivity.this, NativeActivity.class);
				startActivity(intent);
		}});
        
        feedsAdButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EntryActivity.this, FeedsActivity.class);
				startActivity(intent);
		}});
        
//		Log.e("getExternalCacheDir", "getExternalCacheDir:"+this.getApplication().getExternalCacheDir().getAbsolutePath());
//		Log.e("getExternalCacheDirs", "getExternalCacheDirs:"+this.getApplication().getExternalCacheDirs()[0].getAbsolutePath());
//		Log.e("getFilesDir", "getFilesDir:"+this.getApplication().getFilesDir().getAbsolutePath());
//		appFiles = this.getApplication().databaseList();
//		for(String name:appFiles){
//			Log.e("databaseList", "databaseList:"+name);
//		}
		
		
     
		
    }
}


