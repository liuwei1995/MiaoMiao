package com.zhaoyao.miaomiao.util.image.util;

import android.graphics.Bitmap;
import android.widget.ImageView;


/**
 * Created by liuwei on 2017/5/25 09:12
 */

public class ImageHelper implements IImagePresenter {

    private static IImagePresenter mIImagePresenter;

    public static void init(IImagePresenter iHttpPresenter){
        if (mIImagePresenter == null){
            synchronized (ImageHelper.class){
                if (mIImagePresenter == null)
                    mIImagePresenter = iHttpPresenter;
            }
        }
        newInstance();
    }
    private static ImageHelper mHttpHelper;
    private ImageHelper() {
    }

    public static ImageHelper newInstance() {
        if(mIImagePresenter == null)throw new NullPointerException("You must call "+ImageHelper.class.getName()+"#init(IHttpPresenter iHttpPresenter) ");
        if(mHttpHelper == null){
            synchronized (ImageHelper.class){
                if(mHttpHelper == null)
                mHttpHelper = new ImageHelper();
            }
        }
        return mHttpHelper;
    }


    @Override
    public void disPlay(String uri, ImageView imageView) {
        mIImagePresenter.disPlay(uri,imageView);
    }

    @Override
    public void disPlay(String uri, ImageView imageView, int defaultPic) {
        mIImagePresenter.disPlay(uri,imageView,defaultPic);
    }

//
//    @Override
//    public void disPlay(String uri, ImageAware imageAware, int defaultPic) {
//        mIImagePresenter.disPlay(uri,imageAware,defaultPic);
//    }

    @Override
    public Bitmap getImageBitmap(String uri) {
        return mIImagePresenter.getImageBitmap(uri);
    }

    @Override
    public Bitmap getImageBitmap(String uri, int width, int height) {
        return mIImagePresenter.getImageBitmap(uri,width,height);
    }

    @Override
    public Bitmap loadImageSync(String uri) {
        return mIImagePresenter.loadImageSync(uri);
    }

    @Override
    public void onClearMemoryClick() {
        mIImagePresenter.onClearMemoryClick();
    }

    @Override
    public void onClearDiskClick() {
        mIImagePresenter.onClearDiskClick();
    }

    @Override
    public void onClearImageUrl(String url) {
        mIImagePresenter.onClearImageUrl(url);
    }

    @Override
    public void stop() {
        mIImagePresenter.stop();
    }

    @Override
    public void resume() {
        mIImagePresenter.resume();
    }

    @Override
    public void destroy() {
        mIImagePresenter.destroy();
    }

}
