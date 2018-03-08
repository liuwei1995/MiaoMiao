package com.zhaoyao.miaomiao.views.imageview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;
import com.zhaoyao.miaomiao.receiver.GdtAdExposureClickReceiver;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;
import com.zhaoyao.miaomiao.util.preferences.DataAccessSharedPreferencesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuwei on 2018/3/7 10:29
 */

public class BannerViewClick extends BannerView {


    public BannerViewClick(Activity activity, String bannerPosID, ExposureListener exposureListener) {
        this(activity, ADSize.BANNER, Constants.APPID, bannerPosID, exposureListener);
    }

    private String bannerPosID;
    private ExposureListener mExposureListener;


    public BannerViewClick(Activity activity, ADSize adSize, String s, String bannerPosID, ExposureListener exposureListener) {
        super(activity, adSize, s, bannerPosID);
        this.bannerPosID = bannerPosID;
        this.mExposureListener = exposureListener;
        this.setRefresh(30);
//        setOnClickListener(this);
        this.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {
                LogUtils.d("onNoAD");
            }

            @Override
            public void onADReceiv() {
                LogUtils.d("onADReceiv");
            }

            @Override
            public void onADExposure() {
                setTime(System.currentTimeMillis());
                if (mExposureListener != null) {
                    mExposureListener.onADExposure(BannerViewClick.this);
                }
            }

            @Override
            public void onADClicked() {
                if (mExposureListener != null) {
                    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
                    Integer anInt = DataAccessSharedPreferencesUtils.newInstance().getInt("BannerViewClick_" + yyyyMMdd.format(new Date()), BannerViewClick.this.getBannerPosID(), 0);
                    ++anInt;
                    DataAccessSharedPreferencesUtils.newInstance().putInt("BannerViewClick_" + yyyyMMdd.format(new Date()), BannerViewClick.this.getBannerPosID(), anInt);
                    getContext().sendBroadcast(new Intent(GdtAdExposureClickReceiver.ACTION_UPDATE_CLICK_NUMBER));
                    mExposureListener.onADClicked(BannerViewClick.this);
                }
            }
        });
    }

    public interface ExposureListener {

        void onADExposure(BannerView bannerView);

        void onADClicked(BannerView bannerView);

    }

    private long time = 0;

    private int group;//属于组
    private int currentGroup;//当前组

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(int currentGroup) {
        this.currentGroup = currentGroup;
    }


    /**
     * 将图片存到本地
     */
    private synchronized static Uri saveBitmap(Bitmap bm, String picName) {
        FileOutputStream out = null;
        try {
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MiaoMiao/" + picName + ".jpg";
            File f = new File(dir);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            return Uri.fromFile(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    public String getBannerPosID() {
        return bannerPosID;
    }

}
