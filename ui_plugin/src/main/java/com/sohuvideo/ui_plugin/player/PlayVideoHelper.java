package com.sohuvideo.ui_plugin.player;

import android.content.Context;
import android.content.Intent;

import com.sohuvideo.ui_plugin.ui.PlayerActivity;

/**
 * Created by tension on 15/11/23.
 */
public class PlayVideoHelper {
    public static final int TYPE_FOR_SOHU = 1;
    public static final int TYPE_FOR_PGC = 2;

    public static final String EXTRA_VID = "vid";
    public static final String EXTRA_AID = "aid";
    public static final String EXTRA_SITE = "site";
    public static final String EXTRA_STARTPOSITION = "startPosition";//毫秒级别
    public static final String ACTION_PLAY = "sohu.intent.action.PLAYVIDEO";
    public static final String EXTRA_TYPE = "type";

    public static void playSohuOnlineVideo(Context context, long aid, long vid,
                                            int site, int startPosition, int type) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_AID, aid);
        intent.putExtra(EXTRA_VID, vid);
        intent.putExtra(EXTRA_SITE, site);
        intent.putExtra(EXTRA_STARTPOSITION, startPosition < 0 ? 0
                : startPosition);
        context.startActivity(intent);
    }
}
