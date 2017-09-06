package com.zhaoyao.miaomiao.util.image.util;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by liuwei on 2017/5/25 09:10
 */

public interface IImagePresenter {

    void disPlay(String uri, ImageView imageView);

    void disPlay(String uri, ImageView imageView, @DrawableRes int defaultPic);

//    void disPlay(String uri, ImageAware imageAware, @DrawableRes int defaultPic);

    Bitmap getImageBitmap(String uri);

    Bitmap getImageBitmap(String uri, int width, int height);

    Bitmap loadImageSync(String uri);

    /**
     * 清除内存缓存
     */
    void onClearMemoryClick();

    /**
     * 清除本地缓存
     */
     void onClearDiskClick();

    void onClearImageUrl(String url);

    void stop();

     void resume();

     void destroy();


}
