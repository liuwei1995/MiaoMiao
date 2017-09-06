package com.sohuvideo.ui_plugin.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DpiUtil {
    private static float density = 0;

    public static float getDensity(Context c) {
        if (density == 0) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getMetrics(metrics);
            density = metrics.density;
        }
        return density;
    }

    /**
     * dip转换为px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, float dip) {
        return (int) (dip * getDensity(context) + 0.5f);
    }
}
