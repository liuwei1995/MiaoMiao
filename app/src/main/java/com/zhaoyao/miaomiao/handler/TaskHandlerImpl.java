package com.zhaoyao.miaomiao.handler;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 *  Handler 所有
 * Created by liuwei on 2017/6/30 14:11
 */
public class TaskHandlerImpl extends Handler {


   private WeakReference<TaskHandler> weakReference;

    public TaskHandlerImpl(TaskHandler object) {
        weakReference = new WeakReference<>(object);
    }

    /**
     * 消息接受处理
     */
    @Override
    public void handleMessage(Message msg) {
        TaskHandler object;
        if (weakReference != null && (object = weakReference.get()) != null) {
            object.handleMessage(weakReference, msg);
        }
    }
}