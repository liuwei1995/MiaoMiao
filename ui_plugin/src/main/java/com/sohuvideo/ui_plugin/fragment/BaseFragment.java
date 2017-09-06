package com.sohuvideo.ui_plugin.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.view.HeaderPullListView;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected HeaderPullListView mListView;
    protected LinearLayout netErrorView; /*网络加载错误布局*/
    protected LinearLayout mLoadingView; //读取数据的加载进度条
    protected View mFootLoadingView;   //滚动加载更多的进度条
    protected View mEndView;
    protected View mFooterView;
    protected Button mTryBt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_base, container, false);
        initFootView();
        setUpViews(view);
        initListViewEvent();
        initTryButtonEvent();
        return view;
    }

    private void initTryButtonEvent() {
        mTryBt.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        loadDataFromNet();
    }
    public void initData(){

    }

    public abstract void loadDataFromNet();
    public abstract void initListViewEvent();
    public abstract void initFootView();
    private void setUpViews(View view) {
        netErrorView = (LinearLayout) view.findViewById(R.id.net_error);
        mLoadingView = (LinearLayout) view.findViewById(R.id.id_lodingdata);
        mTryBt = (Button) netErrorView.findViewById(R.id.id_try_bt);
        mListView = (HeaderPullListView) view.findViewById(R.id.id_lv);
        mEndView = mFooterView.findViewById(R.id.more_end);
        mListView.addFooterView(mFooterView);
        mFootLoadingView = mFooterView.findViewById(R.id.more_loading);
        stateReset();
        showLoadingDataView();
    }


    protected void stateReset() {
        mListView.setVisibility(View.GONE);
        netErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        TextView tv = (TextView) netErrorView.findViewById(R.id.id_error_tv);
        tv.setText(R.string.netError);
    }

    /**
     * 显示正在读取数据
     */
    protected void showLoadingDataView() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    protected void showErrorView() {
        netErrorView.setVisibility(View.VISIBLE);
    }

    /**
     * 滚动加载更多
     */
    protected void showLoadingView() {
        mEndView = mFooterView.findViewById(R.id.more_end);
        mFootLoadingView.setVisibility(View.VISIBLE);
        mEndView.setVisibility(View.GONE);
    }


    protected void setNoMoreLoadingDataView() {
        mFootLoadingView.setVisibility(View.GONE);
        mEndView.setVisibility(View.VISIBLE);
    }
    protected void hideFootLoadingView() {
        mFootLoadingView.setVisibility(View.GONE);
    }


    protected void showLv() {
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        loadDataFromNet();
    }
}
