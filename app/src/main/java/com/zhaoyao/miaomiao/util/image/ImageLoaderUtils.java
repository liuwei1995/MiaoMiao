package com.zhaoyao.miaomiao.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhaoyao.miaomiao.R;

import java.io.File;

/**
 * Created by liuwei on 2017/6/5 10:58
 */

public final class ImageLoaderUtils {
    private ImageLoaderUtils() {
    }

    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static DisplayImageOptions options = null;

//    public static final String IMAGELOADER_CACHE ="zhaoyaohealthy/imageloader/Cache";

    public static void init(Context context) {
        initImageLoader(context);
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static boolean checkImageLoader() {
        return imageLoader.isInited();
    }

    public static void disPlay(String uri, ImageAware imageAware, int defaultPic) {
        setOptions(defaultPic, new SimpleBitmapDisplayer());
        imageLoader.displayImage(uri, imageAware, options);
    }

    public static void disPlay(String uri, ImageView imageView, int defaultPic,ImageLoadingListener listener) {
        setOptions(defaultPic, new SimpleBitmapDisplayer());
        imageLoader.displayImage(uri, imageView, options, listener);
    }

    public static void disPlayRounded(String uri, ImageView imageView,int defaultPic, int cornerRadiusPixels,
                                      ImageLoadingListener listener) {
        setOptions(defaultPic, new RoundedVignetteBitmapDisplayer(
                cornerRadiusPixels, 2));
        imageLoader.displayImage(uri, imageView, options, listener);
    }

    public static void disPlay(String uri, ImageView imageView) {
        setOptions(0, new SimpleBitmapDisplayer());
        imageLoader.displayImage(uri, imageView, options);
    }

    /**
     * 根据uri获取图片的bitmap
     *
     * @param uri
     *            图片的uri地址
     * @param width
     *            指定图片的最大宽度
     * @param height
     *            指定图片的最大高度
     * @return
     */
    public static Bitmap getImageBitmap(String uri, int width, int height) {
        setOptions(0, new SimpleBitmapDisplayer());
        ImageSize targetSize = new ImageSize(width, height); // result Bitmap
        return imageLoader.loadImageSync(uri, targetSize, options);
    }


    private static void setOptions(int defaultPic, BitmapDisplayer displayer) {
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.biz_news_local_weather_bg_big) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.biz_news_local_weather_bg_big) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.biz_news_local_weather_bg_big) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .displayer(displayer) // 设置成圆角图片
//                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 构建完成
    }

    /**
     * 清除内存缓存
     */
    public static void onClearMemoryClick() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 清除本地缓存
     */
    public static void onClearDiskClick() {
        ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 清除某一张图片
     * @param url
     */
    public static void onClearImageUrl(String url) {
        DiskCacheUtils.removeFromCache(url,   ImageLoader.getInstance().getDiskCache());
        MemoryCacheUtils.removeFromCache(url,   ImageLoader.getInstance().getMemoryCache());
    }

    public static void stop() {
        ImageLoader.getInstance().stop();
    }

    public static void resume() {
        ImageLoader.getInstance().resume();
    }

    public static void destroy() {
        ImageLoader.getInstance().destroy();
    }


    /**
     * 配置imageLoader
     * @param context
     */
    private static void initImageLoader(Context context) {
//        imageloader/Cache
        //缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, context.getPackageName()+"/imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 由原先的discCache -> diskCache
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }
}
