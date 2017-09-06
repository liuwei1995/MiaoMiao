package com.sohuvideo.ui_plugin.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {

    public static final int UNCONSTRAINED = -1;

    public static Options getOptionsForResourceId(Resources res, int id) {
        Options options = new Options();
        options.inJustDecodeBounds = true; // 只描边，不读取数据
        BitmapFactory.decodeResource(res, id, options);
        return options;
    }

    public static Bitmap getBitmapByResource(Resources res, int id,
                                             Options options, int screenWidth, int screenHeight) {
        if (options != null) {
            Rect r = getScreenRegion(screenWidth, screenHeight);
            int w = r.width();
            int h = r.height();
            int maxSize = w > h ? w : h;
            int inSimpleSize = computeSampleSize(options, maxSize, w * h);
            options.inSampleSize = inSimpleSize; // 设置缩放比例
            options.inJustDecodeBounds = false;
        }
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeResource(res, id, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
            System.gc();
        }
        return b;
    }

    public static Options getOptionsForByteArray(byte[] data) {
        Options options = new Options();
        options.inJustDecodeBounds = true; // 只描边，不读取数据
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return options;
    }

    public static Bitmap getBitmapByByteArray(byte[] data, Options options,
                                              int screenWidth, int screenHeight) {
        if (options != null) {
            Rect r = getScreenRegion(screenWidth, screenHeight);
            int w = r.width();
            int h = r.height();
            int maxSize = w > h ? w : h;
            int inSimpleSize = computeSampleSize(options, maxSize, w * h);
            options.inSampleSize = inSimpleSize; // 设置缩放比例
            options.inJustDecodeBounds = false;
        }
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
            System.gc();
        }
        return b;
    }

    /*
     * 获得设置信息
     */
    public static Options getOptionsForPath(String path) {
        Options options = new Options();
        options.inJustDecodeBounds = true; // 只描边，不读取数据
        BitmapFactory.decodeFile(path, options);
        return options;
    }

    /**
     * 获得图像
     *
     * @param path
     * @param options
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap getBitmapByPath(String path, Options options,
                                         int screenWidth, int screenHeight) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        FileInputStream in = null;
        in = new FileInputStream(file);
        if (options != null) {
            Rect r = getScreenRegion(screenWidth, screenHeight);
            int w = r.width();
            int h = r.height();
            int maxSize = w > h ? w : h;
            int inSimpleSize = computeSampleSize(options, maxSize, w * h);
            options.inSampleSize = inSimpleSize; // 设置缩放比例
            options.inJustDecodeBounds = false;
        }
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(in, null, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
            System.gc();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    private static Rect getScreenRegion(int width, int height) {
        return new Rect(0, 0, width, height);
    }

    /**
     * 获取需要进行缩放的比例，即options.inSampleSize
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
                .ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
                .min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == UNCONSTRAINED)
                && (minSideLength == UNCONSTRAINED)) {
            return 1;
        } else if (minSideLength == UNCONSTRAINED) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap getBitmapFromResource(Context context, int res) {
        if (context == null || res <= 0) {
            return null;
        }
        Bitmap bmp = null;
        Options opt = new Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(res);
        try {
            bmp = BitmapFactory.decodeStream(is, null, opt);
        } catch (OutOfMemoryError e) {
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            is = null;
        }
        return bmp;
    }
}
