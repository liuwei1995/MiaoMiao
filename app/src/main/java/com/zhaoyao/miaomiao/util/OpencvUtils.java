package com.zhaoyao.miaomiao.util;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by liuwei on 2018/3/5 13:24
 * opencv 工具
 */

public final class OpencvUtils {

    private static final String TAG = "OpencvUtils";

    public static OpencvUtils newInstance() {//单例模式
        return Singleton.fragment;
    }

    private static final class Singleton {
        private static OpencvUtils fragment = new OpencvUtils();
    }

    private OpencvUtils() {
    }

    /**
     * 比较来个矩阵的相似度
     *
     * @param srcBmp
     * @param desBmp
     */
    public double compareSimilarity(Bitmap srcBmp, Bitmap desBmp) {
        Mat mat1 = new Mat();
        Mat mat2 = new Mat();
        Mat srcMat = new Mat();
        Mat desMat = new Mat();
        Utils.bitmapToMat(srcBmp, mat1);
        Utils.bitmapToMat(desBmp, mat2);
        Imgproc.cvtColor(mat1, srcMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(mat2, desMat, Imgproc.COLOR_BGR2GRAY);
        return compareSimilarity(srcMat, desMat);
    }

    private double compareSimilarity(Mat srcMat, Mat desMat) {
        srcMat.convertTo(srcMat, CvType.CV_32F);
        desMat.convertTo(desMat, CvType.CV_32F);
        double target = Imgproc.compareHist(srcMat, desMat, Imgproc.CV_COMP_CORREL);
        Log.e(TAG, "相似度 ：   ==" + target);
        return target;
    }
}
