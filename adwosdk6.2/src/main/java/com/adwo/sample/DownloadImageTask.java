package com.adwo.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 * 图片下载线程
 */
public class DownloadImageTask extends AsyncTask<String, Void, String > {
	
	private ImageView iv;
	
    public DownloadImageTask(ImageView iv) {
		super();
		this.iv = iv;
	}
    @Override
	protected String doInBackground(String... params) {
		
		String imageUrl = params[1];
		File file = new File(params[0]);
		
		if(!file.exists()){
			InputStream input = null;
			FileOutputStream fos = null;
			HttpURLConnection conn =  null;
			try {
				URL url = new URL(imageUrl);     
                conn = (HttpURLConnection) url.openConnection();     
                conn.setConnectTimeout(20 * 1000); 
                conn.setReadTimeout(20 * 1000);
                conn.setRequestMethod("GET");  
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){     
                	input = conn.getInputStream();        
                }    
//                Log.e("checkdata", input.available()+"-->"+params[0]);
//        		写到程序缓存中
        		fos = new FileOutputStream(file);
        		
    	        byte[] buffer = new byte[1024];     
    	        int len = 0;     
    	        while( (len=input.read(buffer)) != -1){     
    	        	fos.write(buffer, 0, len);     
    	        }     
//    	        Log.e("checkdata", "-->"+imageUrl);
    			conn.disconnect();conn=null;
    			fos.close();fos = null;
    			input.close();input = null;
    			
    			return params[0];
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(input!=null){
						input.close();input = null;
					}
					if(fos!=null){
						fos.close();fos = null;
					}
					if(conn!=null){
						conn.disconnect();conn=null;
					}
				} catch (IOException e) {}
			}
		}
		else{
			Bitmap bitmap = BitmapFactory.decodeFile(params[0]);
			if(bitmap != null){
				bitmap.recycle();
				return params[0];
			}
			else{
				InputStream input = null;
				FileOutputStream fos = null;
				HttpURLConnection conn =  null;
				try {
					URL url = new URL(imageUrl);     
	                conn = (HttpURLConnection) url.openConnection();     
	                conn.setConnectTimeout(20 * 1000); 
	                conn.setReadTimeout(20 * 1000);
	                conn.setRequestMethod("GET");  
	                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){     
	                	input = conn.getInputStream();        
	                }    
//	                Log.e("checkdata", input.available()+"-->"+params[0]);
//	        		写到程序缓存中
	        		fos = new FileOutputStream(file);
	        		
	    	        byte[] buffer = new byte[1024];     
	    	        int len = 0;     
	    	        while( (len=input.read(buffer)) != -1){     
	    	        	fos.write(buffer, 0, len);     
	    	        }     
//	    	        Log.e("checkdata", "-->"+imageUrl);
	    			conn.disconnect();conn=null;
	    			fos.close();fos = null;
	    			input.close();input = null;
	    			
	    			return params[0];
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						if(input!=null){
							input.close();input = null;
						}
						if(fos!=null){
							fos.close();fos = null;
						}
						if(conn!=null){
							conn.disconnect();conn=null;
						}
					} catch (IOException e) {}
				}
			}
		}
		return null;
	}
    
	@Override
	protected void onPostExecute(String result) {
		if(result!=null && iv!=null){
			iv.setImageURI(Uri.parse(result));
		}
	}
}
