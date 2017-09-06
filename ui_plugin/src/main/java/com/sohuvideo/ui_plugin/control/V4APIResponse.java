/*
 * Copyright Sohu TV 2014. All rights reserved.
 */

package com.sohuvideo.ui_plugin.control;

public class V4APIResponse<T> {

    private int status;
    private String statusText;
    private T data;

    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }

    public T getData() {
        return data;
    }
}
