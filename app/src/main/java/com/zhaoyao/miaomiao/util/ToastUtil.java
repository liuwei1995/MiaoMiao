package com.zhaoyao.miaomiao.util;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by HuBaicheng on 2017/6/8
 */

public final class ToastUtil {

    private static Toast toast;

    private ToastUtil() {
        throw new NullPointerException("New object cannot be generated");
    }

    public static void toastSome(Context context, CharSequence text){
        toastSome(context,text,Toast.LENGTH_SHORT);
    }

    public static void toastSome(Context context, CharSequence text,@IntRange(from = Snackbar.LENGTH_INDEFINITE,to = Snackbar.LENGTH_LONG) int duration){
        synchronized (ToastUtil.class){
            if(toast == null){
                if (context.getApplicationContext() != null){
                    toast = Toast.makeText(context.getApplicationContext(), text, duration);
                }else {
                    toast = Toast.makeText(context, text, duration);
                }
            }else{
                toast.setDuration(duration);
                toast.setText(text);
            }
        }
        toast.show();
    }
    public static void toastSome(Context context,@StringRes int resId){
        toastSome(context, resId,Toast.LENGTH_SHORT);
    }
    public static void toastSome(Context context,@StringRes int resId,@IntRange(from = Snackbar.LENGTH_INDEFINITE,to = Snackbar.LENGTH_LONG) int duration){
        synchronized (ToastUtil.class){
            if(toast == null){
                if (context.getApplicationContext() != null){
                    toast = Toast.makeText(context.getApplicationContext(), resId, duration);
                }else {
                    toast = Toast.makeText(context, resId, duration);
                }
            }else{
                toast.setDuration(duration);
                toast.setText(resId);
            }
        }
        toast.show();
    }

    public static void toastConnectFail(Context context){
        toastSome(context,"网络连接失败");
    }

}
