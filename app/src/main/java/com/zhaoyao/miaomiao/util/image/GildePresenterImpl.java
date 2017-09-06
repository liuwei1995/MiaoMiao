package com.zhaoyao.miaomiao.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.zhaoyao.miaomiao.util.image.util.IImagePresenter;


/**
 * Created by liuwei on 2017/8/17 11:15
 */

public class GildePresenterImpl implements IImagePresenter {

    private static GildePresenterImpl mGildePresenterImpl;

    private GildePresenterImpl(Context context) {
        GlideUtils.newInstance().init(context);
    }

    public static GildePresenterImpl newInstance(Context context) {
        if (mGildePresenterImpl == null){
            synchronized (GildePresenterImpl.class){
                if (mGildePresenterImpl == null)
                mGildePresenterImpl = new GildePresenterImpl(context);
            }
        }
        return mGildePresenterImpl;
    }

    @Override
    public void disPlay(String uri, ImageView imageView) {
        GlideUtils.newInstance().load(uri, imageView);
    }

    @Override
    public void disPlay(String uri, ImageView imageView, @DrawableRes int defaultPic) {
        GlideUtils.newInstance().load(uri, imageView);
    }

    @Override
    public Bitmap getImageBitmap(String uri) {
        return null;
    }

    @Override
    public Bitmap getImageBitmap(String uri, int width, int height) {
        return null;
    }

    @Override
    public Bitmap loadImageSync(String uri) {
        return null;
    }

    @Override
    public void onClearMemoryClick() {

    }

    @Override
    public void onClearDiskClick() {

    }

    @Override
    public void onClearImageUrl(String url) {

    }

    @Override
    public void stop() {
        GlideUtils.newInstance().stop();
    }

    @Override
    public void resume() {
        GlideUtils.newInstance().resume();
    }

    @Override
    public void destroy() {
        GlideUtils.newInstance().destroy();
    }
}
