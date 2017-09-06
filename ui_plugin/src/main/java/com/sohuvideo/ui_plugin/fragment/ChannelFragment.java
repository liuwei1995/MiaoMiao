package com.sohuvideo.ui_plugin.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.sohu.lib.net.parse.CommonDataParser;
import com.sohu.lib.net.request.DataRequest;
import com.sohu.lib.net.request.listener.DefaultDataResponse;
import com.sohu.lib.net.util.ErrorType;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.adapter.ChannelAdapter;
import com.sohuvideo.ui_plugin.control.ResponseDataWrapperSet;
import com.sohuvideo.ui_plugin.listener.OnLoadMoreClickListener;
import com.sohuvideo.ui_plugin.manager.ItemModelManager;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.Channel;
import com.sohuvideo.ui_plugin.model.ColumnList;
import com.sohuvideo.ui_plugin.model.ColumnListData;
import com.sohuvideo.ui_plugin.model.ItemModel;
import com.sohuvideo.ui_plugin.net.URLFactory;
import com.sohuvideo.ui_plugin.ui.PGCListActivity;
import com.sohuvideo.ui_plugin.utils.LogManager;
import com.sohuvideo.ui_plugin.view.HeaderPullListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击每个Title对应的页面(除搜狐出品)
 */
public class ChannelFragment extends BaseFragment implements OnLoadMoreClickListener {

    private static final String TAG = ChannelFragment.class.getSimpleName();
    public static final String CID = "cid";
    public static final String SECOND_CATE_CODE = "second_cate_code";
    public static final String TITLE = "title";
    public static final String CHANNEL = "channel";
    private ChannelAdapter lvAdapter;
    private List<ItemModel> mItemModelList;

    public static ChannelFragment newInstance(Channel channel) {
        ChannelFragment f = new ChannelFragment();
        Bundle args = new Bundle();
        args.putParcelable(CHANNEL, channel);
        f.setArguments(args);
        return f;
    }


    @Override
    public void initData() {
        lvAdapter = new ChannelAdapter(getActivity(), this);
        mItemModelList = new ArrayList<ItemModel>();
    }

    @Override
    public void loadDataFromNet() {
        DataRequest dataRequest = new DataRequest(URLFactory.getChannelContentByCid(getChannel().getCate_id()));
        LogManager.e(TAG, URLFactory.getChannelContentByCid(getChannel().getCate_id()));
        dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.ColumnListDataWrapper.class));
        DefaultDataResponse response = new DefaultDataResponse() {
            @Override
            public void onSuccess(Object result, boolean b) {
                ResponseDataWrapperSet.ColumnListDataWrapper wrapper = (ResponseDataWrapperSet.ColumnListDataWrapper) result;
                ColumnList<List<ColumnListData>> data = wrapper.getData();
                if (data != null && data.getCount() > 0) {
                    List<ColumnListData> columns = data.getColumns();
                    updateColumnList(columns);
                } else {
                    noData();
                    return;
                }
                mListView.onRefreshComplete();
                stateReset();
                showLv();
            }

            @Override
            public void onFailure(ErrorType errorType) {
                LogManager.e(TAG, errorType.toString());
                stateReset();
                showErrorView();
            }
        };
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, response);
    }

    @Override
    public void initListViewEvent() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (mListView != null) {
                    mListView.setFirstItemIndex(firstVisibleItem);
                }
            }
        });


        mListView.setonRefreshListener(new HeaderPullListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                loadDataFromNet();
            }
        });
    }

    @Override
    public void initFootView() {
        mFooterView = LayoutInflater.from(getActivity()).inflate(
                R.layout.listview_footer_end_channel, null);
    }

    private void updateColumnList(List<ColumnListData> columns) {
        stateReset();
        showLv();
        if (columns != null && columns.size() > 0) {
            mItemModelList.clear();
            List<ItemModel> resultList = ItemModelManager.convertColumns2ItemModelList(columns);
            mItemModelList.addAll(resultList);
            mListView.setAdapter(lvAdapter);
            lvAdapter.updateList(mItemModelList);
        }
    }


    private void noData() {
        stateReset();
        showErrorView();
        TextView tv = (TextView) netErrorView.findViewById(R.id.id_error_tv);
        tv.setText(R.string.loadDataError);
    }


    @Override
    public void onMoreClick(String title, int cate_code) {
        Intent mIntent = new Intent();
        mIntent.setClass(getActivity(), PGCListActivity.class);
        mIntent.putExtra(CID, getChannel().getCate_id());
        mIntent.putExtra(SECOND_CATE_CODE, cate_code);
        mIntent.putExtra(TITLE, title);
        startActivity(mIntent);
    }

    private Channel getChannel() {
        return getArguments().getParcelable(CHANNEL);
    }
}
