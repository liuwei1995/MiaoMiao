package com.sohuvideo.ui_plugin.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sohu.lib.net.parse.CommonDataParser;
import com.sohu.lib.net.request.DataRequest;
import com.sohu.lib.net.request.listener.DefaultDataResponse;
import com.sohu.lib.net.util.ErrorType;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.api.StatisticConstants;
import com.sohuvideo.ui_plugin.control.ResponseDataWrapperSet;
import com.sohuvideo.ui_plugin.manager.FragmentSwitchManager;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.Channel;
import com.sohuvideo.ui_plugin.net.URLFactory;
import com.sohuvideo.ui_plugin.utils.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * main
 */
public class SohuVideoFragment extends BaseNewFragment implements View.OnClickListener {
    private static final String TAG = SohuVideoFragment.class.getSimpleName();

    private FragmentSwitchManager switchFramentManager;
    private List<Channel> channels;

    private View content;
    private LinearLayout netErrorView; /*网络加载错误布局*/
    private LinearLayout mLoadingView; //读取数据的加载进度条
    private Button mTryBt;
    private ViewPager pager;
    private TabLayout indicator;

    public static SohuVideoFragment newInstance(boolean isPrepared) {
        Bundle args = new Bundle();
        SohuVideoFragment fragment = new SohuVideoFragment();
        args.putBoolean(IS_PREPARED,isPrepared);
        fragment.setArguments(args);
        return fragment;
    }
    public SohuVideoFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        switchFramentManager = FragmentSwitchManager.getInstance(this, activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sohu_video, container, false);
        setUpViews(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        mTryBt.setOnClickListener(this);
    }

    private void setUpViews(View content) {
        pager = (ViewPager) content.findViewById(R.id.id_pager);
        indicator = (TabLayout) content.findViewById(R.id.indicator);
        this.content = content.findViewById(R.id.id_content);
        netErrorView = (LinearLayout) content.findViewById(R.id.net_error);
        mLoadingView = (LinearLayout) content.findViewById(R.id.id_lodingdata);
        mTryBt = (Button) netErrorView.findViewById(R.id.id_try_bt);
        stateReset();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        switchFramentManager.initViewPager(pager, indicator);
//     TODO      getDataFromNet();
    }

    @Override
    protected void initData() {
        getDataFromNet();
    }

    /**
     * 请求频道
     *
     * @return
     */
    public void getDataFromNet() {
        stateReset();
        showLoadingDataView();
        DataRequest dataRequest = new DataRequest(URLFactory.getPgcTitle());
        LogManager.e(TAG, URLFactory.getPgcTitle());
        DefaultDataResponse response = new DefaultDataResponse() {
            @Override
            public void onSuccess(Object result, boolean b) {
                ResponseDataWrapperSet.ChannelListDataWrapper wrapper = (ResponseDataWrapperSet.ChannelListDataWrapper) result;
                if (wrapper != null) {
                    channels = wrapper.getData();
                    initTitle();
                }
            }

            @Override
            public void onFailure(ErrorType errorType) {
                stateReset();
                showErrorView();
            }
        };
        CommonDataParser parser = new CommonDataParser(ResponseDataWrapperSet.ChannelListDataWrapper.class);
        dataRequest.setResultParser(parser);
        dataRequest.setNeedCache(false);
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, response);
    }

    private void showLoadingDataView() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void showContent() {
        content.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        netErrorView.setVisibility(View.VISIBLE);
    }
    
    /**
     * 初始化标题
     */
    private void initTitle() {
        if (channels == null) {
            channels = new ArrayList<>();
        }
//        Channel sohuChannel1 = new Channel();
//        sohuChannel1.setCate_id(9004);
//        sohuChannel1.setCate_name("出品人");
//        sohuChannel1.setType(StatisticConstants.FragmentType.PRODUCER);
//        channels.add(0, sohuChannel1);

        Channel sohuChannel = new Channel();
        sohuChannel.setCate_id(9004);
        sohuChannel.setCate_name("搜狐出品");
        sohuChannel.setType(StatisticConstants.FragmentType.SOHUMAKE);
        channels.add(0, sohuChannel);

        switchFramentManager.notifyAdapter(channels);
        stateReset();
        showContent();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NetRequestManager.cancleAllRequest();
        if (switchFramentManager != null) {
            switchFramentManager.clearFragment();
        }
    }

    /**
     * 重置
     */
    protected void stateReset() {
        content.setVisibility(View.GONE);
        netErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == mTryBt) {
            getDataFromNet();
        }
    }
}
