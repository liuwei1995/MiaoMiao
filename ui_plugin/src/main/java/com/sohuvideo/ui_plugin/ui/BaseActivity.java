package com.sohuvideo.ui_plugin.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sohuvideo.ui_plugin.R;

public abstract class BaseActivity extends Activity {

    protected LinearLayout netErrorView;
    protected LinearLayout loadingView;
    protected Button mTryBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initCommonView();
    }


    private void initCommonView() {
        /*网络加载错误布局*/
        View view = findViewById(R.id.net_error);
        netErrorView = (LinearLayout) view.findViewById(R.id.net_error);
        mTryBt = (Button) netErrorView.findViewById(R.id.id_try_bt);
        /*正在加载布局*/
        loadingView = (LinearLayout) findViewById(R.id.id_lodingdata);
    }

    /**
     * 显示Loading
     */
    protected void showLoadingView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏Loading
     */
    protected void hideLoadingView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示netError
     */
    protected void showNetErrorView() {
        if (netErrorView != null) {
            netErrorView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏NetError
     */
    protected void hideNetErrorView() {
        if (netErrorView != null) {
            netErrorView.setVisibility(View.GONE);
        }
    }


}
