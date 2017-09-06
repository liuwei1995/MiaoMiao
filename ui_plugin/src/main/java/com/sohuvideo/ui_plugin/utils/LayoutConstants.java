package com.sohuvideo.ui_plugin.utils;

import android.content.Context;

import com.android.sohu.sdk.common.toolbox.DisplayUtils;

public class LayoutConstants {
    private static final String TAG = LayoutConstants.class.getSimpleName();
    public static int VIDEO_ITEM_SPACE = 5;
    public static int sHorVideoImgWidth;
    public static int  sHorVideoImgHeight;
    public static int sHorVideoImgWidth_Produce;
    public static int  sHorVideoImgHeight_Produce;

    public static void reInitLayoutConstants(Context context) {
        //4:3
        sHorVideoImgWidth = (DisplayUtils.getScreenWidth(context) - DpiUtil.dipToPx(context, VIDEO_ITEM_SPACE) * 3) /2;
        sHorVideoImgHeight=(sHorVideoImgWidth * 9) >> 4;

        sHorVideoImgWidth_Produce = DisplayUtils.getScreenWidth(context);
        sHorVideoImgHeight_Produce = (sHorVideoImgWidth_Produce * 9) >> 4;
    }
}
