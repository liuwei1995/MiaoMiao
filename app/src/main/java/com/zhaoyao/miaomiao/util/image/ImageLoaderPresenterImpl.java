package com.zhaoyao.miaomiao.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.zhaoyao.miaomiao.util.image.util.IImagePresenter;


/**
 * Created by liuwei on 2017/5/25 09:33
 */

public final class ImageLoaderPresenterImpl implements IImagePresenter {

    private ImageLoaderPresenterImpl(Context context) {
        ImageLoaderUtils.init(context);
    }

    private static ImageLoaderPresenterImpl okHttpUtils = null;
    private static Handler mainHandler;
    public static ImageLoaderPresenterImpl newInstance(Context context) {
        if (okHttpUtils == null) {
            synchronized (ImageLoaderPresenterImpl.class){
                if(okHttpUtils == null){
                    okHttpUtils = new ImageLoaderPresenterImpl(context);
                    //更新UI线程
                    mainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        return  okHttpUtils;
    }

    @Override
    public void disPlay(String uri, ImageView imageView) {
        ImageLoaderUtils.disPlay(uri,imageView);
    }

    @Override
    public void disPlay(String uri, ImageView imageView, @DrawableRes int defaultPic) {
        ImageLoaderUtils.disPlay(uri,imageView,defaultPic,null);
    }

//    @Override
//    public void disPlay(String uri, ImageAware imageAware, @DrawableRes int defaultPic) {
//        ImageLoaderUtils.disPlay(uri,imageAware,defaultPic);
//    }

    @Override
    public Bitmap getImageBitmap(String uri) {
        return null;
    }

    @Override
    public Bitmap getImageBitmap(String uri, int width, int height) {
        return ImageLoaderUtils.getImageBitmap(uri,width,height);
    }

    @Override
    public Bitmap loadImageSync(String uri) {
        return ImageLoaderUtils.getImageLoader().loadImageSync(uri);
    }

    @Override
    public void onClearMemoryClick() {
        ImageLoaderUtils.onClearMemoryClick();
    }

    @Override
    public void onClearDiskClick() {
        ImageLoaderUtils.onClearDiskClick();
    }

    @Override
    public void onClearImageUrl(String url) {
        ImageLoaderUtils.onClearImageUrl(url);
    }

    @Override
    public void stop() {
        ImageLoaderUtils.stop();
    }

    @Override
    public void resume() {
        ImageLoaderUtils.resume();
    }

    @Override
    public void destroy() {
        ImageLoaderUtils.destroy();
    }
}
